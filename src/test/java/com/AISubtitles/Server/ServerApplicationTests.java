package com.AISubtitles.Server;

import com.AISubtitles.Server.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    UserDao userDao;

    @Test
    public void testFind() {
        //查询一个存在的
        System.out.println(userDao.findById(1));

        //查询一个不存在的
        System.out.println(userDao.findById(46546546));

        System.out.println(userDao.findByUserPhoneNumber("123"));
        System.out.println(userDao.findByUserPhoneNumber("468465465465"));
    }

}
