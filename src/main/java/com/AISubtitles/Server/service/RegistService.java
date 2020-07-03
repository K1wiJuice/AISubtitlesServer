package com.AISubtitles.Server.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.domain.UserAuths;
import org.springframework.stereotype.Service;

@Service
public interface RegistService {


    Result findByUserPhoneNumber(String userPhoneNumber);

    Result findByUserEmail(String userEmail);

    Result regist(User user, UserAuths userAuths,
                  String emailCode, HttpServletResponse response, HttpSession session);
}
