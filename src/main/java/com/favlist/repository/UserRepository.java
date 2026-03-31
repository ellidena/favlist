package com.favlist.repository;

import com.favlist.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        return u;
    };

    // Find a user by ID

    // Find a user by username (for login)

    // insert a new user

    // update a user (optional, add it later)

    // delete a user (should automatically delete their wishlist because of ON DELETE CASCADE
}
