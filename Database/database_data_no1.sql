USE [GroceryStore]
GO

-- 10 Categories
INSERT INTO Categories (CategoryName)
VALUES 
(N'Beverages'), (N'Snacks'), (N'Fruits'), (N'Vegetables'), (N'Dairy'),
(N'Bakery'), (N'Meats'), (N'Seafood'), (N'Canned Goods'), (N'Frozen Foods');

-- Beverages (CategoryID = 1)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID)
VALUES 
('BEV001', 'Coca-Cola', 100, 10000, 1),
('BEV002', 'Pepsi', 120, 9500, 1),
('BEV003', 'Sprite', 90, 10000, 1),
('BEV004', 'Fanta', 85, 9750, 1),
('BEV005', '7Up', 80, 10200, 1),
('BEV006', 'Red Bull', 70, 15000, 1),
('BEV007', 'Monster', 60, 16000, 1),
('BEV008', 'Iced Tea', 100, 8500, 1),
('BEV009', 'Lemonade', 95, 7000, 1),
('BEV010', 'Water Bottle', 200, 5000, 1);

-- Snacks (CategoryID = 2)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID)
VALUES 
('SNK001', 'Lays Chips', 150, 12000, 2),
('SNK002', 'Doritos', 140, 13000, 2),
('SNK003', 'Pringles', 130, 14000, 2),
('SNK004', 'KitKat', 110, 6000, 2),
('SNK005', 'Snickers', 100, 6500, 2),
('SNK006', 'M&Ms', 120, 7000, 2),
('SNK007', 'Twix', 90, 6000, 2),
('SNK008', 'Oreo', 80, 8000, 2),
('SNK009', 'Cheez-It', 70, 9000, 2),
('SNK010', 'Trail Mix', 60, 10000, 2);

-- Fruits (CategoryID = 3)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID)
VALUES 
('FRT001', 'Apple', 100, 5000, 3),
('FRT002', 'Banana', 120, 4000, 3),
('FRT003', 'Orange', 110, 4500, 3),
('FRT004', 'Mango', 90, 6000, 3),
('FRT005', 'Pineapple', 80, 7000, 3),
('FRT006', 'Grapes', 95, 5500, 3),
('FRT007', 'Strawberry', 85, 8000, 3),
('FRT008', 'Blueberry', 70, 9000, 3),
('FRT009', 'Watermelon', 60, 10000, 3),
('FRT010', 'Papaya', 50, 6500, 3);

-- Vegetables (CategoryID = 4)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID)
VALUES 
('VEG001', 'Carrot', 100, 3000, 4),
('VEG002', 'Broccoli', 90, 4000, 4),
('VEG003', 'Spinach', 85, 3500, 4),
('VEG004', 'Cabbage', 80, 2500, 4),
('VEG005', 'Potato', 200, 2000, 4),
('VEG006', 'Tomato', 190, 2200, 4),
('VEG007', 'Onion', 180, 1800, 4),
('VEG008', 'Garlic', 150, 2500, 4),
('VEG009', 'Cucumber', 160, 2800, 4),
('VEG010', 'Lettuce', 170, 3200, 4);

-- Dairy (CategoryID = 5)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID)
VALUES 
('DRY001', 'Milk', 100, 6000, 5),
('DRY002', 'Yogurt', 90, 5500, 5),
('DRY003', 'Cheddar Cheese', 80, 7000, 5),
('DRY004', 'Butter', 70, 6500, 5),
('DRY005', 'Cream', 60, 5000, 5),
('DRY006', 'Cottage Cheese', 50, 6000, 5),
('DRY007', 'Sour Cream', 45, 4500, 5),
('DRY008', 'Ghee', 40, 8000, 5),
('DRY009', 'Milk Powder', 55, 9000, 5),
('DRY010', 'Condensed Milk', 60, 7500, 5);

-- Bakery (CategoryID = 6)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID)
VALUES 
('BKY001', 'White Bread', 100, 4000, 6),
('BKY002', 'Whole Grain Bread', 90, 4500, 6),
('BKY003', 'Croissant', 80, 5000, 6),
('BKY004', 'Baguette', 70, 4800, 6),
('BKY005', 'Donut', 60, 3500, 6),
('BKY006', 'Cake', 50, 8000, 6),
('BKY007', 'Muffin', 40, 3800, 6),
('BKY008', 'Bagel', 45, 4200, 6),
('BKY009', 'Biscuit', 55, 4000, 6),
('BKY010', 'Pastry', 60, 5500, 6);

