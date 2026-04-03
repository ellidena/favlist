package com.favlist.controller;

import com.favlist.service.UserService;
import com.favlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;

    public WishlistController(
            WishlistService wishlistService,
            UserService userService
    ) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }


}
