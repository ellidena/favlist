package com.favlist.repository;

import com.favlist.model.WishlistEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

}
