package com.AISubtitles.Server.service;

import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
// @Transactional(rollbackFor = RuntimeException.class)
public class UserService {

    @Autowired
    private UserDao userDao;

    public User test01(Integer userId) {
        return userDao.findById(userId).get();
    }

    public User test02(User user) {
        userDao.save(user);
        return user;
    }

    public Result regist(User user) {
        Result result = new Result();
        result.setCode(500);
        result.setData(null);

        try {
            //判断邮箱和手机号是否已经有人使用过
            User userBondEmail = userDao.findByUserEmail(user.getUserEmail());
            User userBondPhoneNumber = userDao.findByUserPhoneNumber(user.getUserPhoneNumber());
            if (userBondEmail != null) {
                result.setStatus(601);
                result.setData("该邮箱已存在");
            } else if (userBondPhoneNumber != null) {
                result.setStatus(602);
                result.setData("手机号已经存在");
            } else {
                userDao.save(user);
                result.setStatus(200);
                result.setCode(200);
                result.setData(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public User checkUser(String useremail, String userpassword) {
        User user = userDao.findByUserEmailAndUserPassword(useremail,userpassword);
        return user;
    }
}