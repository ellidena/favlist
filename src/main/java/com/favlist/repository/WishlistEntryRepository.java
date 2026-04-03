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

    // old rowmapper just in case, maps directly to wishlist_entry
    private final RowMapper<WishlistEntry> wishlistEntryRowMapper = (rs, rowNum) -> {
        WishlistEntry e = new WishlistEntry();
        e.setWishlistId(rs.getInt("wishlist_id"));
        e.setItemId(rs.getInt("item_id"));
        e.setNote(rs.getString("note"));
        return e;
    };

    // rowmapper for added item name field
    private final RowMapper<WishlistEntry> entryWithItemNameRowMapper = (rs, rowNum) -> {
        WishlistEntry e = new WishlistEntry();
        e.setWishlistId(rs.getInt("wishlist_id"));
        e.setItemId(rs.getInt("item_id"));
        e.setNote(rs.getString("note"));
        e.setItemName(rs.getString("item_name"));
        return e;
    };

    // return all items in a wishlist
    // A join statement to fetch item name data
    public List<WishlistEntry> findByWishlistId(int wishlistId) {
        String sql = """
            SELECT we.wishlist_id,
                   we.item_id,
                   we.note,
                   i.name AS item_name
            FROM wishlist_entry we
            JOIN item i ON we.item_id = i.item_id 
            WHERE we.wishlist_id = ?
            """;

        return jdbc.query(sql, entryWithItemNameRowMapper, wishlistId);
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
