package com.AISubtitles.Server.service.impl;


import com.AISubtitles.Server.dao.UserAuthsDao;
import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.domain.UserAuths;
import com.AISubtitles.Server.service.RegistService;
import com.AISubtitles.common.CodeConsts;
import com.AISubtitles.common.StringConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistServiceImpl implements RegistService {


    @Autowired
    UserDao userDao;

    @Autowired
    UserAuthsDao userAuthsDao;

    @Override
    public Result findByUserPhoneNumber(String userPhoneNumber) {
        Result result = new Result();
        User byUserPhoneNumber = userDao.findByUserPhoneNumber(userPhoneNumber);
        if (byUserPhoneNumber != null) {
            result.setCode(CodeConsts.CODE_DUPLICATE_PHONE_NUMBER);
            result.setData("手机号已注册");
        } else {
            result.setCode(CodeConsts.CODE_CAN_CREATE_USER);
            result.setData("手机号未使用，可以创建");
        }
        return result;
    }


    @Override
    public Result findByUserEmail(String userEmail) {
        Result result = new Result();
        User byUserPhoneNumber = userDao.findByUserPhoneNumber(userEmail);
        if (byUserPhoneNumber != null) {
            result.setCode(CodeConsts.CODE_DUPLICATE_EMAIL);
            result.setData("手机号已注册");
        } else {
            result.setCode(CodeConsts.CODE_CAN_CREATE_USER);
            result.setData("邮箱未使用，可以创建");
        }
        return result;
    }


    @Override
    public Result regist(User user, UserAuths userAuths) {
        Result result = findByUserEmail(user.getUserEmail());
        if(result.getCode() == CodeConsts.CODE_CAN_CREATE_USER);
        else return result;
        result = findByUserPhoneNumber(user.getUserPhoneNumber());
        if(result.getCode() == CodeConsts.CODE_CAN_CREATE_USER);
        else return result;
        result = new Result();
        result.setCode(CodeConsts.CODE_SUCCESS);
        userDao.save(user);
        System.out.println(user);
        userAuths.setUserId(user.getUserId());
        userAuthsDao.save(userAuths);
        return result;
    }
}
