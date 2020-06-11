package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserDao userDao;


    @PostMapping(value = "user/regist")
    public Result handleRegist(User user) {
        Result result = new Result();
        result.setCode(500);
        result.setData(null);

        try {
            User userByPhoneNumber = userDao.findByUserPhoneNumber(user.getUserPhoneNumber());
            User userByEmail = userDao.findByUserEmail(user.getUserEmail());
            if (userByEmail != null) {
                result.setStatus(601);
                result.setData("该邮箱已存在");
            } else if (userByPhoneNumber != null)  {
                result.setStatus(602);
                result.setData("手机号已存在");
            } else {
                userDao.save(user);
                result.setStatus(200);
                result.setCode(200);
                result.setData(user);
            }
        } catch (Exception e) {
            result.setData(e.getCause());
            e.printStackTrace();
        }

        return result;
    }

}
