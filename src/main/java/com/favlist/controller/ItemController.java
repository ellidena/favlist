package com.favlist.controller;


import com.favlist.model.Item;
import com.favlist.model.WishlistEntry;
import com.favlist.service.ItemService;
import com.favlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final WishlistService wishlistService;

    public ItemController(ItemService itemService, WishlistService wishlistService) {
        this.itemService = itemService;
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public String listItems(
            @RequestParam(required = false) Integer category,
            Model model,
            HttpSession session
    ) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("items", itemService.getItems(category));
        model.addAttribute("categories", itemService.getAllCategories());
        model.addAttribute("selectedCategory", category);

        model.addAttribute("wishlistItemIds", wishlistService.getWishlistItemIds(userId));

        return "items/list";
    }

    @GetMapping("/{id}")
    public String itemDetails(
            @PathVariable int id,
            Model model,
            HttpSession session
    ) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("item", itemService.getItemDetails(id));
        // Now the details pge will know whether the item is already in the wishlist:
        model.addAttribute("wishlistItemIds", wishlistService.getWishlistItemIds(userId));

        WishlistEntry entry = wishlistService.getEntryForUser(userId, id);
        model.addAttribute("entryNote", entry != null ? entry.getNote() : "");

        return "items/details";
    }
}
