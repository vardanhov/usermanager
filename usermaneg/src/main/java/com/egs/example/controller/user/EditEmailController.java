package com.egs.example.controller.user;

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

public class EditEmailController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.getAttribute("user");
        User user = (User) session.getAttribute("user");
        String email = request.getParameter("email");
        String newEmail = request.getParameter("newEmail");
        boolean check = validate(request, email, newEmail);
        if(check){
            userService.sendTokenChangeEmail(user,newEmail);
            response.sendRedirect("/welcome");
        }
    }

    private boolean validate(HttpServletRequest request, String email, String newEmail ) {

        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isEmpty(email)) {
            errors.put("email", "Old Email is required");
        }
        if (StringUtils.isEmpty(newEmail)) {
            errors.put("newEmail", "New Email is required");
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            return false;
        }
        return true;
    }
}