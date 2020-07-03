package com.AISubtitles.Server.controller;

import com.AISubtitles.Server.annotation.UserLoginToken;
import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.dao.UserModificationDao;
import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Chunk;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.domain.UserModification;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.Server.service.ChunkService;
import com.AISubtitles.Server.service.SubtitleSupportService;
import com.AISubtitles.Server.service.UserOpVideoService;
import com.AISubtitles.Server.utils.Md5Utils;
import com.AISubtitles.Server.utils.TokenUtil;
import com.AISubtitles.common.CodeConsts;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.integration.annotation.UseSpelInvoker;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@RestController
@RequestMapping("/uploader")
public class UploadController {

    @Value("${video-path}")
    private String videoSavePath;

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ChunkService chunkService;

    @Autowired
    private UserOpVideoService userOpVideoService;

    @Autowired
    @Resource
    SubtitleSupportService subtitleSupportService;

    //方法为post，接收前端的文件块上传
    @UserLoginToken
    @PostMapping("/chunk")
    public Result uploadChunk(Integer chunkNumber,
                              Long chunkSize,
                              String videoName,
                              String identifier,
                              MultipartFile file) {
        Result result = new Result();
        int userId = TokenUtil.getTokenUserId();
        try {
            byte[] bytes = file.getBytes();
            Chunk chunk = new Chunk();

            chunk.setChunkNumber(chunkNumber);
            chunk.setChunkSize(chunkSize);
            chunk.setFile(file);
            chunk.setVideoNameUserId(videoName + "--" + userId);
            chunk.setIdentifier(identifier);

            Path path = Paths.get(generatePath(videoSavePath, chunk));
            Files.write(path, bytes);
            chunkService.saveChunk(chunk);
            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData("chunk上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(CodeConsts.CODE_CHUNK_UPLOAD_FAIL);
            result.setData("chunk上传失败");
        }
        return result;
    }

    @UserLoginToken
    @GetMapping("/chunk")
    public Object checkChunk(Chunk chunk,
                             HttpServletResponse response) {
        if (chunkService.checkChunk(chunk.getIdentifier(), chunk.getChunkNumber())) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
        return chunk;
    }

    @UserLoginToken
    @PostMapping("/mergeFile")
    public Result mergeFile(String format, String identifier, String videoName) {
        Result result = new Result();
        Video video = new Video();

        int userId = TokenUtil.getTokenUserId();
        String videoPath = videoSavePath + "/" + identifier + "/" + identifier;
        String folder = videoSavePath + "/" + identifier;

        merge(videoPath, folder);
        // chunkService.cancel(identifier);
        File mergedVideo = new File(videoPath);

        Encoder encoder = new Encoder();

        MultimediaInfo info = null;
        try {
            info = encoder.getInfo(mergedVideo);

            //获取视频时长
            long videoDuration = info.getDuration() / 1000;
 
            //过去视频格式
            String videoFormat = format;

            //获取视频大小
            FileInputStream fis = new FileInputStream(mergedVideo);

            FileChannel fc= fis.getChannel();

            BigDecimal videoSize = new BigDecimal(fc.size());

            File path = new File(videoPath + "." + videoFormat);
            mergedVideo.renameTo(path);

            video.setVideoShares(0);
            video.setVideoBrowses(0);
            video.setVideoFavors(0);
            video.setVideoComments(0);
            video.setVideoCollections(0);
            video.setVideoDuration(videoDuration);
            video.setVideoFormat(videoFormat);
            video.setVideoName(videoName);
            video.setVideoSize(videoSize.doubleValue());
            video.setVideoCover("/image/Default_video_cover.jpg");
            video.setUserId(userId);
            video.setIdentifier(identifier);
            videoPath = "/video/" + identifier + "/" + identifier + "." + videoFormat;
            video.setVideoPath(videoPath);
            videoDao.save(video);           
            userOpVideoService.flushVideoList();

            int videoId = videoDao.findByIdentifier(identifier).getVideoId();
    	    subtitleSupportService.exportAudio(videoId);
            subtitleSupportService.audio2zhSubtitle(videoId);
            subtitleSupportService.translateSubtitle(videoId, "zh", "en");
            subtitleSupportService.mergeSubtitle(videoId);

            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData(video);

        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_MERGED_FAILED);
            result.setData("合并失败");
            e.printStackTrace();
        }

        return result;
    }

