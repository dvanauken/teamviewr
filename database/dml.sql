-- Inserting data into the Products table
INSERT INTO Products (name, description) VALUES ('Product A', 'Description for Product A');
INSERT INTO Products (name, description) VALUES ('Product B', 'Description for Product B');
INSERT INTO Products (name, description) VALUES ('Product C', 'Description for Product C');

-- Inserting data into the Orders table
INSERT INTO Orders (name, description) VALUES ('Order 1', 'Description for Order 1');
INSERT INTO Orders (name, description) VALUES ('Order 2', 'Description for Order 2');


INSERT INTO OrderItems (orderId, productId, quantity) VALUES (1, 1, 2);
INSERT INTO OrderItems (orderId, productId, quantity) VALUES (1, 2, 1);
INSERT INTO OrderItems (orderId, productId, quantity) VALUES (2, 3, 1);