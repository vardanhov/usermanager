package com.egs.example.controller.user;

import com.egs.example.data.model.TokenType;
import com.egs.example.data.model.User;
import com.egs.example.service.UserService;
import com.egs.example.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EmailChangeConfirmController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = request.getParameter("email");
        String token = request.getParameter("token");
        boolean check = validate(email, token);

        if (check) {
            if (user == null) {
                session.setAttribute("message", "Invalid user for change email");
                response.sendRedirect("/user/edit-email-view");
            } else if (user.getTokens() != null && token.equals(user.getTokens().get(TokenType.EMAIL_CHANGE).getValue())) {
                userService.changeEmail(user.getId(), email);
                session.setAttribute("user", user);
                response.sendRedirect("/user/welcome");
            } else {
                session.setAttribute("message", "You must click link");
                response.sendRedirect("/user/edit-email-view");
            }
        } else {
            session.setAttribute("message", "Invalid request for change email");
            response.sendRedirect("/user/edit-email-view");
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