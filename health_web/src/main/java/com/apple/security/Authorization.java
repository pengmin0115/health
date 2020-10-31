package com.apple.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author pengmin
 */

public class Authorization implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if(exception.getMessage().equals("UserDetailsService returned null, which is an interface contract violation")){
            session.setAttribute("value", "用户名不存在");
            //System.out.println("用户名不存在");
            response.sendRedirect("/login.html?error=" + "username");
        }
        if(exception.getMessage().equals("Bad credentials")){
            session.setAttribute("value", "密码错误");
            //System.out.println("密码错误");
            response.sendRedirect("/login.html?error=" + "password");
        }
    }
}