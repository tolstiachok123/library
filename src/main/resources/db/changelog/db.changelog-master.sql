--liquibase formatted sql

--changeset tolstiachok123:1
CREATE TABLE `author` (
  `id` int NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

--changeset tolstiachok123:2
CREATE TABLE `tag` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

--changeset tolstiachok123:3
CREATE TABLE `book` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `author_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKklnrv3weler2ftkweewlky958` (`author_id`),
  CONSTRAINT `FKklnrv3weler2ftkweewlky958` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
);

--changeset tolstiachok123:4
CREATE TABLE `author_books` (
  `author_id` int NOT NULL,
  `books_id` int NOT NULL,
  UNIQUE KEY `UK_fxksjqa1a5dnqf0egcdxlrcna` (`books_id`),
  KEY `FKfvabqdr9njwv4khjqkf1pbmma` (`author_id`),
  CONSTRAINT `FKfvabqdr9njwv4khjqkf1pbmma` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
  CONSTRAINT `FKr514ej8rhei197wx3nrvp0qie` FOREIGN KEY (`books_id`) REFERENCES `book` (`id`)
);

-- --changeset tolstiachok123:5
-- CREATE TABLE `authority` (
--   `id` int NOT NULL,
--   `name` varchar(255) DEFAULT NULL,
--   PRIMARY KEY (`id`)
-- );

-- --changeset tolstiachok123:6
-- CREATE TABLE `orders` (
--   `id` int NOT NULL,
--   `test` int DEFAULT NULL,
--   PRIMARY KEY (`id`)
-- );

-- --changeset tolstiachok123:7
-- CREATE TABLE `role` (
--   `id` int NOT NULL,
--   `name` varchar(255) DEFAULT NULL,
--   PRIMARY KEY (`id`)
-- );

--changeset tolstiachok123:8
CREATE TABLE `tag_books` (
  `tag_id` int NOT NULL,
  `books_id` int NOT NULL,
  KEY `FK_books_id` (`books_id`),
  KEY `FK_tag_id` (`tag_id`),
  CONSTRAINT `FK_books_id` FOREIGN KEY (`books_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FK_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
);

-- --changeset tolstiachok123:9
-- CREATE TABLE `user` (
--   `id` int NOT NULL,
--   `created_at` datetime(6) DEFAULT NULL,
--   `email` varchar(255) DEFAULT NULL,
--   `first_name` varchar(255) DEFAULT NULL,
--   `last_name` varchar(255) DEFAULT NULL,
--   `password` varchar(255) DEFAULT NULL,
--   `updated_at` datetime(6) DEFAULT NULL,
--   PRIMARY KEY (`id`)
-- );

INSERT INTO author (id, first_name, last_name) VALUES (1, 'Howard Phillips', 'Lovecraft');

INSERT INTO book (id, price, image_url, title, author_id) VALUES (1, 45, 'testUrl', 'The Call of Cthulhu', 1);

INSERT INTO tag (id, name) VALUES (1, 'Horror');

INSERT INTO author_books (author_id, books_id) VALUES (1, 1);

INSERT INTO tag_books (tag_id, books_id) VALUES (1, 1);