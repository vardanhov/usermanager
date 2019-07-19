package com.egs.example.controller.filter;

import com.egs.example.data.model.User;
import com.egs.example.data.model.UserProfile;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminAuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        session.getAttribute("user");
        User user = (User) session.getAttribute("user");
        if (user != null && user.getProfile() == UserProfile.ADMIN) {
            chain.doFilter(request, response);
        } else {
            session.setAttribute("message","Invalid request");
            response.sendRedirect(String.format("%s/login-view", request.getContextPath()));
        }
    }

    @Override
    public void destroy() {
    }

}
