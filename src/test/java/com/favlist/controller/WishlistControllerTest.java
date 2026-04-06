package com.favlist.controller;

import com.favlist.model.Wishlist;
import com.favlist.service.UserService;
import com.favlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(WishlistController.class)
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishlistService wishlistService;

    @MockitoBean
    private UserService userService;

    @Test
    void viewWishlist_ReturnsOkAndView() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(1);

        when(userService.getWishlistForUser(1)).thenReturn(wishlist);
        when(wishlistService.getEntries(1)).thenReturn(List.of());

        mockMvc.perform(get("/wishlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/view"))
                .andExpect(model().attributeExists("entries"));
    }

    @Test
    void updateNote_withoutRedirect_redirectsToWishlist() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(10);

        when(userService.getWishlistForUser(1)).thenReturn(wishlist);

        mockMvc.perform(post("/wishlist/update-note")
                        .param("itemId", "5")
                        .param("note", "hello"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        verify(wishlistService).updateNote(10, 5, "hello");

    }
}
