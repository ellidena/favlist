package com.favlist.controller;

import com.favlist.model.Item;
import com.favlist.service.ItemService;
import com.favlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.hamcrest.Matchers.containsString;
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
        when(itemService.getAllItems()).thenReturn(List.of());
        when(itemService.getAllCategories()).thenReturn(List.of());
        when(wishlistService.getWishlistItemIds(1)).thenReturn(Set.of());

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items/list"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("wishlistItemIds"));
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

}
