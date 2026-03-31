package com.favlist.repository;

import com.favlist.model.WishlistEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistEntryRepository {
    private final JdbcTemplate jdbc;

    public WishlistEntryRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<WishlistEntry> wishlistEntryRowMapper = (rs, rowNum) -> {
        WishlistEntry e = new WishlistEntry();
        e.setWishlistId(rs.getInt("wishlist_id"));
        e.setItemId(rs.getInt("item_id"));
        e.setNote(rs.getString("note"));
        return e;
    };

    // return all items in a wishlist
    public List<WishlistEntry> findByWishlistId(int wishlistId) {
        String sql = "SELECT * FROM wishlist_entry WHERE wishlist_id = ?";
        return jdbc.query(sql, wishlistEntryRowMapper, wishlistId);
    }

}
