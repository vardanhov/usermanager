package com.egs.example.controller;

import com.egs.example.data.model.User;
import com.egs.example.service.user.UpdateUserRequest;
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

public class EditEmailController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.getAttribute("user");
        User user =(User)session.getAttribute("user");
        UpdateUserRequest updateUserRequest =initAndValidatePayload(request, user);
        UserService userService = new UserServiceImpl();
//        userService.update(updateUserRequest);
        response.sendRedirect("/welcome");
    }

    private UpdateUserRequest initAndValidatePayload(HttpServletRequest request, User user) {

        String name = request.getParameter("oldEmail");
        String surname = request.getParameter("newEmail");


        UpdateUserRequest updateUserRequest = null;
        Map<String, String> fieldErrors = new HashMap<>();
        if (StringUtils.isEmpty(name)) {
            fieldErrors.put("oldEmail", "Old Email is required");
        }

        if (StringUtils.isEmpty(surname)) {
            fieldErrors.put("newEmail", "New Email is required");
        }
        if (fieldErrors.isEmpty()) {
            updateUserRequest = new UpdateUserRequest(user.getId(),user.getProfile(),  user.getStatus(), name, surname );

        }
        request.setAttribute("errors", fieldErrors);
        return updateUserRequest;
    }
}