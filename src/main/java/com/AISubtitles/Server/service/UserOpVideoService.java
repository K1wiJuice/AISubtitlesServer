package com.AISubtitles.Server.service;

import com.AISubtitles.Server.dao.VideoBrowsesDao;
import com.AISubtitles.Server.dao.VideoCollectionsDao;
import com.AISubtitles.Server.dao.VideoCommentsDao;
import com.AISubtitles.Server.dao.VideoFavorsDao;
import com.AISubtitles.Server.dao.VideoSharesDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.VideoBrowses;
import com.AISubtitles.Server.domain.VideoCollections;
import com.AISubtitles.Server.domain.VideoComments;
import com.AISubtitles.Server.domain.VideoFavors;
import com.AISubtitles.Server.domain.VideoShares;
import com.AISubtitles.Server.utils.TokenUtil;
import com.AISubtitles.common.CodeConsts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOpVideoService {
    @Autowired
    VideoBrowsesDao videoBrowsesDao;

    @Autowired
    VideoCollectionsDao videoCollectionsDao;

    @Autowired
    VideoCommentsDao videoCommentsDao;

    @Autowired
    VideoFavorsDao videoFavorsDao;

    @Autowired
    VideoSharesDao videoSharesDao;

    public Result addRecord(String type, int videoId, String content) {
        Result result = new Result();
        int userId = TokenUtil.getTokenUserId();
        try {
            if (type.equals("browse")) {
                VideoBrowses videoBrowses = new VideoBrowses();
                videoBrowses.setUserId(userId);
                videoBrowses.setVideoId(videoId);
                videoBrowsesDao.save(videoBrowses);
            } else if (type.equals("collection")) {
                VideoCollections videoCollections = videoCollectionsDao.findByUserIdAndVideoId(userId, videoId);
                if (videoCollections == null) {
                    videoCollections = new VideoCollections();
                    videoCollections.setUserId(userId);
                    videoCollections.setVideoId(videoId);
                    videoCollectionsDao.save(videoCollections);
                } else {
                    throw new Exception("已经收藏过该视频");
                }
            } else if (type.equals("comment")) {
                VideoComments videoComments = new VideoComments();
                videoComments.setUserId(userId);
                videoComments.setVideoId(videoId);
                videoComments.setContent(content);
                videoCommentsDao.save(videoComments);
            } else if (type.equals("favor")) {
                VideoFavors videoFavors = videoFavorsDao.findByUserIdAndVideoId(userId, videoId);
                if (videoFavors == null) {
                    videoFavors = new VideoFavors();
                    videoFavors.setUserId(userId);
                    videoFavors.setVideoId(videoId);
                    videoFavorsDao.save(videoFavors);
                } else {
                    throw new Exception("已经点赞过该视频");
                }
            } else if (type.equals("share")) {
                VideoShares videoShares = new VideoShares();
                videoShares.setUserId(userId);
                videoShares.setVideoId(videoId);
                videoSharesDao.save(videoShares);
            } else {
                result.setCode(CodeConsts.CODE_MODIFY_ERROR);
                result.setData("请求格式错误");
                return result;
            }
        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_HAVE_DONE);
            result.setData(e.getMessage());
            return result;
        }

        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setData("添加成功");
        return result;
    }

    public Result deleteRecord(String type, int videoId) {
        Result result = new Result();

        int userId = TokenUtil.getTokenUserId();
        if (type.equals("collection")) {
            VideoCollections videoCollections = videoCollectionsDao.findByUserIdAndVideoId(userId, videoId);
            videoCollectionsDao.deleteById(videoCollections.getCollectionId());
        } else if (type.equals("favor")) {
            VideoFavors videoFavors = videoFavorsDao.findByUserIdAndVideoId(userId, videoId);
            videoFavorsDao.deleteById(videoFavors.getFavorId());
        } else {
            result.setCode(CodeConsts.CODE_MODIFY_ERROR);
            result.setData("请求格式错误");
            return result;
        }

        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setData("删除成功");
        return result;
    }

}