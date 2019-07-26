package com.egs.example.controller.user;

import com.egs.example.data.model.User;
import com.egs.example.data.model.UserProfile;
import com.egs.example.data.model.UserStatus;
import com.egs.example.data.internal.CreateUserRequest;
import com.egs.example.service.UserService;
import com.egs.example.exception.EmailAlreadyExistException;
import com.egs.example.service.impl.UserServiceImpl;
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

        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setStatus(UserStatus.EMAIL_NOT_CONFIRMED);
        userRequest.setName(request.getParameter("name").trim());
        userRequest.setSurname(request.getParameter("surname").trim());
        userRequest.setEmail(request.getParameter("email").trim());
        UserProfile userProfile = UserProfile.ofName(request.getParameter("profile"));
        userRequest.setProfile(userProfile);
        userRequest.setPassword(request.getParameter("password"));
        userRequest.setConfirmPassword(request.getParameter("confirm-password"));

        Map<String, String> isValid = validate(userRequest);
        if (!isValid.isEmpty()) {
            request.setAttribute("errors", isValid);
            request.getRequestDispatcher("/register-view").forward(request, response);
        } else {
            try {
                UserService userService = new UserServiceImpl();
                User user = userService.create(userRequest);

                HttpSession session = request.getSession();
                session.setAttribute("message", "You are created successfully. Please confirm email");
                session.setAttribute("user", user);
                response.sendRedirect("/");

            } catch (EmailAlreadyExistException e) {
                request.setAttribute("message", "Email already exist");
                request.getRequestDispatcher("/register-view").forward(request, response);
            }
        }
    }

    private Map<String, String> validate(CreateUserRequest userRequest) {

        Map<String, String> errors = new HashMap<>();

        if (StringUtils.isBlank(userRequest.getName())) {
            errors.put("name", "Name is required");
        }

        if (StringUtils.isBlank(userRequest.getSurname())) {
            errors.put("surname", "Surname is required");
        }

        if (!EmailValidation.valid(userRequest.getEmail())) {
            errors.put("email", "Try using valid email!");
        }

        if (StringUtils.isBlank(userRequest.getEmail())) {
            errors.put("email", "Email is required");
        }

        if (userRequest.getProfile()==null) {
            errors.put("profile", "Profile is required");
        }

        if (StringUtils.isBlank(userRequest.getPassword())) {
            errors.put("password", "Password is required");
        }

        if (StringUtils.isBlank(userRequest.getConfirmPassword())) {
            errors.put("confirm-password", "Confirm password is required");
        }

        if (StringUtils.isNotBlank(userRequest.getPassword()) && StringUtils.isNotBlank(userRequest.getConfirmPassword())) {
            if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
                errors.put("password", "Password and confirm password mismatches");
            }
        }
        return errors;
    }
}
