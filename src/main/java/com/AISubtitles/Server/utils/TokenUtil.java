package com.AISubtitles.Server.utils;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class TokenUtil {
    
    public static int getTokenUserId() {
        String token = getRequest().getHeader("token");
        int userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));
        return userId;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder
                                                      .getRequestAttributes();

        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}