package com.egs.example.data.dao.user;

import com.egs.example.data.dao.BaseDao;
import com.egs.example.data.model.User;
import com.egs.example.data.model.UserToken;

import java.util.List;

public interface UserDao {

    User create( User user) ;

    User update(User user);

    int delete(String id);

    User findById(String id);

    List<User> findAll();

    boolean existsByEmail(String email);

     void deleteAll();

    User getByEmail(String email);

    User getByCredential(String email, String password);

    User updateStatus(User user);

    User updateEmail(User user);

    User  updatePassword(String email, String password);

    void addToken(UserToken userToken);

    void commit();

    void rollback();

    void closeConnection();
}