package com.favlist.service;

import com.favlist.model.Item;
import com.favlist.repository.CategoryRepository;
import com.favlist.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItemsByCategory(int categoryId) {
        return itemRepository.findByCategory(categoryId);
    }

    public Item getItemDetails(int itemId) {
        return itemRepository.findById(itemId);
    }
}
