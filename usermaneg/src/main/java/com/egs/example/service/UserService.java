package com.egs.example.service;

import com.egs.example.data.internal.CreateUserRequest;
import com.egs.example.data.internal.Credential;
import com.egs.example.data.internal.UpdateUserRequest;
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

    User getNotConfirmEmail(final String email);

    User getByCredential(Credential credential);

    User updateStatus(UpdateUserRequest updateUserRequest);

     UserToken createToken(User user);

    void sendToken(String email);

    UserToken createTokenForgotPassword(User user);

    UserToken createTokenChangeEmail(User user);

    void changePassword(String email, String password);

    User changeEmail(String id, String password);

    void sendTokenChangeEmail(User user ,String email);

    List<User> getUsers();

}