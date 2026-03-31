package com.favlist.repository;

import com.favlist.model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Item> findAll() {
        String sql = "SELECT * FROM item ORDER BY name";
        return jdbc.query(sql, itemRowMapper);
    }

    public Item findById(int id) {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        return jdbc.queryForObject(sql, itemRowMapper, id);
    }

    public List<Item> findByCategory(int categoryId) {
        String sql = "SELECT * FROM item WHERE category_id = ? ORDER BY name";
        return jdbc.query(sql, itemRowMapper, categoryId);
    }

    public int insert(Item item) {
        String sql = "INSERT INTO itm (name, description, category_id) VALUES (?, ?, ?)";
        return jdbc.update(sql, item.getName(), item.getDescription(), item.getCategoryId());
    }

    // update(Item item)
    public int update(Item item) {
        String sql = "UPDATE item SET name = ?, description = ?, category_id  ? WHERE item_id = ?";
        return jdbc.update(sql,
                item.getName(),
                item.getDescription(),
                item.getCategoryId(),
                item.getItemId());
    }

    // delete(int id)
    public int delete(int id) {
        String sql = "DELETE FROM item WHERE item_id = ?";
        return jdbc.update(sql, id);
    }
}