-- Meats (CategoryID = 7)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID)
VALUES 
('MEAT001', 'Chicken Breast', 100, 10000, 7),
('MEAT002', 'Beef Steak', 90, 15000, 7),
('MEAT003', 'Pork Chop', 85, 12000, 7),
('MEAT004', 'Bacon', 80, 9000, 7),
('MEAT005', 'Sausage', 70, 8000, 7),
('MEAT006', 'Ground Beef', 60, 10500, 7),
('MEAT007', 'Turkey', 50, 13000, 7),
('MEAT008', 'Ham', 45, 11000, 7),
('MEAT009', 'Ribs', 55, 14000, 7),
('MEAT010', 'Lamb', 60, 16000, 7);

-- Seafood (CategoryID = 8)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID)
VALUES 
('SF001', 'Salmon', 100, 14000, 8),
('SF002', 'Shrimp', 90, 13000, 8),
('SF003', 'Tuna', 85, 12500, 8),
('SF004', 'Crab', 80, 15000, 8),
('SF005', 'Lobster', 70, 20000, 8),
('SF006', 'Squid', 60, 10000, 8),
('SF007', 'Clam', 50, 9000, 8),
('SF008', 'Oyster', 45, 11000, 8),
('SF009', 'Mackerel', 55, 8500, 8),
('SF010', 'Anchovy', 60, 7000, 8);

-- Canned Goods (CategoryID = 9)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID)
VALUES 
('CAN001', 'Canned Beans', 100, 4000, 9),
('CAN002', 'Canned Corn', 90, 3500, 9),
('CAN003', 'Canned Tuna', 85, 5000, 9),
('CAN004', 'Canned Tomatoes', 80, 3800, 9),
('CAN005', 'Canned Soup', 70, 4500, 9),
('CAN006', 'Canned Peas', 60, 3200, 9),
('CAN007', 'Canned Pineapple', 50, 4800, 9),
('CAN008', 'Canned Chicken', 45, 6000, 9),
('CAN009', 'Canned Fruit Cocktail', 55, 5500, 9),
('CAN010', 'Canned Mushroom', 60, 4200, 9);

-- Frozen Foods (CategoryID = 10)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID)
VALUES 
('FRZ001', 'Frozen Pizza', 100, 10000, 10),
('FRZ002', 'Frozen Vegetables', 90, 5000, 10),
('FRZ003', 'Frozen Fries', 85, 4000, 10),
('FRZ004', 'Frozen Chicken Nuggets', 80, 8000, 10),
('FRZ005', 'Frozen Fish Fillet', 70, 9000, 10),
('FRZ006', 'Frozen Burger Patty', 60, 7500, 10),
('FRZ007', 'Frozen Dumplings', 50, 6500, 10),
('FRZ008', 'Frozen Spring Rolls', 45, 6000, 10),
('FRZ009', 'Frozen Pasta', 55, 5500, 10),
('FRZ010', 'Frozen Fruit Mix', 60, 7000, 10);

-- Giả sử RoleID = 0 là Customer, 1 là Admin
-- Password của tất cả là Demo@123 (dùng md5 để mã hóa)
INSERT INTO Users (Username, Password, FullName, Email, RoleID)
VALUES 
('user1', 'e14c05f0dc27e6be1fc127abaf474a59', N'Nguyen Van A', 'user1@gmail.com', 0),
('user2', 'e14c05f0dc27e6be1fc127abaf474a59', N'Le Thi B', 'user2@example.com', 0),
('user3', 'e14c05f0dc27e6be1fc127abaf474a59', N'Tran Van C', 'user3@example.com', 0),
('user4', 'e14c05f0dc27e6be1fc127abaf474a59', N'Pham Thi D', 'user4@example.com', 0),
('user5', 'e14c05f0dc27e6be1fc127abaf474a59', N'Ngo Van E', 'user5@example.com', 0),
('user6', 'e14c05f0dc27e6be1fc127abaf474a59', N'Huynh Thi F', 'user6@example.com', 0),
('user7', 'e14c05f0dc27e6be1fc127abaf474a59', N'Dang Van G', 'user7@example.com', 0),
('admin1', 'e14c05f0dc27e6be1fc127abaf474a59', N'Quan Trị Vien A', 'admin1@gmail.com', 1),
('admin2', 'e14c05f0dc27e6be1fc127abaf474a59', N'Quan Tri Vien B', 'admin2@example.com', 1),
('admin3', 'e14c05f0dc27e6be1fc127abaf474a59', N'Quan Tri Vien C', 'admin2@example.com', 1);

-- Thêm sản phẩm vào giỏ hàng cho các User.
INSERT INTO Carts (UserID, ProductID, Quantity)
VALUES

-- User 1
(1, 1, 2),
(1, 15, 1),
(1, 20, 1),
(1, 22, 2),
(1, 80, 1),
(1, 83, 2),

-- User 2
(2, 35, 2),
(2, 40, 4),
(2, 55, 1),

