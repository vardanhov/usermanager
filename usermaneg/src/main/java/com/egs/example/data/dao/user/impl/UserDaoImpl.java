package com.egs.example.data.dao.user.impl;

import com.egs.example.data.dao.BaseDao;
import com.egs.example.data.dao.exception.DatabaseException;
import com.egs.example.data.dao.user.UserDao;
import com.egs.example.data.dao.user.mapper.UserMapper;
import com.egs.example.data.dao.util.ConnectionProvider;
import com.egs.example.data.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDao implements UserDao {

    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = ? ";

    private static final String DELETE_USER = "DELETE FROM user WHERE id =?";

    private static final String DELETE_USER_TOKEN = "DELETE FROM user_token WHERE user_id =?";

    private static final String UPDATE_USER = "UPDATE user SET name = ?, surname = ? where id = ?";

    private static final String UPDATE_STATUS = "UPDATE user SET status_id =? where id = ?";

    private static final String UPDATE_PASSWORD = "UPDATE user SET password =? where id = ?";

    private static final String UPDATE_EMAIL = "UPDATE user SET email = ? ,new_email =? where id = ?";

    private static final String INSERT_USER = "INSERT INTO user (id, profile_id, status_id, email, password, name, surname) VALUES(?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_ALL = "SELECT * FROM user";

    private static final String GET_ALL_USER = "SELECT * FROM user  where profile_id=?";

    private static final String GET_USER_BY_EMAIL = "SELECT * FROM user u LEFT JOIN  user_token ut ON u.id=ut.user_id  WHERE u.email = ? ";

    private static final String GET_USER_BY_CREDENTIAL = "SELECT * FROM user WHERE email = ? AND password = ?";

    private static final String DELETE_ALL_USERS = "DELETE FROM USER";

    private static final String INSERT_USER_TOKEN = "INSERT INTO user_token (user_id, token_type_id, value) VALUES(?, ?, ?)";

    private static final String UPDATE_USER_TOKEN = "UPDATE user_token SET value = ? WHERE user_id = ? AND token_type_id= ?";

    private static final String SET_NEW_EMAIL = "UPDATE user SET not_confirm_email =? where id = ?";


    @Override
    public User create(final User user) {

        Connection connection;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection(false);
            currentConnection.set(connection);

            preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getId());
            preparedStatement.setInt(2, user.getProfile().getValue());
            preparedStatement.setInt(3, user.getStatus().getValue());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getName());
            preparedStatement.setString(7, user.getSurname());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(INSERT_USER_TOKEN);

            UserToken userToken = user.getTokens().get(TokenType.CONFIRM_EMAIL);
            preparedStatement.setString(1, userToken.getUserId());
            preparedStatement.setInt(2, userToken.getType().getValue());
            preparedStatement.setString(3, userToken.getValue());
            preparedStatement.executeUpdate();

            return user;
        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to create user. User: %s", user);
            throw new DatabaseException(message, ex);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public User update(final User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionProvider.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_USER);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getId());

            preparedStatement.executeUpdate();


            return user;
        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to update user. User: %s", user);
            throw new DatabaseException(message, ex);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public User updateStatus(User user) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        final User findedUser = findById(user.getId());

        try {
            connection = ConnectionProvider.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_STATUS);

            preparedStatement.setInt(1, user.getStatus().getValue());
            preparedStatement.setString(2, user.getId());

            preparedStatement.executeUpdate();
            final User result = findById(user.getId());

            return result;
        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to update user status. User: %s", findedUser);
            throw new DatabaseException(message, ex);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public User updateEmail(String id, String newEmail) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        final User findedUser = findById(id);

        try {
            connection = ConnectionProvider.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_EMAIL);

            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, null);
            preparedStatement.setString(3, id);

            preparedStatement.executeUpdate();
            final User result = findById(id);

            return result;
        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to update user email. User: %s", findedUser);
            throw new DatabaseException(message, ex);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public User setNewEmail(String id, String newEmail) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        final User findedUser = findById(id);

        try {
            connection = currentConnection.get();
            preparedStatement = connection.prepareStatement(SET_NEW_EMAIL);

            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, id);

            preparedStatement.executeUpdate();
            final User result = findById(id);

            return result;
        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to update user email. User: %s", findedUser);
            throw new DatabaseException(message, ex);
        } finally {
            close(preparedStatement);

        }
    }


    @Override
    public User updatePassword(String id, String password) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        final User findedUser = findById(id);

        try {
            connection = ConnectionProvider.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, findedUser.getId());

            preparedStatement.executeUpdate();
            final User result = findById(id);

            return result;
        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to update user password. User: %s", findedUser);
            throw new DatabaseException(message, ex);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public int delete(final String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(DELETE_USER);

            preparedStatement.setString(1, id);

            return preparedStatement.executeUpdate();
        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to delete user. User id: %s", id);
            throw new DatabaseException(message, ex);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }



    @Override
    public User findById(final String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            return UserMapper.map(resultSet);
        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to find user. User id: %s", id);
            throw new DatabaseException(message, ex);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public List<User> findAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL);
            return mapAsList(resultSet);
        } catch (final SQLException ex) {
            throw new DatabaseException("Something went wrong when trying to find all users.", ex);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<User> getAllUsers(int value) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(GET_ALL_USER);
            preparedStatement.setInt(1, value);

            resultSet = preparedStatement.executeQuery();

            return UserMapper.mapAsList(resultSet);
        } catch (final SQLException ex) {
            throw new DatabaseException("Something went wrong when trying to find users.", ex);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
    }


    @Override
    public boolean existsByEmail(final String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (final SQLException ex) {
            final String message = String.format("Find email error. email: %s.", email);
            throw new DatabaseException(message, ex);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public void deleteAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(DELETE_ALL_USERS);

            preparedStatement.executeUpdate();


        } catch (final SQLException ex) {

        } finally {

            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public User getByEmail(String email) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            return UserMapper.map(resultSet, true);
        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to find user:  %s", email);
            throw new DatabaseException(message, ex);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public User getByCredential(String email, String password) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_CREDENTIAL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return UserMapper.map(resultSet);
        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to find user:  %s", email);
            throw new DatabaseException(message, ex);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public void addToken(UserToken userToken) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection(false);
            currentConnection.set(connection);

            preparedStatement = connection.prepareStatement(INSERT_USER_TOKEN);

            preparedStatement.setString(1, userToken.getUserId());
            preparedStatement.setInt(2, userToken.getType().getValue());
            preparedStatement.setString(3, userToken.getValue());

            preparedStatement.executeUpdate();

        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to add: %s", userToken);
            throw new DatabaseException(message, ex);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public void updateToken(UserToken userToken) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionProvider.getInstance().getConnection(false);
            currentConnection.set(connection);

            preparedStatement = connection.prepareStatement(UPDATE_USER_TOKEN);

            preparedStatement.setString(1, userToken.getValue());
            preparedStatement.setString(2, userToken.getUserId());
            preparedStatement.setInt(3, userToken.getType().getValue());

            preparedStatement.executeUpdate();

        } catch (final SQLException ex) {
            final String message = String.format("Something went wrong when trying to update: %s", userToken);
            throw new DatabaseException(message, ex);
        } finally {
            close(preparedStatement);
        }
    }
    private static List<User> mapAsList(final ResultSet resultSet) throws SQLException {
        final List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(UserMapper.map(resultSet));
        }
        return users;
    }


}
