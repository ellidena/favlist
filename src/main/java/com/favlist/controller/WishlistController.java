package com.favlist.controller;

import com.favlist.model.Item;
import com.favlist.model.Wishlist;
import com.favlist.model.WishlistEntry;
import com.favlist.service.UserService;
import com.favlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // Todo: Add form
    @PostMapping("/add")
    public String addItem(@RequestParam int itemId, @RequestParam String note) {
        int userId = 1;
        Wishlist wishlist = userService.getWishlistForUser(userId);

        wishlistService.addItem(wishlist.getWishlistId(), itemId, note);
        return "redirect:/wihlist";
    }

    @ResponseBody
    @GetMapping("/json")
    public List<WishlistEntry> showWishListJson(){
        Wishlist wishlist = userService.getWishlistForUser(1);
        return wishlistService.getEntries(wishlist.getWishlistId());
    }
}
