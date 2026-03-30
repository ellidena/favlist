package com.favlist.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistEntryRepository {
    private final JdbcTemplate jdbc;

    public WishlistEntryRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
