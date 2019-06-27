package com.egs.example.service.user;

import com.egs.example.data.internal.Credential;
import com.egs.example.data.model.User;
import com.egs.example.data.model.UserToken;

import java.util.List;

public interface UserService {

    User create(CreateUserRequest createUserRequest);

    User update(UpdateUserRequest updateUserRequest);

    void delete(String id);

    User getById(String id);

    List<User> getAll();

    User getByEmail(String email);

    User getByCredential(Credential credential);

    User updateStatus(UpdateUserRequest updateUserRequest);

     UserToken createToken(User user);

    void sendEmail(String email);

    UserToken createTokenForgotPassword(User user);

    void changePassword(String email, String password);

}