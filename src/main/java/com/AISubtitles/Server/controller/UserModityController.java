package com.AISubtitles.Server.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import com.AISubtitles.common.CodeConsts;
import com.AISubtitles.Server.dao.UserAuthsDao;
import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.dao.UserModificationDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.domain.UserAuths;
import com.AISubtitles.Server.domain.UserModification;
import com.AISubtitles.Server.utils.Md5Utils;
import com.AISubtitles.Server.utils.TokenUtil;
import com.AISubtitles.Server.annotation.UserLoginToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javassist.Loader.Simple;

@RestController
public class UserModityController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserAuthsDao userAuthsDao;

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
    @UserLoginToken
    @PostMapping(value = "user/userModify")
    public Result modify(String userName, String userGender, String userBirthday, String userSignature) {
        Result result;
        result = modify1field("userName", userName);
        if (result.getCode() == CodeConsts.CODE_MODIFY_ERROR) return result;
        result = modify1field("userGender", userGender);
        if (result.getCode() == CodeConsts.CODE_MODIFY_ERROR) return result;
        result = modify1field("userBirthday", userBirthday);
        if (result.getCode() == CodeConsts.CODE_MODIFY_ERROR) return result;
        result = modify1field("userSignature", userSignature);
        return result;
    }

    @UserLoginToken
    @PostMapping(value = "user/userModifyEPP")
    public Result modify(String fieldName, String newValue) {
        return modify1field(fieldName, newValue);
    }

    private Result modify1field(String fieldName, String newValue) {
        Result result = new Result();
        if (newValue == null) {
            result.setCode(0);
            result.setData(newValue);
            return result;
        }

        User user;
        int userId = TokenUtil.getTokenUserId();
        try {
            user = userDao.findById(userId).get();
        } catch (NoSuchElementException e) {
            result.setCode(CodeConsts.CODE_MODIFY_ERROR);
            result.setData("no such user");
            return result;
        }

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
                if(user.getUserBirthday() == null) oldValue = "null";
                else oldValue = simpleDateFormat.format(user.getUserBirthday());
                user.setUserBirthday(new Date(simpleDateFormat.parse(newValue).getTime()));
            } else if (fieldName.equals("userSignature")) {
                oldValue = user.getUserSignature();
                user.setUserSignature(newValue);
            } else if (fieldName.equals("userEmail")) {
                oldValue = user.getUserEmail();
                if (userDao.findByUserEmail(newValue) == null) user.setUserEmail(newValue);
                else {
                    result.setCode(CodeConsts.CODE_DUPLICATE_EMAIL);
                    result.setData("邮箱重复");
                    return result;
                }
            } else if (fieldName.equals("userPhoneNumber")) {
                oldValue = user.getUserPhoneNumber();
                if (userDao.findByUserPhoneNumber(newValue) == null) user.setUserPhoneNumber(newValue);
                else {
                    result.setCode(CodeConsts.CODE_DUPLICATE_PHONE_NUMBER);
                    result.setData("手机号重复");
                    return result;
                }
            } else if(!fieldName.equals("userPassword")) throw new NullPointerException();
            userDao.saveAndFlush(user);
        } catch (Exception e) {
            //NullPointerException or ParseException
            result.setCode(CodeConsts.CODE_MODIFY_ERROR);
            result.setData(e.toString());
            return result;
        }

        if (fieldName.equals("userPassword")) {
            oldValue = newValue;
            UserAuths userAuths = userAuthsDao.findByUserId(userId);
            userAuths.setUserPassword(Md5Utils.md5(newValue));
            userAuthsDao.saveAndFlush(userAuths);
        }

        if(!oldValue.equals(newValue)) add_user_modification_record(userId, fieldName, oldValue, newValue);
        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setData(userDao.findById(userId));
        return result;
    }

}
