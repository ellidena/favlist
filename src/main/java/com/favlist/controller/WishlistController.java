package com.favlist.controller;

import com.favlist.model.Wishlist;
import com.favlist.service.UserService;
import com.favlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String viewWishlist(Model model, HttpSession session) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        Wishlist wishlist = userService.getWishlistForUser(userId);
        model.addAttribute("entries", wishlistService.getEntries(wishlist.getWishlistId()));

        return "wishlist/view";
    }

    @PostMapping("/add")
    public String addItem(
            @RequestParam int itemId,
            @RequestParam(required = false) String note,
            @RequestParam(required = false) String redirect,
            HttpSession session
    ) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        Wishlist wishlist = userService.getWishlistForUser(userId);
        wishlistService.addItem(wishlist.getWishlistId(), itemId, note);

        return "redirect:" + (redirect != null ? redirect : "/items");
    }

    @PostMapping("remove")
    public String removeItem(@RequestParam int itemId,
                             @RequestParam(required = false) String redirect,
                             HttpSession session
    ) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        Wishlist wishlist = userService.getWishlistForUser(userId);
        wishlistService.removeItem(wishlist.getWishlistId(), itemId);

        return "redirect:" + (redirect != null ? redirect : "/wishlist");
    }

    @PostMapping("/update-note")
    public String updateNote(
            @RequestParam int itemId,
            @RequestParam String note,
            @RequestParam(required = false) String redirect,
            HttpSession session
    ) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        Wishlist wishlist = userService.getWishlistForUser(userId);
        wishlistService.updateNote(wishlist.getWishlistId(), itemId, note);

        return "redirect:" + (redirect != null ? redirect : "/wishlist");
    }

}
