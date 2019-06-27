package com.egs.example.data.dao.user.mapper;

import com.egs.example.data.model.*;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static User map(final ResultSet resultSet) throws SQLException {
        return map(resultSet, false);
    }

    public static User map(final ResultSet resultSet, boolean readTokens) throws SQLException {
        User user = null;
        while (resultSet.next()) {
            if (user == null) {
                user = new User();
                user.setId(resultSet.getString("id"));

                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));

                user.setProfile(UserProfile.ofValue(resultSet.getInt("profile_id")));
                user.setStatus(UserStatus.ofValue(resultSet.getInt("status_id")));

                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
            }
            if (readTokens) {
                String tokenValue = resultSet.getString("value");
                if (StringUtils.isNotBlank(tokenValue)) {
                    int tokenType = resultSet.getInt("token_type_id");
                    UserToken token = new UserToken();
                    token.setType(TokenType.ofValue(tokenType));
                    token.setValue(tokenValue);
                    token.setUserId(user.getId());
                    user.addToken(token);
                }
            }
        }
        return user;
    }
}