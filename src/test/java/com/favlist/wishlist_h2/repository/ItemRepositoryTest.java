package com.favlist.wishlist_h2.repository;

import com.favlist.model.Item;
import com.favlist.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void findAll_returnsAllItems() {
        List<Item> items = itemRepository.findAll();

        assertThat(items)
                .isNotNull()
                .hasSize(3)
                .extracting(Item::getName)
                .containsExactly("Fancy Keyboard", "Kobo Clara", "The Hobbit");
    }

    @Test
    void findById_returnsCorrectItem() {
        Item item = itemRepository.findById(1);

        assertThat(item.getName()).isEqualTo("The Hobbit");
    }

    @Test
    void findByCategory_returnsFilteredItems() {
        List<Item> items = itemRepository.findByCategory(2);

        assertThat(items)
                .extracting(Item::getName)
                .containsExactly("Fancy Keyboard", "Kobo Clara");
    }
}
