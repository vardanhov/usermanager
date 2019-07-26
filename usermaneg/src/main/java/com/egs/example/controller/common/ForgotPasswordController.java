package com.egs.example.controller.common;

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

public class ForgotPasswordController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {


        String newPass = request.getParameter("newPass");
        String confirmPass = request.getParameter("confirmPass");
        boolean check = initAndValidate(request, newPass, confirmPass);

        if (check) {
            HttpSession session = request.getSession();
            if (newPass.equals(confirmPass)) {
                User user = (User) session.getAttribute("user");
                userService.changePassword(user.getId(), newPass);
                session.setAttribute("message", "Password changed successfully");
                response.sendRedirect("/login-view");
            }
            if (!newPass.equals(confirmPass)) {
                session.setAttribute("message", "New  password field and Confirm password field must be match");
                request.getRequestDispatcher("/change-password-view").forward(request, response);
            }

        } else {
            request.getRequestDispatcher("/change-password-view").forward(request, response);
        }
    }

    private  boolean initAndValidate(HttpServletRequest request, String newPass, String confirmPass) {
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