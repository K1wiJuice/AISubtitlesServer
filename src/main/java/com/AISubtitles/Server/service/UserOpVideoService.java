package com.AISubtitles.Server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.AISubtitles.Server.dao.MessageDao;
import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.dao.VideoBrowsesDao;
import com.AISubtitles.Server.dao.VideoCollectionsDao;
import com.AISubtitles.Server.dao.VideoCommentsDao;
import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.dao.VideoFavorsDao;
import com.AISubtitles.Server.dao.VideoSharesDao;
import com.AISubtitles.Server.domain.Message;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.Server.domain.VideoBrowses;
import com.AISubtitles.Server.domain.VideoCollections;
import com.AISubtitles.Server.domain.VideoComments;
import com.AISubtitles.Server.domain.VideoFavors;
import com.AISubtitles.Server.domain.VideoShares;
import com.AISubtitles.Server.utils.TokenUtil;
import com.AISubtitles.common.CodeConsts;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOpVideoService {

    @Autowired
    UserDao userDao;

    @Autowired
    VideoDao videoDao;

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

    @Autowired
    MessageDao messageDao;

    public void addMessage(String type, String fromUserName, int toUserId, String videoName, String content) {
        Message msg = new Message();
        msg.setMsgType(type);
        msg.setFromUserName(fromUserName);
        msg.setToUserId(toUserId);
        msg.setVideoName(videoName);
        msg.setMsgContent(content);
        msg.setMsgRead(0);
        messageDao.save(msg);
    }

    public Result addRecord(String type, int videoId, String content) {
        Result result = new Result();
        int userId = TokenUtil.getTokenUserId();
        try {
            if (type.equals("browse")) {
                VideoBrowses videoBrowses = new VideoBrowses();
                videoBrowses.setUserId(userId);
                videoBrowses.setVideoId(videoId);
                videoBrowsesDao.save(videoBrowses);
                Video video = videoDao.findById(videoId).get();
                video.setVideoBrowses(video.getVideoBrowses()+1);
                videoDao.saveAndFlush(video);
            } else if (type.equals("collection")) {
                VideoCollections videoCollections = videoCollectionsDao.findByUserIdAndVideoId(userId, videoId);
                if (videoCollections == null) {
                    videoCollections = new VideoCollections();
                    videoCollections.setUserId(userId);
                    videoCollections.setVideoId(videoId);
                    videoCollectionsDao.save(videoCollections);
                    Video video = videoDao.findById(videoId).get();
                    video.setVideoCollections(video.getVideoCollections()+1);
                    videoDao.saveAndFlush(video);
                    addMessage(type,userDao.findById(userId).get().getUserName(), video.getUserId(), video.getVideoName(), content);
                } else {
                    throw new Exception("已经收藏过该视频");
                }
            } else if (type.equals("comment")) {
                VideoComments videoComments = new VideoComments();
                videoComments.setUserId(userId);
                videoComments.setVideoId(videoId);
                videoComments.setContent(content);
                videoCommentsDao.save(videoComments);
                Video video = videoDao.findById(videoId).get();
                video.setVideoComments(video.getVideoComments()+1);
                videoDao.saveAndFlush(video);
                addMessage(type, userDao.findById(userId).get().getUserName(), video.getUserId(), video.getVideoName(), content);
            } else if (type.equals("favor")) {
                VideoFavors videoFavors = videoFavorsDao.findByUserIdAndVideoId(userId, videoId);
                if (videoFavors == null) {
                    videoFavors = new VideoFavors();
                    videoFavors.setUserId(userId);
                    videoFavors.setVideoId(videoId);
                    videoFavorsDao.save(videoFavors);
                    Video video = videoDao.findById(videoId).get();
                    video.setVideoFavors(video.getVideoFavors()+1);
                    videoDao.saveAndFlush(video);
                    addMessage(type, userDao.findById(userId).get().getUserName(), video.getUserId(), video.getVideoName(), content);
                } else {
                    throw new Exception("已经点赞过该视频");
                }
            } else if (type.equals("share")) {
                VideoShares videoShares = new VideoShares();
                videoShares.setUserId(userId);
                videoShares.setVideoId(videoId);
                videoSharesDao.save(videoShares);
                Video video = videoDao.findById(videoId).get();
                video.setVideoShares(video.getVideoShares()+1);
                videoDao.saveAndFlush(video);
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

    public Result searchRecord(int videoId) {
        Result result = new Result();

        int userId = TokenUtil.getTokenUserId();
        
        JSONObject jsonObject = new JSONObject();
        VideoCollections videoCollections = videoCollectionsDao.findByUserIdAndVideoId(userId, videoId);
        if(videoCollections == null) {
            jsonObject.put("collection", 0);
        } else {
            jsonObject.put("collection", 1);
        }

        VideoFavors videoFavors = videoFavorsDao.findByUserIdAndVideoId(userId, videoId);
        if(videoFavors == null) {
            jsonObject.put("favor", 0);
        } else {
            jsonObject.put("favor", 1);
        }

        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setData(jsonObject.toJSONString());;
        return result;
    }

    public Result findVideoInfo(int videoId) {
        Result result = new Result();
        Video video = videoDao.findById(videoId).get();
        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setData(video);
        return result;
    }

    public Result deleteRecord(String type, int videoId) {
        Result result = new Result();

        int userId = TokenUtil.getTokenUserId();
        if (type.equals("collection")) {
            VideoCollections videoCollections = videoCollectionsDao.findByUserIdAndVideoId(userId, videoId);
            videoCollectionsDao.deleteById(videoCollections.getCollectionId());
            Video video = videoDao.findById(videoId).get();
            video.setVideoCollections(video.getVideoCollections() - 1);
            videoDao.saveAndFlush(video);
        } else if (type.equals("favor")) {
            VideoFavors videoFavors = videoFavorsDao.findByUserIdAndVideoId(userId, videoId);
            videoFavorsDao.deleteById(videoFavors.getFavorId());
            Video video = videoDao.findById(videoId).get();
            video.setVideoFavors(video.getVideoFavors() - 1);
            videoDao.saveAndFlush(video);
        } else {
            result.setCode(CodeConsts.CODE_MODIFY_ERROR);
            result.setData("请求格式错误");
            return result;
        }

        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setData("删除成功");
        return result;
    }

    private List<Video> videoList;
    private int videoNum;

    public void flushVideoList() {
        videoList = videoDao.findAll();
        Collections.sort(videoList, (Video v1, Video v2) -> v2.getCreateTime().compareTo(v1.getCreateTime()));
        videoNum = videoList.size();
    }

    public Result allVideo(int beginIndex, int endIndex) {
        Result result = new Result();
        if(videoList == null) {
            flushVideoList();
        }
        List<Video> dataList = new ArrayList<Video>();
        int cnt = 0;
        for (Video i : videoList) {
            ++cnt;
            if (cnt < beginIndex) continue;
            if (cnt > endIndex) break;
            dataList.add(i);
        }
        result.setCode(1000 + videoNum);
        result.setData(dataList);

        return result;
    }

    public Result ownVideo(int beginIndex, int endIndex) {
        Result result = new Result();
        int userId = TokenUtil.getTokenUserId();
        List<Video> ownList =  videoDao.findAllByUserId(userId);
        Collections.sort(ownList, (Video v1, Video v2) -> v2.getCreateTime().compareTo(v1.getCreateTime()));
        List<Video> dataList = new ArrayList<Video>();
        int cnt = 0;
        for (Video i : ownList) {
            ++cnt;
            if (cnt < beginIndex) continue;
            if (cnt > endIndex) break;
            dataList.add(i);
        }
        result.setData(dataList);
        result.setCode(1000 + ownList.size());
        return result;
    }

    public Result collectionVideo(int beginIndex, int endIndex) {
        Result result = new Result();
        int userId = TokenUtil.getTokenUserId();
        List<VideoCollections> videoCollections = videoCollectionsDao.findAllByUserId(userId);
        List<Video> dataList = new ArrayList<Video>();
        int cnt = 0;
        for (VideoCollections i : videoCollections) {
            ++cnt;
            if (cnt < beginIndex) continue;
            if (cnt > endIndex) break;
            dataList.add(videoDao.findById(i.getVideoId()).get());
        }
        result.setData(dataList);
        result.setCode(1000 + videoCollections.size());
        return result;
    }
    
    public Result searchVideo(String key, int beginIndex, int endIndex) {
        Result result = new Result();
        List<Video> keyVideoList = videoDao.findByVideoNameLike(key);
        List<Video> dataList = new ArrayList<Video>();
        int cnt = 0;
        for (Video i : keyVideoList) {
            ++cnt;
            if (cnt < beginIndex) continue;
            if (cnt > endIndex) break;
            dataList.add(i);
        }
        result.setData(dataList);
        result.setCode(1000 + keyVideoList.size());
        return result;
    }

}