package com.egs.example.controller.admin;

import com.egs.example.data.model.User;
import com.egs.example.service.user.UserService;
import com.egs.example.service.user.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        String userId = request.getParameter("id");
        if (userId != null) {
            User user = userService.getById(userId);
            if (user != null) {
                userService.delete(userId);
            }
        } else {
            request.setAttribute("message", "Invalid request for delete user");
        }
        request.getRequestDispatcher("/admin/users").forward(request, response);
    }
}