package com.AISubtitles.Server.controller;

import javax.annotation.Generated;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.AISubtitles.Server.service.SendMailService;

/**
 * 
 * @author Gavin
 *
 */
@Controller
@RequestMapping("/email")
public class SendEmailController {

	@Autowired
	SendMailService sendMailService;

	//@Value("${mail.fromMail.addr}")----此处对应properties文件配置
	@Value("${spring.mail.from}")
	public String from;

	@PostMapping("/sendCode")
	@ResponseBody
	public String sendCode(String email, HttpSession session) throws MessagingException {
		StringBuilder strbd = new StringBuilder();
		String code = generateEmailCode();
		session.setAttribute("code", code);
		strbd.append("<html><head></head>");
		strbd.append("<body><h1>你的验证码是： </h1><p>");
		strbd.append(code);
		strbd.append("</p></body>");
		strbd.append("</html>");
		sendMailService.sendHtmlMail(from, email, strbd.toString());
		return "success";
	}

	public String generateEmailCode() {
		int codeLength = 6;
		StringBuilder builder = new StringBuilder();
		int[] random = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		for (int i = 0; i < codeLength; i++) {
			int index = (int) (Math.random() * 10);
			builder.append(random[index]);
		}
		String code = builder.toString();
		return code;
	}
}
