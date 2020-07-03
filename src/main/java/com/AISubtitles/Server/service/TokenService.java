package com.AISubtitles.Server.service;

import java.util.Date;

import com.AISubtitles.Server.domain.UserAuths;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.stereotype.Service;

@Service("TokenService")
public class TokenService {
    public String getToken(UserAuths userAuths) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60*60*1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(String.valueOf(userAuths.getUserId())).withIssuedAt(start).withExpiresAt(end)
                            .sign(Algorithm.HMAC256(userAuths.getUserPassword()));
        return token;
    }

}