package com.AISubtitles.Server.controller;

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

	@Value("${mail.fromMail.addr}")
	public String from;

	@PostMapping("/sendCode")
	@ResponseBody
	public String sendCode(String email, HttpSession session) throws MessagingException {
		StringBuilder sb = new StringBuilder();
		String code = getCode();
		session.setAttribute("code", code);
		sb.append("<html><head></head>");
		sb.append("<body><h1>你的验证码是： </h1><p>");
		sb.append(code);
		sb.append("</p></body>");
		sb.append("</html>");
		sendMailService.sendHtmlMail(from, email, sb.toString());
		return "success";
	}

	public String getCode() {
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
