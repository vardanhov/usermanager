package com.egs.example.controller.user;


import com.egs.example.data.model.TokenType;
import com.egs.example.data.model.User;
import com.egs.example.data.internal.UpdateUserRequest;
import com.egs.example.service.UserService;
import com.egs.example.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmailConfirmController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String token = request.getParameter("token");
        boolean isValid = validate(email, token);

        if (isValid) {
            User user = userService.getByEmail(email);
            if (user == null) {
                session.setAttribute("message", "Invalid request for email confirmation");
            } else if (user.getTokens() != null && token.equals(user.getTokens().get(TokenType.CONFIRM_EMAIL).getValue())) {
                UpdateUserRequest updateUserRequest = new UpdateUserRequest(user.getId(), user.getProfile(), user.getStatus(), user.getName(), user.getSurname());
                userService.updateStatus(updateUserRequest);
                session.setAttribute("message", "Email confirmed successfully");
            } else {
                session.setAttribute("message", "You must confirm email");
            }
        } else {
            session.setAttribute("message", "Invalid request for email confirmation");
        }
        response.sendRedirect("/login-view");
    }

    private boolean validate(String email, String token) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        if (StringUtils.isBlank(token)) {
            return false;
        }
        return true;
    }
}