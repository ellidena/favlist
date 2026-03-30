package com.favlist.wishlist_h2.repository;

import com.favlist.model.Category;
import com.favlist.repository.CategoryRepository;
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
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findAll_returnsAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        assertThat(categories)
                .isNotNull()
                .hasSize(2)
                .extracting(Category::getName)
                .containsExactly("Books", "Electronics");
    }
}
