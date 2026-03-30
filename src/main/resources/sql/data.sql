-- USERS
INSERT INTO user (name, username, password)
VALUES ('Daniella Norgren', 'dani','dani123');

-- WISHLIST
INSERT INTO wishlist (user_id)
VALUES (1);

-- CATEGORIES
INSERT INTO category (name)
VALUES ('Books','Electronics');

-- ITEMS
INSERT INTO item (name, description, category_id)
VALUES ('The Hobbit', 'A fantasy novel',1),
       ('Fancy Keyboard', 'Ergonomic computer keyboard', 2),
       ('Kobo Clara', 'Portable digital book reader', 2);

-- WISHLIST ENTRIES
INSERT INTO wishlist_entry (wishlist_id, item_id, note)
VALUES (1, 1, 'Should read this decade'),
       (1,2, 'I just want to type in bed without getting up lol')
