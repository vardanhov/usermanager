package com.egs.example.controller.common;

import com.egs.example.data.model.User;

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
        if (user.getProfile().getName().equals("Admin")) {
            chain.doFilter(request, response);
            return;
        }
    }

    @Override
    public void destroy() {
    }

}
