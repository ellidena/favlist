package com.favlist.repository;

import com.favlist.model.Wishlist;
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

    // return one entry, useful for editing the note
    public WishlistEntry findOne(int wishlistId, int itemId) {
        String sql = "SELECT * FROM wishlist_entry WHERE wishlist_id = ? AND item_id = ?";
        return jdbc.queryForObject(sql, wishlistEntryRowMapper, wishlistId, itemId);
    }

    public int insert(WishlistEntry entry) {
        String sql = "INSERT INTO wishlist_entry (wishlist_id, item_id, note) VALUES (?, ?, ?)";
        return jdbc.update(sql, entry.getWishlistId(), entry.getItemId(), entry.getNote());
    }

    public int updateNote(WishlistEntry entry) {
        String sql = "UPDATE wishlist_entry SET note = ? WHERE wishlist_id = ? AND item_id = ?";
        return jdbc.update(sql, entry.getNote(), entry.getWishlistId(), entry.getItemId());
    }

    public int delete(int wishlistId, int itemId) {
        String sql = "DELETE FROM wishlist_entry WHERE wishlist_id = ? AND item_id = ?";
        return jdbc.update(sql, wishlistId, itemId);
    }

}
