package com.egs.example.controller.forget_password;


import com.egs.example.data.model.TokenType;
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


public class PasswordChangeConfirmController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String token = request.getParameter("token");
        boolean check = validate(email, token);

        if (check) {
            User user = userService.getByEmail(email);
            if (user == null) {
                session.setAttribute("message", "Invalid user for change password");
                response.sendRedirect("/login-view");
            } else if (user.getTokens() != null && token.equals(user.getTokens().get(TokenType.FORGOT_PASSWORD).getValue())) {
                session.setAttribute("user", user);
                response.sendRedirect("/change-password-view");
            } else {
                session.setAttribute("message", "You must click link");
                response.sendRedirect("/login-view");
            }
        } else {
            session.setAttribute("message", "Invalid request for change password");
            response.sendRedirect("/login-view");
        }
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