package com.favlist.repository;

import com.favlist.model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {
    private final JdbcTemplate jdbc;

    public ItemRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
        Item i = new Item();
        i.setItemId(rs.getInt("item_id"));
        i.setName(rs.getString("name"));
        i.setDescription(rs.getString("description"));
        i.setCategoryId(rs.getInt("category_id"));
        return i;
    };
}
