package com.favlist.controller;

import com.favlist.model.Item;
import com.favlist.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String listItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("categories", itemService.getAllCategories());
        return "items/list";
    }

    // TEMP sanity-check
    @GetMapping("/json")
    @ResponseBody
    public List<Item> listItemsJson() {
        return itemService.getAllItems();
    }
}
