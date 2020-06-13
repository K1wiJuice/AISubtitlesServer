package com.AISubtitles.Server.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import com.AISubtitles.common.CodeConsts;
import com.AISubtitles.common.StatusConsts;
import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.dao.UserModificationDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.domain.UserModification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javassist.Loader.Simple;

@RestController
public class UserModity {

    @Autowired
    UserDao userDao;

    @Autowired
    UserModificationDao userModificationDao;

    // 操作时间字段由数据库自动记录
    public void add_user_modification_record(int userId, String fieldName, String oldValue, String newValue) {
        UserModification um = new UserModification();
        um.setUserId(userId);
        um.setFieldName(fieldName);
        um.setOldValue(oldValue);
        um.setNewValue(newValue);
        userModificationDao.save(um);
    }

    // 对用户信息的修改
    @PostMapping(value = "user/userModify")
    public Result motify4person(HttpSession session, String fieldName, String newValue) {
        Result<User> result = new Result<User>();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        try {
            user = userDao.findById(userId).get();
        } catch (NoSuchElementException e) {
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setStatus(StatusConsts.STATUS_MODIFY_ERROR);
            result.setData(null);
            return result;
        }

        // userId，image, userPassword不在修改范围内
        String oldValue = "";
        try {
            if (fieldName.equals("userName")) {
                oldValue = user.getUserName();
                user.setUserName(newValue);
            } else if (fieldName.equals("userGender")) {
                oldValue = user.getUserGender();
                user.setUserGender(newValue);
            } else if (fieldName.equals("userBirthday")) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                oldValue = simpleDateFormat.format(user.getUserBirthday());
                user.setUserBirthday(new Date(simpleDateFormat.parse(newValue).getTime()));
            } else if (fieldName.equals("userEmail")) {
                oldValue = user.getUserEmail();
                user.setUserEmail(newValue);
            } else if (fieldName.equals("userPhoneNumber")) {
                oldValue = user.getUserPhoneNumber();
                user.setUserPhoneNumber(newValue);
            } else
                throw new NullPointerException();
            userDao.saveAndFlush(user);
        } catch (Exception e) {
            //NullPointerException or ParseException
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setStatus(StatusConsts.STATUS_MODIFY_ERROR);
            result.setData(null);
            return result;
        }

        add_user_modification_record(userId, fieldName, oldValue, newValue);
        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setStatus(StatusConsts.STATUS_SUCCESS);
        result.setData(userDao.findById(userId).get());
        return result;
    }

}
