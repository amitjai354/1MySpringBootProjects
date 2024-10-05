insert INTO category (Category_name) VALUES ('Fashion');
insert into Category ("CATEGORY_NAME")  values ('Electronics');
--"CATEGORY_Name" gives error column not found, but only "CATEGORY_NAME" works fine so do not use double quote
INSERT INTO CATEGORY (Category_name) values ('Books');
INSERT INTO CATEGORY (Category_name) values ('Groceries');
INSERT INTO CATEGORY (Category_name) values ('Medicines');

insert into USER (username, password) values ('jack', 'pass_word');
insert into USER (USERNAME, "PASSWORD") values ('bob', 'pass_word');
insert into user (username, password) values ('apple', 'pass_word');
insert into user (username, password) values ('glaxo', 'pass_word');

insert into Cart (total_amount, User_User_Id) values (20, 1);
insert into Cart (total_amount, User_User_Id) values (0, 2);

insert into User_Role (User_id, Roles) values (1, 'CONSUMER');
insert into User_Role (user_id, Roles) values (2, 'CONSUMER');
insert into User_Role (user_id, Roles) values (3, 'SELLER');
insert into User_Role (user_id, Roles) values (4, 'SELLER');

insert into Product(price, product_name, category_id, seller_id) values (29190, 'Apple ipad 10.2 8th Gen WiFi iOS Tablet',2,3);
insert into Product(price, product_name, category_id, seller_id) values (10, 'Crocin pain relief tablet',5,4);

insert into Cart_Product (cart_id, product_id, quantity) values (1, 2, 2)


