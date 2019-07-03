package com.egs.example.service.user.impl;

import com.egs.example.data.dao.exception.DatabaseException;
import com.egs.example.data.dao.user.UserDao;
import com.egs.example.data.dao.user.impl.UserDaoImpl;
import com.egs.example.data.internal.Credential;
import com.egs.example.data.model.TokenType;
import com.egs.example.data.model.User;
import com.egs.example.data.model.UserStatus;
import com.egs.example.data.model.UserToken;
import com.egs.example.service.user.CreateUserRequest;
import com.egs.example.service.user.SendEmail;
import com.egs.example.service.user.UpdateUserRequest;
import com.egs.example.service.user.UserService;
import com.egs.example.service.user.exception.EmailAlreadyExistException;
import com.egs.example.service.user.exception.MailException;
import com.egs.example.service.user.exception.UserNotFoundException;

import java.util.*;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final SendEmail emailSender;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
        this.emailSender = SendEmail.getInstance();
    }

    @Override
    public User create(final CreateUserRequest createUserRequest) {
        checkIfExistsByEmail(createUserRequest.getEmail());

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        user.setProfile(createUserRequest.getProfile());
        user.setStatus(UserStatus.EMAIL_NOT_CONFIRMED);
        user.setName(createUserRequest.getName());
        user.setSurname(createUserRequest.getSurname());

        UserToken userToken = createToken(user);
        Map<TokenType, UserToken> tokens = new HashMap<>();
        tokens.put(TokenType.CONFIRM_EMAIL, userToken);
        user.setTokens(tokens);

        try {
            user = userDao.create(user);
            emailSender.sendEmailConfirmation(user.getEmail(), userToken.getValue());
            userDao.commit();
            return user;
        } catch (Exception e) {
            userDao.rollback();
            throw new DatabaseException();
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public User update(final UpdateUserRequest updateUserRequest) {

        final User user = getById(updateUserRequest.getId());
        user.setId(updateUserRequest.getId());
        user.setProfile(updateUserRequest.getProfile());
        user.setStatus(updateUserRequest.getStatus());
        user.setName(updateUserRequest.getName());
        user.setSurname(updateUserRequest.getSurname());
        return userDao.update(user);
    }

    @Override
    public UserToken createToken(User user) {
        UserToken emailToken = new UserToken();
        emailToken.setType(TokenType.CONFIRM_EMAIL);
        emailToken.setValue(UUID.randomUUID().toString());
        emailToken.setUserId(user.getId());
        return emailToken;
    }

    @Override
    public UserToken createTokenForgotPassword(User user) {
        UserToken emailToken = new UserToken();
        emailToken.setUserId(user.getId());
        emailToken.setType(TokenType.FORGOT_PASSWORD);
        emailToken.setValue(UUID.randomUUID().toString());
        return emailToken;
    }

    @Override
    public UserToken createTokenChangeEmail(User user) {
        UserToken emailToken = new UserToken();
        emailToken.setUserId(user.getId());
        emailToken.setType(TokenType.EMAIL_CHANGE);
        emailToken.setValue(UUID.randomUUID().toString());
        return emailToken;
    }


    @Override
    public User updateStatus(UpdateUserRequest updateUserRequest) {
        final User user = getById(updateUserRequest.getId());
        user.setId(updateUserRequest.getId());
        user.setProfile(updateUserRequest.getProfile());
        user.setStatus(UserStatus.ACTIVE);
        user.setName(updateUserRequest.getName());
        user.setSurname(updateUserRequest.getSurname());
        return userDao.updateStatus(user);
    }


    @Override
    public void delete(final String id) {
        int status = userDao.delete(id);
        if (status != 1) {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public User getById(final String id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> getAll() {
        final List<User> users = userDao.findAll();
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        return users;
    }

    @Override
    public User getByEmail(final String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public User getByCredential(Credential credential) {
        return userDao.getByCredential(credential.getEmail(), credential.getPassword());
    }

    @Override
    public void sendToken(String email) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        UserToken userToken = createTokenForgotPassword(user);
        try {
            if (user.getTokens() != null && user.getTokens().get(TokenType.FORGOT_PASSWORD) != null) {
                userDao.updateToken(userToken);
            } else {
                userDao.addToken(userToken);
            }
            emailSender.sendResetPassword(user.getEmail(), userToken.getValue());
            userDao.commit();
        } catch (Exception e) {
            userDao.rollback();
            throw new DatabaseException();
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public void changePassword(String id, String password) {
        userDao.updatePassword(id, password);
    }

    @Override
    public void changeEmail(String id, String newEmail) {
        userDao.updateEmail(id, newEmail);
    }

    private void checkIfExistsByEmail(final String email) {
        if (userDao.existsByEmail(email)) {
            throw new EmailAlreadyExistException(email);
        }
    }

//    public static void main(String[] args) {
//        List<? extends Number> ints = new ArrayList<>();
//        ints = new ArrayList<Integer>();
//
//        List list = new ArrayList();
//        list.add(1);
//        list.add(new Object[]{});
//
//        ints.add(3); ints.add(5); ints.add(10);
//        double sum = sum(ints);
//        System.out.println("Sum of ints="+sum);
//    }
//
//    public static double sum(List<? extends Number> list){
//        double sum = 0;
//        for(Number n : list){
//            sum += n.doubleValue();
//        }
//        return sum;
//
//    }
}