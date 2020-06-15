package com.AISubtitles.Server.config;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.AISubtitles.Server.annotation.PassToken;
import com.AISubtitles.Server.annotation.UserLoginToken;
import com.AISubtitles.Server.dao.UserAuthsDao;
import com.AISubtitles.Server.domain.UserAuths;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserAuthsDao userAuthsDao;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");
        if(!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)object;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        if (method.isAnnotationPresent(UserLoginToken.class)) {

            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                if (token == null) throw new RuntimeException("无token，请重新登录");
            }

            String userId;
            try {
                userId = JWT.decode(token).getAudience().get(0);
            } catch (JWTDecodeException e) {
                throw new RuntimeException("401");
            }
            UserAuths userAuths = userAuthsDao.findByUserId(Integer.parseInt(userId));
            if (userAuths == null) {
                throw new RuntimeException("用户不存在，请重新登录");
            }

            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(userAuths.getUserPassword())).build();
            try {
                jwtVerifier.verify(token);
            } catch (JWTVerificationException e) {
                throw new RuntimeException("401");
            }
            return true;
        }

        return true;

    }
        


    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object object, ModelAndView modelAndView) throws Exception {
    
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object object, Exception e) throws Exception {
    
    }

}