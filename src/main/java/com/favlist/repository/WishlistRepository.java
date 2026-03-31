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
}
