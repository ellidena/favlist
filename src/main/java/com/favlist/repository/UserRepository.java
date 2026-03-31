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

    public  User findById(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbc.queryForObject(sql, userRowMapper, id);
    }

    // Find a user by username (for login)
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbc.queryForObject(sql, userRowMapper, username);
    }

    // insert a new user

    // update a user (optional, add it later)

    // delete a user (should automatically delete their wishlist because of ON DELETE CASCADE
}
