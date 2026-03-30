package com.favlist.repository;

import com.favlist.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {
    private final JdbcTemplate jdbc;

    public CategoryRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Category> categoryRowMapper = (rs, rowNum) -> {
        Category c = new Category();
        c.setCategoryId(rs.getInt("category_id"));
        c.setName(rs.getString("name"));
        return c;
    };
}
