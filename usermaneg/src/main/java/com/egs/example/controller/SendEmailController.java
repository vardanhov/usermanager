package com.egs.example.controller;


import com.egs.example.data.model.User;
import com.egs.example.service.user.UserService;
import com.egs.example.service.user.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SendEmailController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean check = initAndValidate(request);
        if (check) {
            User user = userService.getByEmail(request.getParameter("email"));
            if (user == null) {
                request.setAttribute("message", "Invalid user");
                request.getRequestDispatcher("/forgot-password-view").forward(request, response);
            } else {
                request.setAttribute("message", "Link sent successfully ");
                userService.sendToken(user.getEmail());
                request.getRequestDispatcher("/login-view").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/forgot-password-view").forward(request, response);
        }
    }


    public boolean initAndValidate(HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        String email = request.getParameter("email");
        if (StringUtils.isEmpty(email)) {
            fieldErrors.put("email", "Email is required");
            request.setAttribute("errors", fieldErrors);
            return false;
        }
        return true;
    }
}
