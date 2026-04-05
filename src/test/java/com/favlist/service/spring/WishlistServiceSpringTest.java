package com.favlist.service.spring;

import com.favlist.service.UserService;
import com.favlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


/*
integration version
loads Spring context, real repositories, real H2 database and real SQL seed data
 */
@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class WishlistServiceSpringTest {

    // Inject the real services
    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserService userService;

    @Test
    void addItem_addsNewItem() {
        int userId = 1;
        int wishlistId = userService.getWishlistForUser(userId).getWishlistId();

        wishlistService.addItem(wishlistId, 3, "note");

        assertThat(wishlistService.containsItem(wishlistId, 3)).isTrue();
    }


}