-- User 3
(3, 10, 2),
(3, 12, 1),
(3, 15, 3),
(3, 90, 2),
(3, 85, 2),
(3, 90, 1),

-- User 4
(4, 22, 1),
(4, 25, 2),
(4, 29, 2),
(4, 30, 1),
(4, 92, 1),

-- User 5
(5, 35, 3),
(5, 38, 1),
(5, 40, 4),
(5, 95, 3),

-- User 6
(6, 45, 2),
(6, 48, 1),
(6, 50, 1),
(6, 97, 2),

-- User 7
(7, 55, 3),
(7, 58, 2),
(7, 60, 1),
(7, 99, 1),
(7, 99, 1),

-- Admin 1 cũng có cart để test (không cần thiết nhưng có thể dùng demo)
(8, 65, 2),
(8, 68, 1),
(8, 70, 3),
(8, 68, 1),
(8, 70, 3),

-- Admin 2 cũng có cart để test (không cần thiết nhưng có thể dùng demo)
(9, 73, 1),
(9, 75, 2),

-- Admin 3 cũng có cart để test (không cần thiết nhưng có thể dùng demo)
(10, 99, 3),
(10, 40, 2),
(10, 60, 2);

INSERT INTO DiscountTypes (DiscountTypeID, TypeName)
VALUES
(0, N'Phần trăm (%)'),
(1, N'Giảm cố định (VND)'),
(2, N'Miễn phí vận chuyển');

INSERT INTO DiscountCodes (Code, DiscountValue, DiscountTypeID, QuantityAvailable, ExpiryDate, MinOrderValue)
VALUES
-- Phần trăm
('SALE10', 10.0, 0, 100, '2025-12-31', 100000),
('SALE15', 15.0, 0, 80,  '2025-12-31', 150000),
('SALE20', 20.0, 0, 50,  '2025-12-31', 200000),
('BIGSALE25', 25.0, 0, 30, '2025-11-30', 300000),
('HOT30', 30.0, 0, 20, '2025-10-31', 400000),
('FLASH35', 35.0, 0, 10, '2025-09-30', 500000),
('VIP40', 40.0, 0, 5, '2025-08-31', 600000),
('MEGA45', 45.0, 0, 3, '2025-08-01', 700000),
('MAX50', 50.0, 0, 2, '2025-07-31', 800000),
('SUMMER5', 5.0, 0, 150, '2025-12-31', 50000),

-- Giảm tiền trực tiếp
('GIAM10000', 10000.0, 1, 100, '2025-12-31', 50000),
('GIAM20000', 20000.0, 1, 80, '2025-12-31', 100000),
('GIAM30000', 30000.0, 1, 70, '2025-12-31', 150000),
('GIAM40000', 40000.0, 1, 50, '2025-12-31', 200000),
('GIAM50000', 50000.0, 1, 40, '2025-12-31', 250000),
('GIAM60000', 60000.0, 1, 30, '2025-11-30', 300000),
('GIAM70000', 70000.0, 1, 20, '2025-11-30', 350000),
('GIAM80000', 80000.0, 1, 15, '2025-10-31', 400000),
('GIAM90000', 90000.0, 1, 10, '2025-09-30', 450000),
('GIAM100000', 100000.0, 1, 5, '2025-08-31', 500000),

-- Freeship
('FREESHIP1', 0.0, 2, 100, '2025-12-31', 0),
('FREESHIP2', 0.0, 2, 80, '2025-12-31', 100000),
('FREESHIP3', 0.0, 2, 50, '2025-12-31', 150000),
('FREESHIP4', 0.0, 2, 30, '2025-12-31', 200000),
('FREESHIP5', 0.0, 2, 20, '2025-11-30', 250000),
('FREESHIP6', 0.0, 2, 10, '2025-10-31', 300000),

-- Combo đặc biệt
('COMBO20K', 20000.0, 1, 30, '2025-12-31', 80000),
('QUATANG5', 5.0, 0, 100, '2025-12-31', 30000),
('SHOCKDEAL', 10.0, 0, 10, '2025-07-15', 100000),
('LASTCHANCE', 15.0, 0, 5, '2025-07-01', 120000),
('XMAS50', 50000.0, 1, 20, '2025-12-24', 300000);

INSERT INTO Orders (UserID, OrderDate, Status, DiscountCodeID)
VALUES
-- User 1
(1, '2025-06-01', N'Completed', 1),
(1, '2025-06-10', N'Processing', NULL),

-- User 2
(2, '2025-06-02', N'Shipped', 3),
(2, '2025-06-15', N'Pending', NULL),

-- User 3
(3, '2025-05-30', N'Completed', 2),
(3, '2025-06-05', N'Cancelled', NULL),

-- User 4
(4, '2025-06-03', N'Completed', 5),

