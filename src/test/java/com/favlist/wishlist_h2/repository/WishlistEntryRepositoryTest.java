package com.favlist.wishlist_h2.repository;

import com.favlist.model.WishlistEntry;
import com.favlist.repository.WishlistEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class WishlistEntryRepositoryTest {

    @Autowired
    private WishlistEntryRepository wishlistEntryRepository;

    @Test
    void findByWishlistId_returnsAllEntries() {
        List<WishlistEntry> entries = wishlistEntryRepository.findByWishlistId(1);

        assertThat(entries).hasSize(2);
    }

    @Test
    void findOne_returnsCorrectEntry() {
        WishlistEntry entry = wishlistEntryRepository.findOne(1, 1);

        assertThat(entry).isNotNull();
        assertThat(entry.getWishlistId()).isEqualTo(1);
        assertThat(entry.getItemId()).isEqualTo(1);
        assertThat(entry.getNote()).isEqualTo("Should read this decade");
    }
}
