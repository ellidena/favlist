package com.favlist.controller;

import com.favlist.model.Item;
import com.favlist.model.WishlistEntry;
import com.favlist.service.ItemService;
import com.favlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Set;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @MockitoBean
    private WishlistService wishlistService;

    @Test
    void listItems_returnsOkAndView() throws Exception {
        /*
        The endpoint loads, the correct view is returns, the model contains the expected attributes
         */
        when(itemService.getItems(null)).thenReturn(List.of());
        when(itemService.getAllCategories()).thenReturn(List.of());
        when(wishlistService.getWishlistItemIds(1)).thenReturn(Set.of());

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items/list"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("wishlistItemIds"))
                .andExpect(model().attribute("selectedCategory", nullValue()));
    }

    @Test
    void listItems_showsItemNames() throws Exception {
        Item i1 = new Item();
        i1.setItemId(1);
        i1.setName("Lamp");

        Item i2 = new Item();
        i2.setItemId(2);
        i2.setName("Keyboard");

        when(itemService.getItems(null)).thenReturn(List.of(i1, i2));
        when(itemService.getAllCategories()).thenReturn(List.of());
        when(wishlistService.getWishlistItemIds(1)).thenReturn(Set.of());

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items/list"))
                .andExpect(content().string(containsString("Lamp")))
                .andExpect(content().string(containsString("Keyboard")));
    }

    @Test
    void listItems_showsQuickAddWhenNotInWishlist() throws Exception {
        Item item = new Item();
        item.setItemId(5);
        item.setName("Lamp");

        when(itemService.getAllItems()).thenReturn(List.of(item));
        when(itemService.getAllCategories()).thenReturn(List.of());
        when(wishlistService.getWishlistItemIds(1)).thenReturn(Set.of()); // empty wishlist

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("action=\"/wishlist/add\"")))
                .andExpect(content().string(containsString("name=\"itemId\" value=\"5\"")))
                .andExpect(content().string(not(containsString("action=\"/wishlist/remove\""))));
    }

    @Test
    void listItems_showsRemoveButtonWhenInWishlist() throws Exception {
        Item item = new Item();
        item.setItemId(5);
        item.setName("Lamp");

        when(itemService.getAllItems()).thenReturn(List.of(item));
        when(itemService.getAllCategories()).thenReturn(List.of());
        when(wishlistService.getWishlistItemIds(1)).thenReturn(Set.of(5));

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("action=\"/wishlist/remove\"")))
                .andExpect(content().string(containsString("name=\"itemId\" value=\"5\"")))
                .andExpect(content().string(not(containsString("action=\"/wishlist/add\""))));
    }

    @Test
    void itemDetails_showsAddFormWhenNotInWishlist() throws Exception {
        Item item = new Item();
        item.setItemId(5);
        item.setName("Lamp");
        item.setDescription("Bright");

        when(itemService.getItemDetails(5)).thenReturn(item);
        when(wishlistService.getWishlistItemIds(1)).thenReturn(Set.of());
        when(wishlistService.getEntryForUser(1, 5)).thenReturn(null);

        mockMvc.perform(get("/items/5"))
                .andExpect(status().isOk())
                .andExpect(view().name("items/details"))
                .andExpect(model().attribute("entryNote", ""))
                .andExpect(content().string(containsString("action=\"/wishlist/add\"")));

    }

    @Test
    void itemDetails_showsEditFormWhenInWishlist() throws Exception {
        Item item = new Item();
        item.setItemId(5);
        item.setName("Lamp");
        item.setDescription("Bright");

        WishlistEntry entry = new WishlistEntry();
        entry.setItemId(5);
        entry.setNote("Old note");

        when(itemService.getItemDetails(5)).thenReturn(item);
        when(wishlistService.getWishlistItemIds(1)).thenReturn(Set.of(5));
        when(wishlistService.getEntryForUser(1, 5)).thenReturn(entry);

        mockMvc.perform(get("/items/5"))
                .andExpect(status().isOk())
                .andExpect(view().name("items/details"))
                .andExpect(model().attribute("entryNote", "Old note"))

                // Edit form is present
                .andExpect(content().string(containsString("action=\"/wishlist/update-note\"")))

                // Redirect hidden field is correct
                .andExpect(content().string(containsString("name=\"redirect\"")))
                .andExpect(content().string(containsString("value=\"/items/5\"")))

                // Note field is pre-filled
                .andExpect(content().string(containsString("name=\"note\"")))
                .andExpect(content().string(containsString("value=\"Old note\"")))

                // Add form should NOT appear
                .andExpect(content().string(not(containsString("action=\"/wishlist/add\""))));
    }

    @Test
    void itemDetails_showsRemoveButtonWhenInWishlist() throws Exception {
        // Arrange
        Item item = new Item();
        item.setItemId(5);
        item.setName("Lamp");
        item.setDescription("Bright");

        WishlistEntry entry = new WishlistEntry();
        entry.setItemId(5);
        entry.setNote("Old note");

        when(itemService.getItemDetails(5)).thenReturn(item);
        when(wishlistService.getWishlistItemIds(1)).thenReturn(Set.of(5));
        when(wishlistService.getEntryForUser(1, 5)).thenReturn(entry);

        // Act + Assert
        mockMvc.perform(get("/items/5"))
                .andExpect(status().isOk())
                .andExpect(view().name("items/details"))

                // Remove form is present
                .andExpect(content().string(containsString("action=\"/wishlist/remove\"")))

                // Correct redirect hidden field
                .andExpect(content().string(containsString("name=\"redirect\"")))
                .andExpect(content().string(containsString("value=\"/items\"")))

                // Correct itemId hidden field
                .andExpect(content().string(containsString("name=\"itemId\"")))
                .andExpect(content().string(containsString("value=\"5\"")))

                // Add form should NOT appear
                .andExpect(content().string(not(containsString("action=\"/wishlist/add\""))));
    }

    @Test
    void listItems_showsCorrectItemLink() throws Exception {
        Item item = new Item();
        item.setItemId(5);
        item.setName("Lamp");

        when(itemService.getAllItems()).thenReturn(List.of(item));
        when(itemService.getAllCategories()).thenReturn(List.of());
        when(wishlistService.getWishlistItemIds(1)).thenReturn(Set.of());

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items/list"))

                // The link to the item details page should be correct
                .andExpect(content().string(containsString("<a href=\"/items/5\">Lamp</a>")));
    }

    @Test
    void listItems_showsEmptyStateWhenNoItems() throws Exception {
        when(itemService.getAllItems()).thenReturn(List.of());
        when(itemService.getAllCategories()).thenReturn(List.of());
        when(wishlistService.getWishlistItemIds(1)).thenReturn(Set.of());

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items/list"))

                // No item links
                .andExpect(content().string(not(containsString("<a href=\"/items/"))))

                // No Add or Remove forms
                .andExpect(content().string(not(containsString("<form action=\"/wishlist/add\""))))
                .andExpect(content().string(not(containsString("<form action=\"/wishlist/remove\""))));
    }

}
