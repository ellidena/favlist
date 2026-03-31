package com.favlist.repository;

import com.favlist.model.Wishlist;
import com.favlist.model.WishlistEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbc;

    public WishlistRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> {
        Wishlist w = new Wishlist();
        w.setWishlistId(rs.getInt("wishlist_id"));
        w.setUserId(rs.getInt("user_id"));
        return w;
    };

    public Wishlist findById(int id) {
        String sql = "SELECT * FROM wishlist WHERE wishlist_id = ?";
        return jdbc.queryForObject(sql, wishlistRowMapper, id);
    }

    public Wishlist findByUserId(int id) {
        String sql = "SELECT * FROM wishlist WHERE user_id = ?";
        return jdbc.queryForObject(sql, wishlistRowMapper, id);
    }

    public int insert(int userId) {
        String sql = "INSERT INTO wishlist (user_id) VALUES (?)";
        return jdbc.update(sql, userId);
    }

    public int deleteByUserId(int id) {
        String sql = "DELETE FROM wishlist WHERE user_id = ?";
        return jdbc.update(sql, id);
    }
}
