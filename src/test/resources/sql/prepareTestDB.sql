INSERT INTO `tag` (id, name)
VALUES (1, 'Horror');

INSERT INTO author (id, first_name, last_name)
VALUES (NULL, 'Jules', 'Verne'),
       (NULL, 'Howard Philips', 'Lovecraft');

INSERT INTO book (id, title, price, image_url, created_at, updated_at, author_id)
VALUES (NULL, 'Dagon', 45.00, 'URL', '2021-11-08T02:34:47+03:00[Europe/Minsk]',
        '2021-11-08T02:34:47+03:00[Europe/Minsk]', 1);

INSERT INTO book_tags (tags_id, books_id)
VALUES (1, 1);

INSERT INTO authority (id, name)
VALUES (NULL, 'read'),
       (NULL, 'write'),
       (NULL, 'delete'),
       (NULL, 'edit');

INSERT INTO role (id, name)
VALUES (1, 'USER');

INSERT INTO role_authority (role_id, authority_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4);

INSERT INTO user (id, email, password)
values (1, 'admin_mock', '$2y$12$Ol2wWiJjCsY.u3C7H8u3I.xB9FWRZdRF/qppePPnT75rWKQGSIRbq');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1);

INSERT INTO `order` (id, status, history, user_id)
VALUES (NULL, 'DRAFT',
        '[{"book": {"id": 1, "tags": [{"id": 1, "name": "Horror"}], "price": 45.00, "title": "Dagon", "author": {"id": 1, "lastName": "Jules", "firstName": "Verne"}, "imageUrl": "url", "createdAt": "2021-11-08T02:34:47+03:00[Europe/Minsk]", "updatedAt": "2021-11-08T02:34:47+03:00[Europe/Minsk]"}, "quantity": 1}]',
        1);