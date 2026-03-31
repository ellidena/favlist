package com.favlist.wishlist_h2.repository;

import com.favlist.model.Wishlist;
import com.favlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void findById_returnsCorrectWishlist() {
        Wishlist wishlist = wishlistRepository.findById(1);
        assertThat(wishlist.getUserId()).isEqualTo(1);
    }

    @Test
    void findByUserId_returnsCorrectWishlist() {
        Wishlist w = wishlistRepository.findByUserId(1);
        assertThat(w.getWishlistId()).isEqualTo(1);
    }
}
