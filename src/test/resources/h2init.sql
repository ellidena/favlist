DROP TABLE IF EXISTS wishlist_entry;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS users;

/*
- Created first because the other tables depend on it.
- Stores login + profile information.
- username is UNIQUE to two users cannot share the same login.
*/
CREATE TABLE users (
                      user_id      INT AUTO_INCREMENT PRIMARY KEY,
                      name     VARCHAR(100) NOT NULL,
                      username  VARCHAR(100) NOT NULL UNIQUE,
                      password  VARCHAR(100) NOT NULL
);

/*
- Each user has exactly one wishlist (1-to-1 relationship)
- Must be created AFTER user because it references user_id
- ON DELETE CASCADE:
    If a user is deleted, their wishlist is automatically deleted.
    This prevents orphaned wishlists.
*/
CREATE TABLE wishlist (
                          wishlist_id      INT AUTO_INCREMENT PRIMARY KEY,
                          user_id      INT NOT NULL,

                          FOREIGN KEY (user_id)
                              REFERENCES users(user_id)
                              ON DELETE CASCADE
);

/*
- Independent lookup table.
- Created before item because item references category_id.
- name is UNIQUE so categories cannot be duplicated.
*/
CREATE TABLE category (
                          category_id   INT AUTO_INCREMENT PRIMARY KEY,
                          name        VARCHAR(100) NOT NULL UNIQUE
);

/*
- Represents items available to add to a wishlist.
- Depends on category, so created AFTER category.
- ON DELETE RESTRICT:
    Prevents deleting a category if items still use it.
    This protects data integrity.
*/
CREATE TABLE item (
                      item_id   INT AUTO_INCREMENT PRIMARY KEY,
                      name     VARCHAR(150) NOT NULL,
                      description   VARCHAR(500),
                      category_id   INT NOT NULL,

                      FOREIGN KEY (category_id)
                          REFERENCES category(category_id)
                          ON DELETE RESTRICT
);

/*
JOIN TABLE
- Represents items added to a wishlist.
- Composite primary key (wishlist_id + item_id):
    Ensures the same item cannot be added twice to the same wishlist.
    Saves space by avoiding an extra surrogate key.
- Must be created AFTER wishlist and item.
- ON DELETE CASCADE:
    If a wishlist is deleted -> all its entries are deleted.
    If an item is deleted -> it is removed from all wishlists.
*/
CREATE TABLE wishlist_entry (
                                wishlist_id INT NOT NULL,
                                item_id     INT NOT NULL,
                                note       VARCHAR(500),

                                PRIMARY KEY (wishlist_id, item_id),

                                FOREIGN KEY (wishlist_id)
                                    REFERENCES wishlist(wishlist_id)
                                    ON DELETE CASCADE,

                                FOREIGN KEY (item_id)
                                    REFERENCES item(item_id)
                                    ON DELETE CASCADE
);

-- USERS
INSERT INTO users (name, username, password)
VALUES ('Daniella Norgren', 'dani','dani123');

-- WISHLIST
INSERT INTO wishlist (user_id)
VALUES (1);

-- CATEGORIES
INSERT INTO category (name)
VALUES ('Books'),('Electronics');

-- ITEMS
INSERT INTO item (name, description, category_id)
VALUES ('The Hobbit', 'A fantasy novel',1),
       ('Fancy Keyboard', 'Ergonomic computer keyboard', 2),
       ('Kobo Clara', 'Portable digital book reader', 2);

-- WISHLIST ENTRIES
INSERT INTO wishlist_entry (wishlist_id, item_id, note)
VALUES (1, 1, 'Should read this decade'),
       (1,2, 'I just want to type in bed without getting up lol')
