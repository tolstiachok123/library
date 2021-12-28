DELETE
FROM `order`;
ALTER TABLE `order`
    ALTER COLUMN id RESTART WITH 1;

DELETE
FROM book_tags;

DELETE
FROM `tag`;
ALTER TABLE `tag`
    ALTER COLUMN id RESTART WITH 1;

DELETE
FROM book;
ALTER TABLE book
    ALTER COLUMN id RESTART WITH 1;

DELETE
FROM author;
ALTER TABLE author
    ALTER COLUMN id RESTART WITH 1;

DELETE
FROM user_role;

DELETE
FROM role_authority;

DELETE
FROM user;
ALTER TABLE user
    ALTER COLUMN id RESTART WITH 1;

DELETE
FROM role;
ALTER TABLE role
    ALTER COLUMN id RESTART WITH 1;

DELETE
FROM authority;
ALTER TABLE authority
    ALTER COLUMN id RESTART WITH 1;
