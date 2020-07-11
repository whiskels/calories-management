DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, description, calories, date_time) VALUES
(100000, 'Pastrami sandwich', 500, '2020-07-11 10:00:00'),
(100000, 'Orange juice', 100, '2020-07-11 12:00:00'),
(100000, 'Vegetable salad', 500, '2020-07-11 14:00:00'),
(100000, 'Ice cream', 500, '2020-07-11 20:00:00'),
(100001, 'Admin super meal', 9999, current_timestamp);
