package com.favlist.controller;


import com.favlist.model.Item;
import com.favlist.service.ItemService;
import com.favlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
    public String listItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("categories", itemService.getAllCategories());

        // user 1 for now until login/registry exists
        int userId = 1;
        model.addAttribute("wishlistItemIds", wishlistService.getWishlistItemIds(userId));

        return "items/list";
    }

    @GetMapping("/{id}")
    public String itemDetails(@PathVariable int id, Model model) {
        model.addAttribute("item", itemService.getItemDetails(id));
        return "items/details";
    }

    // TEMP sanity-check
    @GetMapping("/json")
    @ResponseBody
    public Item itemDetailsJson() {
        return itemService.getItemDetails(1);
    }
}
