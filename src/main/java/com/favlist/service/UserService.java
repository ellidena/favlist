package com.favlist.service;

import com.favlist.model.User;
import com.favlist.repository.UserRepository;
import com.favlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    /*
    finding a user
    creating a user
    getting a user's wishlist
    uses:
    UserRepo, WishlistRepo
     */
    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;

    public UserService(
            UserRepository userRepository,
            WishlistRepository wishlistRepository
    ) {
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
    }

    // create a user and their wishlist
    public void createUser(User user) {
        userRepository.insert(user);
        wishlistRepository.insert(user.getUserId());
    }
}
