package com.egs.example.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthenticationFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
//        String loginURI = request.getContextPath() + "/login";
//
//        boolean loggedIn = session != null && session.getAttribute("user") != null;
//        boolean loginRequest = request.getRequestURI().equals(loginURI);

        if (session.getAttribute("user")!=null) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect("login-view");
        }
    }
    @Override
    public void destroy() {}


}