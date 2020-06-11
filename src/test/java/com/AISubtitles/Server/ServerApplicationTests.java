package com.AISubtitles.Server;

import com.AISubtitles.Server.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

<<<<<<< HEAD
=======
import javax.sql.DataSource;
import java.sql.Connection;
>>>>>>> ee2c2b0cef44ee90ff420df110f29c39318c8171
import java.sql.SQLException;

@SpringBootTest
class ServerApplicationTests {

//	@Autowired
//	DataSource dataSource;

<<<<<<< HEAD
	@Test
	void contextLoads() throws SQLException {
//		System.out.println(dataSource.getClass());
=======
	@Autowired
	UserMapper userMapper;

	@Test
	void contextLoads() throws SQLException {
//		System.out.println(dataSource.getClass());
		System.out.println(userMapper.getClass());
		System.out.println(userMapper.findUserByEmail("1063676802@qq.com"));
>>>>>>> ee2c2b0cef44ee90ff420df110f29c39318c8171
		//System.out.println(userMapper.insertUser(new User(1,"1", "1")));
//
//		Connection connection = dataSource.getConnection();
//		System.out.println(connection);

//		connection.close();
	}
}
