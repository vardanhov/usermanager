package com.egs.example.controller.user;

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
import java.util.HashMap;
import java.util.Map;

public class ResetPasswordController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        String confirmPass = request.getParameter("confirmPass");
        boolean isValid = initAndValidate(request, oldPass, newPass, confirmPass);

        if (isValid) {
            if (user.getPassword().equals(oldPass)) {
                userService.changePassword(user.getId(), newPass);
                session.setAttribute("message", "Password changed successfully");
                response.sendRedirect("/user/welcome");
            } else {
                session.setAttribute("message", "Invalid old password");
                request.getRequestDispatcher("/user/reset-password-view").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/user/reset-password-view").forward(request, response);

        }
    }

    boolean initAndValidate(HttpServletRequest request, String oldPass, String newPass, String confirmPass) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(oldPass)) {
            errors.put("oldPass", "Old password is required");
        }
        if (StringUtils.isBlank(newPass)) {
            errors.put("newPass", "New password is required");
        }
        if (StringUtils.isBlank(confirmPass)) {
            errors.put("confirmPass", "Confirm password is required");
        }

        if (errors.isEmpty() && !newPass.equals(confirmPass)) {
            errors.put("newPass", "Password & Confirm password is mismatched");
        }

        if (errors.isEmpty() && oldPass.equals(newPass)) {
            errors.put("oldPass", "Old password and new password must be different");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        }
        return errors.isEmpty();
    }
}