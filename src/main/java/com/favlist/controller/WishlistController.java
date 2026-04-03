package com.favlist.controller;

import com.favlist.model.Item;
import com.favlist.model.Wishlist;
import com.favlist.model.WishlistEntry;
import com.favlist.service.UserService;
import com.favlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @GetMapping
    public String viewWishlist(Model model) {
        int userId = 1; // temporary until login exists
        Wishlist wishlist = userService.getWishlistForUser(userId);

        model.addAttribute("entries", wishlistService.getEntries(wishlist.getWishlistId()));
        return "wishlist/view";
    }

    @ResponseBody
    @GetMapping("/json")
    public List<WishlistEntry> showWishListJson(){
        Wishlist wishlist = userService.getWishlistForUser(1);
        return wishlistService.getEntries(wishlist.getWishlistId());
    }
}
