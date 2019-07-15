package com.egs.example.controller.admin;

import com.egs.example.data.model.User;
import com.egs.example.service.UserService;
import com.egs.example.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ListUsersController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();


    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        final List<User> users = userService.getUsers();
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/pages/users.jsp");
        dispatcher.forward(request, response);

    }
}