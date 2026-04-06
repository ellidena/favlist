package com.favlist.controller;

import com.favlist.model.Wishlist;
import com.favlist.model.WishlistEntry;
import com.favlist.service.UserService;
import com.favlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
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
    void viewWishlist_loadsEntriesIntoModel() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(1);

        WishlistEntry entry = new WishlistEntry();
        entry.setItemId(5);
        entry.setItemName("Lamp");

        when(userService.getWishlistForUser(1)).thenReturn(wishlist);
        when(wishlistService.getEntries(1)).thenReturn(List.of(entry));

        mockMvc.perform(get("/wishlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/view"))
                .andExpect(model().attributeExists("entries"))
                .andExpect(content().string(containsString("Lamp")));
    }

    @Test
    void addItem_addsItemAdRedirectsToItems() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(10);

        when(userService.getWishlistForUser(1)).thenReturn(wishlist);

        mockMvc.perform(post("/wishlist/add")
                        .param("itemId", "5")
                        .param("note", "hello"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items"));

        verify(wishlistService).addItem(10, 5, "hello");
    }

    @Test
    void removeItem_withoutRedirect_redirectsToWishlistView() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(10);

        when(userService.getWishlistForUser(1)).thenReturn(wishlist);

        mockMvc.perform(post("/wishlist/remove")
                        .param("itemId", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/view"));

        verify(wishlistService).removeItem(10, 5);
    }

    @Test
    void removeItem_withRedirect_redirectsToProvidedUrl() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(10);

        when(userService.getWishlistForUser(1)).thenReturn(wishlist);

        mockMvc.perform(post("/wishlist/remove")
                        .param("itemId", "5")
                        .param("redirect", "/items"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items"));

        verify(wishlistService).removeItem(10, 5);
    }

    @Test
    void updateNote_callsServiceWithCorrectArguments() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(10);

        when(userService.getWishlistForUser(1)).thenReturn(wishlist);

        mockMvc.perform(post("/wishlist/update-note")
                        .param("itemId", "7")
                        .param("note", "Updated note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        verify(wishlistService).updateNote(10, 7, "Updated note");
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

    @Test
    void updateNote_withRedirect_redirectsToProvidedUrl() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(10);

        when(userService.getWishlistForUser(1)).thenReturn(wishlist);

        mockMvc.perform(post("/wishlist/update-note")
                        .param("itemId", "5")
                        .param("note", "hello")
                        .param("redirect", "/items/5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items/5"));

        verify(wishlistService).updateNote(10, 5, "hello");
    }

    @Test
    void wishlist_editMode_showsEditFormForCorrectItem() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(1);

        WishlistEntry e1 = new WishlistEntry();
        e1.setItemId(5);
        e1.setItemName("Lamp");
        e1.setNote("Old note");

        WishlistEntry e2 = new WishlistEntry();
        e2.setItemId(7);
        e2.setItemName("Keyboard");
        e2.setNote("Clicky");

        when(userService.getWishlistForUser(1)).thenReturn(wishlist);
        when(wishlistService.getEntries(1)).thenReturn(List.of(e1, e2));

        mockMvc.perform(get("/wishlist").param("edit", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/view"))

                // Edit form appears for item 5
                .andExpect(content().string(containsString("action=\"/wishlist/update-note\"")))
                .andExpect(content().string(containsString("value=\"5\"")))
                .andExpect(content().string(containsString("Old note")))

                // Edit link for item 5 is hidden
                .andExpect(content().string(not(containsString("/wishlist?edit=5"))))

                // Item 7 remains in view mode
                .andExpect(content().string(containsString("Keyboard")))
                .andExpect(content().string(containsString("Clicky")))
                .andExpect(content().string(containsString("/wishlist?edit=7")));
    }

}
