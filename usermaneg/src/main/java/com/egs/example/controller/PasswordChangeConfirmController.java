package com.egs.example.controller;



import com.egs.example.service.user.UserService;
import com.egs.example.service.user.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class PasswordChangeConfirmController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String token = request.getParameter("token");
        boolean check = validate(email, token);
        if (check) {
            request.setAttribute("email", email);
            getServletContext().getRequestDispatcher("/change-password-view").forward(request, response);

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