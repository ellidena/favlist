package com.favlist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // this forces the test to use the test-properties configured for H2
    /*
    Alternatively this whole test can be disabled with @Disabled("Context load test not needed in CI")
    contextLoads() tests starts failing in CI as more tests, profiles,H2, thymeleaf templates, fragments, redirects, services calling repos are added
     */
class FavlistApplicationTests {

    @Test
    void contextLoads() {
    }

}
