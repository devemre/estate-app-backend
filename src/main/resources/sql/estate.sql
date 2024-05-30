-- Create the estate_schema database
CREATE DATABASE estate_schema;

-- Use the estate_schema database
USE estate_schema;

-- Create users table
CREATE TABLE `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `mobile_number` VARCHAR(20) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  `create_dt` DATE DEFAULT NULL,
  `is_active` BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (`user_id`)
);

-- Insert sample users
INSERT INTO `users` (`name`, `email`, `mobile_number`, `password`, `create_dt`, `is_active`)
VALUES
  ('Emre', 'devemre@example.com', '+90 000 000 00 00', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', CURDATE(), TRUE),
  ('Alice', 'alice@example.com', '+90 111 111 11 11', '$2y$12$eK2Bjx8XvCJcD8/1RER4cOnWUi..//3JxOAIyTEe3/1xAdSu/yxb3', CURDATE(), TRUE),
  ('Bob', 'bob@example.com', '+90 222 222 22 22', '$2y$12$u2BaxK1YdTYDJhS/2ME3QOeAkD3..//4YzO3MNae4/2vBdQv/zxc4', CURDATE(), TRUE),
  ('Carol', 'carol@example.com', '+90 333 333 33 33', '$2y$12$g1Hbn1XtQCYD5hI/3ME3HOeTkT4..//5OzO5MOce5/3yCfWx/xzd5', CURDATE(), TRUE),
  ('Dave', 'dave@example.com', '+90 444 444 44 44', '$2y$12$h2Ico2YuYDYZ6iJ/4MF4POeUlU5..//6PzO6NPdf6/4zDgXy/yze6', CURDATE(), TRUE);

-- Create authorities table
CREATE TABLE `authorities` (
  `authority_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`authority_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

-- Insert roles for the users
INSERT INTO `authorities` (`user_id`, `name`)
VALUES
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_USER'),
  (3, 'ROLE_USER'),
  (4, 'ROLE_USER'),
  (5, 'ROLE_USER');

-- Create estates table
CREATE TABLE `estates` (
  `estate_id` INT AUTO_INCREMENT PRIMARY KEY,
  `address` VARCHAR(255) NOT NULL,
  `price` DECIMAL(19, 2) NOT NULL,
  `user_id` INT NOT NULL,
  `create_dt` DATE DEFAULT NULL,
  `is_active` BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

-- Insert sample estates
INSERT INTO `estates` (`address`, `price`, `user_id`, `create_dt`, `is_active`)
VALUES
  ('123 Main St, Istanbul', 250000.00, 1, CURDATE(), TRUE),
  ('456 Side St, Ankara', 300000.00, 2, CURDATE(), TRUE),
  ('789 Broad St, Izmir', 150000.00, 3, CURDATE(), TRUE),
  ('101 Center St, Bursa', 200000.00, 4, CURDATE(), TRUE),
  ('202 Square St, Antalya', 350000.00, 5, CURDATE(), TRUE),
  ('303 Triangle St, Adana', 275000.00, 1, CURDATE(), TRUE),
  ('404 Circle St, Gaziantep', 325000.00, 2, CURDATE(), TRUE),
  ('505 Pentagon St, Konya', 400000.00, 3, CURDATE(), TRUE),
  ('606 Hexagon St, Mersin', 450000.00, 4, CURDATE(), TRUE),
  ('707 Octagon St, Kayseri', 500000.00, 5, CURDATE(), TRUE);
