package com.AISubtitles.Server.controller;

import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.common.CodeConsts;
import com.AISubtitles.common.StatusConsts;
import com.AISubtitles.common.StringConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
public class RegisterController {

    @Autowired
    UserDao userDao;


    @PostMapping(value = "user/regist")
    public Result handleRegist(User user, HttpSession session) {
        Result result = new Result();

        try {
            User userByPhoneNumber = userDao.findByUserPhoneNumber(user.getUserPhoneNumber());
            User userByEmail = userDao.findByUserEmail(user.getUserEmail());
            if (userByEmail != null) {
                result.setStatus(StatusConsts.STATUS_DUPLICATE_EMAIL);
                result.setData(StringConsts.EMAIL_EXIST);

            } else if (userByPhoneNumber != null)  {
                result.setStatus(StatusConsts.STATUS_DUPLICATE_PHONE_NUMBER);
                result.setData(StringConsts.PHONE_EXIST);

            } else {
                userDao.save(user);
                result.setStatus(StatusConsts.STATUS_SUCCESS);
                result.setCode(CodeConsts.CODE_SUCCESS);
                result.setData(user);
            }

        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setData(e.getCause());
            e.printStackTrace();
        }

        return result;
    }
}
