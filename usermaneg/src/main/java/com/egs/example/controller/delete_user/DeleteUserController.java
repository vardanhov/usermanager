package com.egs.example.controller.delete_user;

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
        userService.delete(request.getParameter("id"));
        response.sendRedirect("/users");
    }
}