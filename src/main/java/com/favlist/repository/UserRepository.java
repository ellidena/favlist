package com.favlist.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Find a user by ID

    // Find a user by username (for login)

    // insert a new user

    // update a user (optional, add it later)

    // delete a user (should automatically delete their wishlist because of ON DELETE CASCADE
}
