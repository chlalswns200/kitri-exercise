INSERT INTO category(id, name)
VALUES (1, 'BOOK'),
       (2, 'ELECTRONIC');

INSERT INTO product(id, name, price, stock, category_id)
VALUES (1, 'Spring Guide', 25000, 100, 1),
       (2, 'AI Speaker', 70000, 50, 2);

INSERT INTO "user"(id, email, password, name)
VALUES (1, 'alice@example.com', '1q2w3e4r', 'Alice');

INSERT INTO orders(id, user_id, total_price, ordered_at)
VALUES (1, 1, 25000, now());

INSERT INTO order_item(id, order_id, product_id, quantity, item_price)
VALUES (1, 1, 1, 1, 25000);


