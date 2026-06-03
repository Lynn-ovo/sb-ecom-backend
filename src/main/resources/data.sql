-- 分类
INSERT INTO categories (category_id, category_name) VALUES (1, 'Electronics');
INSERT INTO categories (category_id, category_name) VALUES (2, 'Clothing');
INSERT INTO categories (category_id, category_name) VALUES (3, 'Books');

-- 电子产品
INSERT INTO products (product_id, product_name, description, price, discount, special_price, quantity, image, category_id)
VALUES (1, 'iPhone 15 Pro', 'Apple iPhone 15 Pro 256GB, Titanium finish with A17 Pro chip.', 1199.00, 5.0, 1139.05, 30, 'default.jpg', 1);

INSERT INTO products (product_id, product_name, description, price, discount, special_price, quantity, image, category_id)
VALUES (2, 'Samsung Galaxy S24', 'Samsung Galaxy S24 128GB, AI-powered Android smartphone.', 899.00, 10.0, 809.10, 45, 'default.jpg', 1);

INSERT INTO products (product_id, product_name, description, price, discount, special_price, quantity, image, category_id)
VALUES (3, 'Sony WH-1000XM5', 'Industry-leading noise cancelling wireless headphones.', 349.00, 15.0, 296.65, 60, 'default.jpg', 1);

INSERT INTO products (product_id, product_name, description, price, discount, special_price, quantity, image, category_id)
VALUES (4, 'MacBook Air M3', 'Apple MacBook Air 13-inch with M3 chip, 8GB RAM, 256GB SSD.', 1299.00, 0.0, 1299.00, 20, 'default.jpg', 1);

-- 服装
INSERT INTO products (product_id, product_name, description, price, discount, special_price, quantity, image, category_id)
VALUES (5, 'Classic White T-Shirt', '100% organic cotton unisex t-shirt, available in all sizes.', 29.99, 0.0, 29.99, 200, 'default.jpg', 2);

INSERT INTO products (product_id, product_name, description, price, discount, special_price, quantity, image, category_id)
VALUES (6, 'Slim Fit Jeans', 'Premium denim slim fit jeans in dark blue wash.', 79.99, 20.0, 63.99, 150, 'default.jpg', 2);

INSERT INTO products (product_id, product_name, description, price, discount, special_price, quantity, image, category_id)
VALUES (7, 'Wool Winter Coat', 'Warm wool blend coat, perfect for cold weather.', 189.99, 10.0, 170.99, 80, 'default.jpg', 2);

-- 书籍
INSERT INTO products (product_id, product_name, description, price, discount, special_price, quantity, image, category_id)
VALUES (8, 'Clean Code', 'A Handbook of Agile Software Craftsmanship by Robert C. Martin.', 39.99, 0.0, 39.99, 100, 'default.jpg', 3);

INSERT INTO products (product_id, product_name, description, price, discount, special_price, quantity, image, category_id)
VALUES (9, 'Spring Boot in Action', 'Practical guide to building Spring Boot applications.', 49.99, 5.0, 47.49, 75, 'default.jpg', 3);

INSERT INTO products (product_id, product_name, description, price, discount, special_price, quantity, image, category_id)
VALUES (10, 'The Pragmatic Programmer', '20th Anniversary Edition — your journey to mastery.', 44.99, 0.0, 44.99, 90, 'default.jpg', 3);