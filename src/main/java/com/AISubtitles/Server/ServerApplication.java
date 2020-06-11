package com.AISubtitles.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		/*
		 * 在控制台中输出用户表的数据
		 */
		/*ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM 用户表");
        System.out.println(result);*/
	}

}
