package com.favlist.wishlist_h2.repository;

import com.favlist.model.User;
import com.favlist.repository.UserRepository;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findById_returnsCorrectUser() {
        User user = userRepository.findById(1);

        assertThat(user.getName()).isEqualTo("Daniella Norgren");
    }

    @Test
    void findByUsername_returnsCorrectUser() {
        User user = userRepository.findByUsername("dani");

        assertThat(user.getUsername()).isEqualTo("dani");
    }

    @Test
    void insert_addsNewUser() {
        User newUser = new User();
        newUser.setName("Gabriella");
        newUser.setUsername("bella");
        newUser.setPassword("pw");

        int rows = userRepository.insert(newUser);
        assertThat(rows).isEqualTo(1);

        User fetched = userRepository.findByUsername("bella");
        assertThat(fetched.getName()).isEqualTo("Gabriella");
    }
}