-- User 5
(5, '2025-06-12', N'Pending', NULL),

-- User 6
(6, '2025-06-01', N'Shipped', 4),

-- User 7
(7, '2025-06-07', N'Processing', NULL),

-- Admin 1 (UserID 8)
(8, '2025-06-05', N'Completed', 1),
(8, '2025-06-13', N'Shipped', NULL),

-- Admin 2 (UserID 9)
(9, '2025-06-14', N'Processing', 6),

-- Admin 3 (UserID 10)
(10, '2025-06-11', N'Pending', NULL),
(10, '2025-06-17', N'Completed', 7),

-- Thêm 4 đơn lẻ để tăng số lượng
(3, '2025-06-18', N'Pending', NULL),
(4, '2025-06-18', N'Shipped', 8),
(5, '2025-06-19', N'Completed', NULL),
(7, '2025-06-20', N'Processing', NULL);

-- OrderDetails chưa dùng dc(chưa fix)
INSERT INTO OrderDetails (OrderID, ProductID, Quantity, UnitPrice)
VALUES
-- Order 1
(1, 1, 2, 15000),
(1, 5, 1, 30000),

-- Order 2
(2, 10, 1, 25000),

-- Order 3
(3, 12, 3, 20000),
(3, 14, 1, 50000),

-- Order 4
(4, 7, 1, 35000),

-- Order 5
(5, 3, 2, 10000),
(5, 8, 1, 45000),

-- Order 6
(6, 2, 1, 18000),

-- Order 7
(7, 22, 2, 25000),
(7, 23, 1, 29000),

-- Order 8
(8, 30, 1, 15000),

-- Order 9
(9, 35, 2, 20000),

-- Order 10
(10, 40, 1, 60000),
(10, 41, 1, 25000),

-- Order 11
(11, 44, 1, 17000),

-- Order 12
(12, 50, 2, 40000),

-- Order 13
(13, 55, 1, 22000),

-- Order 14
(14, 60, 3, 28000),

-- Order 15
(15, 65, 1, 19000),

-- Order 16
(16, 70, 2, 30000),

-- Order 17
(17, 75, 1, 27000),

-- Order 18
(18, 80, 2, 26000),

-- Order 19
(19, 85, 3, 31000),

-- Order 20
(20, 90, 1, 40000),
(20, 91, 1, 33000);

INSERT INTO Reviews (UserID, ProductID, Rating, Comment, ReviewDate)
VALUES
(1, 1, 5, N'Rất hài lòng với sản phẩm!', '2025-06-15'),
(1, 5, 4, N'Sản phẩm tốt nhưng giao hàng hơi lâu.', '2025-06-16'),
(1, 80, 5, N'Giá tốt, chất lượng tốt.', '2025-06-18'),

(2, 10, 3, N'Tạm ổn, không quá xuất sắc.', '2025-06-12'),
(2, 35, 5, N'Sản phẩm như mô tả, giao nhanh.', '2025-06-13'),
(2, 40, 4, N'Sử dụng tốt, sẽ mua lại.', '2025-06-17'),

(3, 12, 2, N'Chất lượng không như mong đợi.', '2025-06-10'),
(3, 14, 4, N'Sản phẩm được đóng gói kỹ.', '2025-06-11'),
(3, 85, 5, N'Tuyệt vời!', '2025-06-19'),

(4, 22, 5, N'Hài lòng.', '2025-06-13'),
(4, 25, 4, N'Đáng tiền.', '2025-06-14'),
(4, 29, 3, N'Cũng ổn.', '2025-06-14'),

(5, 35, 5, N'Rất tốt.', '2025-06-15'),
(5, 38, 4, N'Sản phẩm đúng như mong đợi.', '2025-06-16'),

(6, 45, 3, N'Sản phẩm bình thường.', '2025-06-12'),
(6, 50, 4, N'Giao hàng nhanh, sản phẩm ổn.', '2025-06-12'),

(7, 55, 5, N'Rất thích!', '2025-06-14'),
(7, 60, 5, N'Chất lượng tuyệt vời.', '2025-06-16'),
(7, 58, 4, N'Sản phẩm đẹp, đóng gói cẩn thận.', '2025-06-17'),

-- Một số người đánh giá thêm
(1, 83, 5, N'Đáng đồng tiền.', '2025-06-18'),
(2, 90, 4, N'Sẽ ủng hộ tiếp.', '2025-06-18'),
(3, 92, 3, N'Không tệ.', '2025-06-18'),
(4, 95, 2, N'Có thể cải thiện.', '2025-06-19'),
(5, 97, 5, N'Rất hài lòng.', '2025-06-19'),
(6, 99, 5, N'Siêu phẩm!', '2025-06-19');

---End---