package com.AISubtitles.Server.controller;

import com.AISubtitles.Server.annotation.UserLoginToken;
import com.AISubtitles.Server.dao.MessageDao;
import com.AISubtitles.Server.domain.Message;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.MessageService;
import com.AISubtitles.common.CodeConsts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;
    
    @UserLoginToken
    @PostMapping(value = "/msg")
    public Result msg(String type) {
        if (type.equals("system"));
        else if (type.equals("favor"));
        else if (type.equals("collection"));
        else if (type.equals("read"));
        else if (type.equals("notread"));
        else {
            Result result = new Result();
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            return result;
        }
        return messageService.msg(type);
    }

    @Autowired
    MessageDao messageDao;

    @UserLoginToken
    @PostMapping(value = "/msg/read")
    public Result readMsg(int msgId) {
        Result result = new Result();
        Message msg = messageDao.findById(msgId).get();
        msg.setMsgRead(1);
        messageDao.saveAndFlush(msg);
        result.setCode(CodeConsts.CODE_SUCCESS);
        return result;
    }

    @GetMapping(value = "/addmsg")
    public void msg(String type, String fromUserName, int toUserId, String videoName, String content, int read) {
        Message message = new Message();
        message.setMsgType(type);
        message.setFromUserName(fromUserName);
        message.setToUserId(toUserId);
        message.setVideoName(videoName);
        message.setMsgContent(content);
        message.setMsgRead(read);
        messageDao.save(message);
    }

}