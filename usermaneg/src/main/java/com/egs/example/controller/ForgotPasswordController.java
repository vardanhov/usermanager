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

public class ForgotPasswordController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String newPass = request.getParameter("newPass");
        String confirmPass = request.getParameter("confirmPass");
        boolean check = initAndValidate(request, newPass, confirmPass);

        if (check) {
            if (newPass.equals(confirmPass)) {
                userService.changePassword(user.getId(), newPass);
                session.setAttribute("message", "Password changed successfully");
                response.sendRedirect("/welcome");
            }
            if (!newPass.equals(confirmPass)) {
                request.setAttribute("message", "New  password field and Confirm password field must be match");
                request.getRequestDispatcher("/change-password-view").forward(request, response);
            }
            request.setAttribute("message", "Password changed successfully");
            request.getRequestDispatcher("/login-view").forward(request, response);

        } else {
            request.getRequestDispatcher("/change-password-view").forward(request, response);
        }
    }

    boolean initAndValidate(HttpServletRequest request, String newPass, String confirmPass) {
        Map<String, String> errors = new HashMap<>();

        if (StringUtils.isBlank(newPass)) {
            errors.put("newPass", "New password is required");
        }
        if (StringUtils.isBlank(confirmPass)) {
            errors.put("confirmPass", "Confirm password is required");
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            return false;
        }
        return true;
    }
}