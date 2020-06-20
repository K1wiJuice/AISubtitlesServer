package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Chunk;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.Server.service.ChunkService;
import com.AISubtitles.common.CodeConsts;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${prop.upload-folder}")
    private String uploadFolder;

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private ChunkService chunkService;


    //方法为post，接收前端的文件块上传
    @PostMapping("/chunk")
    public Result uploadChunk(Integer chunkNumber,
                              Long chunkSize,
                              String videoName,
                              Integer userId,
                              MultipartFile file) {
        Result result = new Result();
        try {
            byte[] bytes = file.getBytes();
            Chunk chunk = new Chunk();

            chunk.setChunkNumber(chunkNumber);
            chunk.setChunkSize(chunkSize);
            chunk.setFile(file);
            String video = videoName + "--" + userId;
            chunk.setVideoNameUserId(video);

            Path path = Paths.get(generatePath(uploadFolder, chunk));
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


    @GetMapping("/chunk")
    public Result checkChunk(Integer chunkNumber,
                             String videoName,
                             Integer userId) {
        Result result = new Result();
        if (chunkService.checkChunk(videoName+"--"+userId, chunkNumber)) {
            result.setCode(CodeConsts.CODE_CHUNK_EXIST);
            result.setData("该块已上传");
        } else {
            result.setCode(CodeConsts.CODE_CHUNK_NOT_EXITS);
            result.setData("该块未上传");
        }
        System.out.println("get");
        System.out.println(result);
        System.out.println(chunkNumber);
        System.out.println("get");
        return result;
    }


    @GetMapping("/mergeFile")
    public Result mergeFile(Integer userId,
                            String videoName) {
        Result result = new Result();
        Video video = new Video();

        String videoPath = uploadFolder + "/" + videoName + "--" + userId + "/" + videoName;
        String folder = uploadFolder + "/" + videoName + "--" + userId;
        merge(videoPath, folder, videoName);
        File mergedVideo = new File(videoPath);

        Encoder encoder = new Encoder();
        MultimediaInfo info = null;
        try {
            info = encoder.getInfo(mergedVideo);
            //获取视频时长
            long videoDuration = info.getDuration() / 60;
            //过去视频格式
            String videoFormat = info.getFormat();
            //获取视频大小
            FileInputStream fis = new FileInputStream(mergedVideo);
            FileChannel fc= fis.getChannel();
            BigDecimal videoSize = new BigDecimal(fc.size());

            video.setVideoShares(0);
            video.setVideoBrowses(0);
            video.setVideoFavors(0);
            video.setVideoDuration(videoDuration);
            video.setVideoFormat(videoFormat);
            video.setVideoName(videoName);
            video.setVideoSize(videoSize.doubleValue());
            video.setVideoCover("videoCover");
            video.setUserId(userId);
            video.setVideoPath(videoPath);
            videoDao.save(video);

            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData("合并成功");

        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_MERGED_FAILED);
            result.setData("合并失败");
            e.printStackTrace();
        }

        return result;
    }


    //result中的data是用户正在上传的文件的列表
    @GetMapping("/uploadingVideos")
    public Result getVideoUploading(Integer userId) {
        Result result = getVideoUploadingViaDatabase(userId);
        return result;
    }


    @Transactional
    @GetMapping("/cancel")
    public Result cancel(String videoName,
                         Integer userId) {
        Result result = new Result();
        //删除数据库中的记录
        chunkService.cancel(videoName, userId);
        //删除文件夹
        deleteFolder(uploadFolder+"/"+videoName+"--"+userId);
        return result;
    }


    //为文件块生成存放路径
    public static String generatePath(String uploadFolder, Chunk chunk) {
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append("/").append(chunk.getVideoNameUserId());

        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        if (!Files.isWritable(Paths.get(sb.toString()))) {
            try {
                Files.createDirectories(Paths.get(sb.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.append("/")
                .append(chunk.getVideoNameUserId())
                .append("-")
                .append(chunk.getChunkNumber()).toString();
    }


    //合并文件块
    public static void merge(String targetFile, String folder, String filename) {
        try {
            System.out.println(targetFile);
            System.out.println(folder);
            System.out.println(filename);
            Files.createFile(Paths.get(targetFile));
            Files.list(Paths.get(folder))
                    .filter(path -> !path.getFileName().toString().equals(filename))
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
//                            Files.delete(path);
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
