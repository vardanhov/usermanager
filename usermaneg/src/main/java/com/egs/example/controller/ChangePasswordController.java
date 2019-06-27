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

public class ChangePasswordController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {


        String email = (String) request.getAttribute("email");
        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        String confirmPass = request.getParameter("confirmPass");
        boolean check = initAndValidate(request, oldPass, newPass, confirmPass);

        if (check) {
            if (!oldPass.equals(newPass) && newPass.equals(confirmPass)) {
                userService.changePassword(email, newPass);
                request.setAttribute("message", "Password changed successfully");
                request.getRequestDispatcher("/login-view").forward(request, response);
            }
            if (oldPass.equals(newPass)){
                request.setAttribute("message", "Old password and new password must be different");
                request.getRequestDispatcher("/change-password-view").forward(request, response);
            }
            if (!newPass.equals(confirmPass)){
                request.setAttribute("message", "New  password field and Confirm password field must be match");
                request.getRequestDispatcher("/change-password-view").forward(request, response);
            }
            request.setAttribute("message", "Password changed successfully");
            request.getRequestDispatcher("/login-view").forward(request, response);

        } else {
            request.getRequestDispatcher("/change-password-view").forward(request, response);
        }
    }

    boolean initAndValidate(HttpServletRequest request, String oldPass, String newPass, String confirmPass) {

        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(oldPass)) {
            errors.put("oldPass", "Old password is required");
            return false;
        }
        if (StringUtils.isBlank(newPass)) {
            errors.put("newPass", "New password is required");
            return false;
        }
        if (StringUtils.isBlank(confirmPass)) {
            errors.put("confirmPass", "Confirm password is required");
            return false;
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        }
        return true;
    }
}