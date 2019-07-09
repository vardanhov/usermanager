package com.egs.example.controller.login;

import com.egs.example.data.internal.Credential;
import com.egs.example.data.model.User;
import com.egs.example.data.model.UserProfile;
import com.egs.example.data.model.UserStatus;
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
import java.util.List;
import java.util.Map;


public class LoginController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        Credential credential = initAndValidatePayload(request);
        if (credential == null) {
            request.getRequestDispatcher("/login-view").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        UserService userService = new UserServiceImpl();
        User user = userService.getByCredential(credential);
        if (user == null) {
            session.setAttribute("message", "Invalid username/password");
            request.getRequestDispatcher("/login-view").forward(request, response);
            return;
        }

        if (user.getStatus() == UserStatus.ACTIVE) {
            if (user.getProfile() == UserProfile.USER){
                session.setAttribute("user",  user);
                response.sendRedirect("welcome");
            } else if (user.getProfile() == UserProfile.ADMIN){
                response.sendRedirect("/users");
            }
            return;
        }
        if (user.getStatus() == UserStatus.EMAIL_NOT_CONFIRMED) {
            session.setAttribute("message", "Your email is not confirmed, please confirm it!");
            request.getRequestDispatcher("/login-view").forward(request, response);
            return;
        }
        if (user.getStatus() == UserStatus.DEACTIVATED) {
            session.setAttribute("message", "Your email is deactivated");
            request.getRequestDispatcher("/login-view").forward(request, response);
            return;
        }
        session.setAttribute("message", String.format("Your account is %s", user.getStatus().getName()));
        request.getRequestDispatcher("/login-view").forward(request, response);
    }

    /**
     * Initialize and validate credential data
     * if request is invalid then return null
     * otherwise return credential
     * @param request
     * @return {@link Credential}
     */
    private Credential initAndValidatePayload( HttpServletRequest request) {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Map<String, String> fieldErrors = new HashMap<>();
        if (StringUtils.isEmpty(email)) {
            fieldErrors.put("email", "Email is required");
        }
        if (StringUtils.isEmpty(password)) {
            fieldErrors.put("password", "Password is required");
        }

        if (fieldErrors.isEmpty()) {
            return new Credential(email, password);
        }
        request.setAttribute("errors", fieldErrors);
        return null;
    }
}
