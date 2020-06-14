package com.AISubtitles.Server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author Gavin
 *
 */
@Service
public class SendMailService{

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendHtmlMail(String from, String to, String content) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject("Springboot 邮件测试  您的验证码为：");
        // 启用html
        mimeMessageHelper.setText(content, true);
        // 发送邮件
        System.out.println(content);
        mailSender.send(mimeMessage);
        System.out.println("邮件已发送");
    }

}
