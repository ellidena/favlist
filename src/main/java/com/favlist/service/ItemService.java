package com.favlist.service;

import com.favlist.repository.CategoryRepository;
import com.favlist.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    /*
    listing items
    filtering by category
    showing item details
    uses:
    ItemRepo, CategoryRepo
     */
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public ItemService(
            ItemRepository itemRepository,
            CategoryRepository categoryRepository
    ) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }
}
