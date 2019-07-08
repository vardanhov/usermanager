package com.egs.example.data.dao.user.mapper;

import com.egs.example.data.model.*;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User map(final ResultSet resultSet) throws SQLException {
        return map(resultSet, false);
    }

    public static User map(final ResultSet resultSet, boolean readTokens) throws SQLException {
        return resultSet.next() ? mapUser(resultSet, readTokens) : null;
    }

    public static List<User> mapAsList(final ResultSet resultSet) throws SQLException {
        return mapAsList(resultSet, false);
    }

    public static List<User> mapAsList(final ResultSet resultSet, boolean readTokens) throws SQLException {
        final List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            final User user = mapUser(resultSet, readTokens);
            users.add(user);
        }
        return users;
    }

    private static User mapUser(ResultSet resultSet, boolean readTokens) throws SQLException {
        final User user = new User();

        user.setId(resultSet.getString("id"));

        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));

        user.setProfile(UserProfile.ofValue(resultSet.getInt("profile_id")));
        user.setStatus(UserStatus.ofValue(resultSet.getInt("status_id")));

        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));

        if (readTokens) {
            final String tokenValue = resultSet.getString("value");
            if (StringUtils.isNotBlank(tokenValue)) {
                final UserToken token = new UserToken();
                token.setValue(tokenValue);
                token.setUserId(user.getId());
                token.setType(TokenType.ofValue(resultSet.getInt("token_type_id")));
                user.addToken(token);
            }
        }
        return user;
    }
}