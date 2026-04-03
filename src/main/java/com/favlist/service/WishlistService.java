package com.favlist.service;

import com.favlist.model.Wishlist;
import com.favlist.model.WishlistEntry;
import com.favlist.repository.WishlistEntryRepository;
import com.favlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    /*
    getting a wishlist
    listing items in a wishlist
    adding an item to a wishlist
    removing an item from a wishlist
    adding/updating a note
    uses:
    WishlistRepo, WishlistEntryRepo

    Later validation:
    check if item already exists
    validate item exists
    validate wishlist exists
     */
    private final WishlistRepository wishlistRepository;
    private final WishlistEntryRepository wishlistEntryRepository;

    public WishlistService(
            WishlistRepository wishlistRepository,
            WishlistEntryRepository wishlistEntryRepository
    ) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistEntryRepository = wishlistEntryRepository;
    }

    public Wishlist getWishlistForUser(int userId) {
        return wishlistRepository.findByUserId(userId);
    }

    public List<WishlistEntry> getEntries(int wishlistId) {
        return wishlistEntryRepository.findByWishlistId(wishlistId);
    }

    public void addItem(int wishlistId, int itemId, String note) {
        WishlistEntry entry = new WishlistEntry();
        entry.setWishlistId(wishlistId);
        entry.setItemId(itemId);
        entry.setNote(note);

        wishlistEntryRepository.insert(entry);
    }
}
