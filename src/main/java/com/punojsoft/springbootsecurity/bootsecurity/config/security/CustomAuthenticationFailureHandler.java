package com.punojsoft.springbootsecurity.bootsecurity.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.punojsoft.springbootsecurity.bootsecurity.config.security.event.OnLoginAttemptFailedEvent;
import com.punojsoft.springbootsecurity.bootsecurity.model.User;
import com.punojsoft.springbootsecurity.bootsecurity.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static int loginAttempt = 0;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        loginAttempt++;
        httpServletRequest.getSession().setAttribute("loginAttempt", loginAttempt);
        if (loginAttempt > 3) {
            System.out.println("Your account is temporarly blocked");
            User user = User.builder()
                    .username(httpServletRequest.getParameter("username"))
                    .password(httpServletRequest.getParameter("password")).build();

            eventPublisher.publishEvent(new OnLoginAttemptFailedEvent(null, AppUtils.getAppURI(httpServletRequest), httpServletRequest.getLocale(), user, httpServletResponse));
            return;
        }
        System.out.println("loginattempt " + loginAttempt);
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date().toString());
        data.put("status", "Authentication Failed");
        data.put("message", "Authentication Failed!");
        httpServletResponse.getOutputStream().println(new ObjectMapper().writeValueAsString(data));

    }

}
