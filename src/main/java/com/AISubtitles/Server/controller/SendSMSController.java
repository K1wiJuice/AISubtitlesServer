package com.AISubtitles.Server.controller;

import java.net.URLEncoder;

import javax.annotation.Generated;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.AISubtitles.Server.config.SMSConfig;
import com.AISubtitles.Server.service.SendMailService;
import com.AISubtitles.Server.utils.HttpUtil;

/**
 * 
 * @author Gavin
 *
 */
@Controller
@RequestMapping("/SMS")
public class SendSMSController {

	@PostMapping("/sendSMSCode")
	@ResponseBody
	public String sendSMSCode(String phoneNumber, HttpSession session) throws Exception {
		StringBuilder sb = new StringBuilder();
		String code = generateSMSCode();
		session.setAttribute("code", code);
		sb.append("accountSid").append("=").append(SMSConfig.ACCOUNT_SID);
		sb.append("&to").append("=").append(phoneNumber);
		sb.append("&param").append("=").append(URLEncoder.encode(code,"UTF-8"));
		sb.append("&templateid").append("=").append("250972");
//		sb.append("&smsContent").append("=").append( URLEncoder.encode("【Ai字幕菌】您的验证码为123456，该验证码5分钟内有效。请勿泄漏于他人。","UTF-8"));
		String body = sb.toString() + HttpUtil.createCommonParam(SMSConfig.ACCOUNT_SID, SMSConfig.AUTH_TOKEN);
		String result = HttpUtil.post(SMSConfig.BASE_URL, body);
		System.out.println(result);
		return result;
	}

	public String generateSMSCode() {
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
