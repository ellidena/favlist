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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    void insert_addsNewEntry() {
        WishlistEntry newEntry = new WishlistEntry();
        newEntry.setWishlistId(1);
        newEntry.setItemId(3);
        newEntry.setNote("Adding a new item to wishlist");

        int rows = wishlistEntryRepository.insert(newEntry);
        assertThat(rows).isEqualTo(1);

        WishlistEntry fetched = wishlistEntryRepository.findOne(1, 3);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getWishlistId()).isEqualTo(1);
        assertThat(fetched.getItemId()).isEqualTo(3);
        assertThat(fetched.getNote()).isEqualTo("Adding a new item to wishlist");
    }

    @Test
    void updateNote_updatesExistingEntry() {
        WishlistEntry entry = wishlistEntryRepository.findOne(1,1);
        entry.setNote("Updated note");

        int rows = wishlistEntryRepository.updateNote(entry);
        assertThat(rows).isEqualTo(1);

        WishlistEntry updated = wishlistEntryRepository.findOne(1, 1);
        assertThat(updated.getNote()).isEqualTo("Updated note");
    }

    @Test
    void delete_removesEntry() {
        int rows = wishlistEntryRepository.delete(1, 1);
        assertThat(rows).isEqualTo(1);

        assertThatThrownBy(() -> wishlistEntryRepository.findOne(1,1))
                .isInstanceOf(Exception.class);
    }
}
