package com.AISubtitles.Server.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

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
public class UserController {

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

    // 对用户信息的增删改查
    @GetMapping(value = "user/motify4person")
    public Result motify4person(int userId, String fieldName, String newValue) {
        Result<User> result = new Result<User>();
        User user;
        try {
            user = userDao.findById(userId).get();
        } catch (NoSuchElementException e) {
            result.setCode(500);
            result.setStatus(608);
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
                user.setUserBirthday(simpleDateFormat.parse(newValue));
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
            result.setCode(500);
            result.setStatus(607);
            result.setData(null);
            return result;
        }

        add_user_modification_record(userId, fieldName, oldValue, newValue);
        result.setCode(200);
        result.setStatus(200);
        result.setData(userDao.findById(userId).get());
        return result;
    }

    @GetMapping("/test")
    public User insertUser(User user) {
        userDao.save(user);
        add_user_modification_record(user.getUserId(), "new", user.getUserName(), user.getUserName());
        return user;
    }
    @GetMapping("/test1")
    public User testUser(int userId) {
        try{
            System.out.println(userDao.findById(userId));
            return userDao.findById(userId).get();
        }catch(NoSuchElementException e) {
            return null;
        }

    }

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

    @PostMapping(value = "user/login")
    public String login(@RequestParam String userEmail,
                        @RequestParam String userPassword,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userDao.findByUserEmailAndUserPassword(userEmail,userPassword);
        if (user != null) {
            user.setUserPassword(null);
            session.setAttribute("user",user);
            return "user/index";
        }else {
            attributes.addFlashAttribute("message","y用户名密码错误");
            return "redirect:/user/login";
        }
    }

    @PostMapping(value = "user/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:user/login";
    }

}
