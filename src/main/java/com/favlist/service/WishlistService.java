package com.favlist.service;

import com.favlist.model.Wishlist;
import com.favlist.model.WishlistEntry;
import com.favlist.repository.WishlistEntryRepository;
import com.favlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        // if already in the wishlist, do nothing
        if (containsItem(wishlistId, itemId)) {
            return; // alternatively, throw exception, or log exception
        }

        WishlistEntry entry = new WishlistEntry();
        entry.setWishlistId(wishlistId);
        entry.setItemId(itemId);
        entry.setNote(note);

        wishlistEntryRepository.insert(entry);
    }

    public void removeItem(int wishlistId, int itemId){
        wishlistEntryRepository.delete(wishlistId, itemId);
    }

    public void updateNote(int wishlistId, int itemId, String newNote) {
        WishlistEntry entry = new WishlistEntry();
        entry.setWishlistId(wishlistId);
        entry.setItemId(itemId);
        entry.setNote(newNote);

        wishlistEntryRepository.updateNote(entry);
    }

    // check if an item is already in the wishlist
    public boolean containsItem(int wishlistId, int itemId) {
        try {
            wishlistEntryRepository.findOne(wishlistId, itemId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int countItems(int wishlistId) {
        return wishlistEntryRepository.findByWishlistId(wishlistId).size();
    }

    public Set<Integer> getWishlistItemIds(int userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId);
        List<WishlistEntry> entries = wishlistEntryRepository.findByWishlistId(wishlist.getWishlistId());

        Set<Integer> ids = new HashSet<>();
        for (WishlistEntry entry : entries) {
            ids.add(entry.getItemId());
        }
        return ids;
    }

    public WishlistEntry getEntryForUser(int userId, int itemId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId);

        try {
            return wishlistEntryRepository.findOne(wishlist.getWishlistId(), itemId);
        } catch (Exception e) {
            return null;
        }
    }
}
