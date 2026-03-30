package com.favlist.repository;

import com.favlist.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Category> findAll() {
        String sql = "SELECT * FROM category ORDER BY name";
        return jdbc.query(sql, categoryRowMapper);
    }

    public Category findById(int id) {
        String sql = "SELECT * FROM category WHERE category_id ? ?";
        return jdbc.queryForObject(sql, categoryRowMapper, id);
    }

    //For future version to add and edit categories:

    // insert(Category category)

    // update(Category category)

    // delete(int id)
}
