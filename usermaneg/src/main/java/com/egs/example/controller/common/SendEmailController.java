package com.egs.example.controller.common;


import com.egs.example.data.model.User;
import com.egs.example.service.UserService;
import com.egs.example.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SendEmailController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean isValid = initAndValidate(request);
        if (isValid) {
            User user = userService.getByEmail(request.getParameter("email"));
            if (user == null) {
                request.setAttribute("message", "Invalid user");
                request.getRequestDispatcher("/forgot-password-view").forward(request, response);
            } else {
                userService.sendToken(user.getEmail());
                request.setAttribute("message", "Link sent successfully ");
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
