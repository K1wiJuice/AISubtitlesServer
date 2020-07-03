package com.AISubtitles.Server.service;

import java.util.ArrayList;
import java.util.List;

import com.AISubtitles.Server.dao.MessageDao;
import com.AISubtitles.Server.domain.Message;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.utils.TokenUtil;
import com.AISubtitles.common.CodeConsts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    
    @Autowired
    MessageDao messageDao;

    public Result msg(String type) {
        Result result = new Result();
        int userId = TokenUtil.getTokenUserId();
        List<Message> messageList = messageDao.findAllByToUserId(userId);
        List<Message> dataList = new ArrayList<>();
        if(type.equals("read")) {
            for (Message i : messageList) {
                if(i.getMsgType().equals("comment") && i.getMsgRead() == 0) {
                    dataList.add(i);
                }
            }
        } else if (type.equals("notread")) {
            for (Message i : messageList) {
                if(i.getMsgType().equals("comment") && i.getMsgRead() == 1) {
                    dataList.add(i);
                }
            }
        } else {
            for (Message i : messageList) {
                if(i.getMsgType().equals(type)) {
                    dataList.add(i);
                }
            }
        }
        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setData(dataList);
        return result;
    }

}