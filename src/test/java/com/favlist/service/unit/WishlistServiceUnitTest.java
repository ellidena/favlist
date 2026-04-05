package com.favlist.service.unit;

import com.favlist.model.WishlistEntry;
import com.favlist.repository.WishlistEntryRepository;
import com.favlist.repository.WishlistRepository;
import com.favlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/*
No Spring context, no SQL
Tests service behavior, and that correct repository methods are called, independently of the database
 */
@ExtendWith(MockitoExtension.class)
public class WishlistServiceUnitTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private WishlistEntryRepository wishlistEntryRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @Test
    void addItem_callsInsertWhenNotDuplicate() {
        // Simulate: item not found → containsItem() returns false
        when(wishlistEntryRepository.findOne(1, 3))
                .thenThrow(new EmptyResultDataAccessException(1));

        wishlistService.addItem(1, 3, "note");

        verify(wishlistEntryRepository).insert(any());
    }

    @Test
    void addItem_doesNothingWhenDuplicate() {
        // Simulate: item exists → containsItem() returns true
        when(wishlistEntryRepository.findOne(1, 3))
                .thenReturn(new WishlistEntry());

        wishlistService.addItem(1, 3, "note");

        verify(wishlistEntryRepository, never()).insert(any());
    }

}
