package com.egs.example.controller.user;

import com.egs.example.data.model.User;
import com.egs.example.data.model.UserProfile;
import com.egs.example.data.model.UserStatus;
import com.egs.example.service.user.CreateUserRequest;
import com.egs.example.service.user.UserService;
import com.egs.example.service.user.exception.EmailAlreadyExistException;
import com.egs.example.service.user.impl.UserServiceImpl;
import com.egs.example.validation.EmailValidation;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        CreateUserRequest userRequest = initAndValidatePayload(request);
        if (request.getAttribute("errors") != null) {
            request.getRequestDispatcher("/register-view").forward(request, response);
            return;
        }

        try {
            UserService userService = new UserServiceImpl();
            User user = userService.create(userRequest);

            HttpSession session = request.getSession();
            session.setAttribute("message", "You are created successfully. Please confirm email");
            session.setAttribute("user", user);
            response.sendRedirect("/");

        } catch (EmailAlreadyExistException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("email", "Email already exists");
            request.getRequestDispatcher("/register-view").forward(request, response);
        }
    }

    private CreateUserRequest initAndValidatePayload(HttpServletRequest request) {
        CreateUserRequest userRequest = new CreateUserRequest();
        Map<String, String> errors = new HashMap<>();

        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            userRequest.setName(name.trim());
        } else {
            errors.put("name", "Name is required");
        }

        String surname = request.getParameter("surname");
        if (StringUtils.isNotBlank(surname)) {
            userRequest.setSurname(surname.trim());
        } else {
            errors.put("surname", "Surname is required");
        }

        String email = request.getParameter("email");
        if (!EmailValidation.valid(email)) {
            errors.put("email", "Try using valid email!");
        }

        if (StringUtils.isNotBlank(email)) {
            userRequest.setEmail(email.trim());
        } else {
            errors.put("email", "Email is required");
        }

        UserProfile profile = UserProfile.ofName(request.getParameter("profile"));
        if (profile != null) {
            userRequest.setProfile(profile);
        } else {
            errors.put("profile", "Profile is required");
        }

        userRequest.setStatus(UserStatus.EMAIL_NOT_CONFIRMED);

        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword)) {
            if (password.equals(confirmPassword)) {
                userRequest.setPassword(password);
            } else {
                errors.put("password", "Password and confirm password mismatches");
            }
        }
        if (StringUtils.isEmpty(password)) {
            errors.put("password", "Password is required");
        }
        if (StringUtils.isEmpty(confirmPassword)) {
            errors.put("confirm-password", "Confirm password is required");
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        }
        return userRequest;
    }
}
