package com.favlist.controller;


import com.favlist.model.Item;
import com.favlist.model.WishlistEntry;
import com.favlist.service.ItemService;
import com.favlist.service.WishlistService;
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
            Model model
    ) {
        model.addAttribute("items", itemService.getItems(category));
        model.addAttribute("categories", itemService.getAllCategories());
        model.addAttribute("selectedCategory", category);

        // user 1 for now until login/registry exists
        int userId = 1;
        model.addAttribute("wishlistItemIds", wishlistService.getWishlistItemIds(userId));

        return "items/list";
    }

    @GetMapping("/{id}")
    public String itemDetails(@PathVariable int id, Model model) {
        model.addAttribute("item", itemService.getItemDetails(id));

        int userId = 1;
        // Now the details pge will know whether the item is already in the wishlist:
        model.addAttribute("wishlistItemIds", wishlistService.getWishlistItemIds(userId));

        WishlistEntry entry = wishlistService.getEntryForUser(userId, id);
        model.addAttribute("entryNote", entry != null ? entry.getNote() : "");

        return "items/details";
    }

    // TEMP sanity-check
    @GetMapping("/json")
    @ResponseBody
    public Item itemDetailsJson() {
        return itemService.getItemDetails(1);
    }
}