    @UserLoginToken
    //result中的data是用户正在上传的文件的列表
    @GetMapping("/uploadingVideos")
    public Result getVideoUploading() {
        int userId = TokenUtil.getTokenUserId();
        Result result = getVideoUploadingViaDatabase(userId);
        return result;
    }


    @Transactional
    @GetMapping("/cancel")
    public Result cancel(String identifier) {
        Result result = new Result();
        int userId = TokenUtil.getTokenUserId();
        //删除数据库中的记录
        chunkService.cancel(identifier);
        //删除文件夹
        deleteFolder(videoSavePath+"/"+identifier);
        //deleteFolder(uploadFolder+"/" + Md5Utils.md5(videoName+"--"+userId));
        return result;
    }

    @Value("${image-path}")
    private String imageSavePath;

    @Autowired
    UserModificationDao userModificationDao;

    // 操作时间字段由数据库自动记录
    public void add_user_modification_record(int userId, String fieldName, String oldValue, String newValue) {
        UserModification um = new UserModification();
        um.setUserId(userId);
        um.setFieldName(fieldName);
        um.setOldValue(oldValue);
        um.setNewValue(newValue);
        userModificationDao.save(um);
    }

    @UserLoginToken
    @PostMapping("/image")
    public Result uploadImage(String videoId, @RequestParam(value = "file") MultipartFile file) {
        Result result = new Result();
        if(file == null) {
            result.setCode(CodeConsts.CODE_CHUNK_UPLOAD_FAIL);
            result.setData("上传失败，文件为空");
            return result;
        }
        String identifier = "";
        String imagePath = "";
        try {
            byte[] bytes = file.getBytes();
            identifier = Md5Utils.md5(new String(bytes));
            imagePath = imageSavePath + "/" + identifier + "." + file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length-1];
            Files.write(Paths.get(imagePath), bytes);
            
        } catch (IOException e) {
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setData("写文件异常");
            return result;
        }

        String pathInDB = "/image/" + identifier + "." + file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length-1];
        if (videoId == null) {
            int userId = TokenUtil.getTokenUserId();
            User user = userDao.findById(userId).get();
            String oldValue = user.getImage();
            user.setImage(pathInDB);
            userDao.saveAndFlush(user);
            add_user_modification_record(userId, "image", oldValue, imagePath);
            result.setData(user);
        } else {
            Video video = videoDao.findById(Integer.parseInt(videoId)).get();
            video.setVideoCover(pathInDB);
            videoDao.saveAndFlush(video);
            userOpVideoService.flushVideoList();
        }


        result.setCode(CodeConsts.CODE_SUCCESS);
        return result;
    }


    //为文件块生成存放路径
    public static String generatePath(String uploadFolder, Chunk chunk) {
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append("/").append(chunk.getIdentifier());

        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        if (!Files.isWritable(Paths.get(sb.toString()))) {
            try {
                Files.createDirectories(Paths.get(sb.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.append("/")
                .append(chunk.getIdentifier())
                .append("-")
                .append(chunk.getChunkNumber()).toString();
    }


    //合并文件块
    public static void merge(String targetFile, String folder) {
        try {
            Files.createFile(Paths.get(targetFile));
            Files.list(Paths.get(folder))
                    .filter(path -> path.getFileName().toString().contains("-"))
                    .sorted((o1, o2) -> {
                        String p1 = o1.getFileName().toString();
                        String p2 = o2.getFileName().toString();
                        int i1 = p1.lastIndexOf("-");
                        int i2 = p2.lastIndexOf("-");
                        return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                    })
                    .forEach(path -> {
                        try {
                            //以追加的形式写入文件
                            Files.write(Paths.get(targetFile), Files.readAllBytes(path), StandardOpenOption.APPEND);
                            //合并后删除该块
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //通过数据库查找正在上传的视频
    //正在上传的视频是在chunk表中有记录但是在video_info中没有的
    public Result getVideoUploadingViaDatabase(Integer userId) {
        Result result = new Result();
        List<String> allVideosUploading = chunkService.getAllVideosUploading("%" + userId);

        if (allVideosUploading.size() == 0) {
            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData("没有正在传的视频");

        } else {
            result.setCode(CodeConsts.CODE_SUCCESS);
            for (int i = 0; i < allVideosUploading.size(); i++) {
                result.setData(result.getData() + "," + allVideosUploading.get(i).substring(0, allVideosUploading.get(i).length()-3));
            }
        }

        System.out.println(result);
        return result;
    }


    //取消上传时需要将已经传输的块都删除
    public void deleteFolder(String folderPath) {
        File file = new File(folderPath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
            file.delete();
        }
    }

}
