package com.favlist.service;

import com.favlist.model.Wishlist;
import com.favlist.repository.WishlistEntryRepository;
import com.favlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

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
}
