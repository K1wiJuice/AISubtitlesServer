package com.AISubtitles.Server.service;

import com.AISubtitles.Server.dao.UserRepository;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;
    private UserRepository userRepository;

    public Result regist(User user) {
        Result result = new Result();
        result.setCode(500);
        result.setData(null);

        try {
            //判断邮箱和手机号是否已经有人使用过
            User userBondEmail = userMapper.findUserByEmail(user.getUserEmail());
            User userBondPhoneNumber = userMapper.findUserByPhoneNumber(user.getUserPhoneNumber());
            if (userBondEmail != null) {
                result.setStatus(601);
                result.setData("该邮箱已存在");
            } else if (userBondPhoneNumber != null) {
                result.setStatus(602);
                result.setData("手机号已经存在");
            } else {
                userMapper.regist(user);
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
                User user = userRepository.findByUserNameAndPassword(useremail,userpassword);
        return user;
    }
}