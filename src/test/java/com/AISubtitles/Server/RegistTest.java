package com.AISubtitles.Server;


import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.domain.UserAuths;
import com.AISubtitles.Server.service.RegistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

@SpringBootTest
public class RegistTest {

    @Autowired
    RegistService registService;


    @Test
    public void testRegist() {
        User user = new User();
        user.setImage("");
        user.setUserName("byc");
        user.setUserGender("male");
        user.setUserPhoneNumber("15665835082");
        user.setUserEmail("1063676802@qq.com");
        user.setUserBirthday(new Date(System.currentTimeMillis()));

        UserAuths userAuths = new UserAuths();
        userAuths.setUserId(user.getUserId());
        userAuths.setUserPassword("byc123");
        userAuths.setLoginType("email");

        System.out.println(registService.findByUserEmail(user.getUserEmail()));
        System.out.println(registService.findByUserPhoneNumber(user.getUserPhoneNumber()));

        registService.regist(user, userAuths);

    }
}
