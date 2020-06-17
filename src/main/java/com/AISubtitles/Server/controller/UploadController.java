package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Chunk;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.Server.service.ChunkService;
import com.AISubtitles.common.CodeConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
            chunk.setVideoNameAndUserId(video);

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


    @PostMapping("/mergeFile")
    public Result mergeFile(Integer userId,
                            String videoName,
                            Double videoSize,
                            String videoFormat,
                            String videoCover,
                            Double videoDuration) {
        Result result = new Result();
        Video video = new Video();

        video.setVideoShares(0);
        video.setVideoBrowses(0);
        video.setVideoFavors(0);
        video.setVideoDuration(videoDuration);
        video.setVideoFormat(videoFormat);
        video.setVideoName(videoName);
        video.setVideoSize(videoSize);
        video.setVideoCover(videoCover);
        video.setUserId(userId);

        String videoPath = uploadFolder + "/" + videoName + "--" + userId + videoName;
        String folder = uploadFolder + "/" + videoName + "--" + userId;
        merge(videoPath, folder, videoName);
        video.setVideoPath(videoPath);
        videoDao.save(video);
        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setData("合并成功");

        return result;
    }


    //为文件块生成存放路径
    public static String generatePath(String uploadFolder, Chunk chunk) {
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append("/").append(chunk.getVideoNameAndUserId());

        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        if (!Files.isWritable(Paths.get(sb.toString()))) {
            try {
                Files.createDirectories(Paths.get(sb.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.append("/")
                .append(chunk.getVideoNameAndUserId())
                .append("-")
                .append(chunk.getChunkNumber()).toString();
    }


    //合并文件块
    public static void merge(String targetFile, String folder, String filename) {
        try {
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
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
