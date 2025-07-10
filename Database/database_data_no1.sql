USE [GroceryStore]
GO

-- Chạy được nhiều lần.
-- Password của tất cả là người dung là Demo@123 (dùng md5 để mã hóa).
-- Tên username là tên + họ tên đệm viết tắt (VD: Nguyen Van Nam ==> NamNV). 
-- Password của từng thành viên là CE + MSSV + @.

-- Xóa data cũ nếu tồn tại.
-- Reset lại id tự tăng của identity.
IF EXISTS (SELECT 1 FROM OrderItems)
	DBCC CHECKIDENT (OrderItems, RESEED, 0);
    DELETE FROM OrderItems;

IF EXISTS (SELECT 1 FROM Orders)
DBCC CHECKIDENT (Orders, RESEED, 0);
    DELETE FROM Orders;

IF EXISTS (SELECT 1 FROM Carts)
	DBCC CHECKIDENT (Carts, RESEED, 0);
    DELETE FROM Carts;

IF EXISTS (SELECT 1 FROM Reviews)
	DBCC CHECKIDENT (Reviews, RESEED, 0);
    DELETE FROM Reviews;

IF EXISTS (SELECT 1 FROM DiscountCodes)
	DBCC CHECKIDENT (DiscountCodes, RESEED, 0);
    DELETE FROM DiscountCodes;

IF EXISTS (SELECT 1 FROM DiscountTypes)
    DELETE FROM DiscountTypes;

IF EXISTS (SELECT 1 FROM Products)
	DBCC CHECKIDENT (Products, RESEED, 0);
    DELETE FROM Products;

IF EXISTS (SELECT 1 FROM Categories)
	DBCC CHECKIDENT (Categories, RESEED, 0);
    DELETE FROM Categories;

IF EXISTS (SELECT 1 FROM Users)
	DBCC CHECKIDENT (Users, RESEED, 0);
    DELETE FROM Users;

-- 10 Categories
INSERT INTO Categories (CategoryName, IsHidden, ImagePath)
VALUES

(N'Beverages', 0, NULL), 
(N'Snacks', 0, NULL), 
(N'Fruits', 0, NULL), 
(N'Vegetables', 0, NULL), 
(N'Dairy', 0, NULL),
(N'Bakery', 0, NULL), 
(N'Meats', 0, NULL), 
(N'Seafood', 0, NULL), 
(N'Canned Goods', 0, NULL), 
(N'Frozen Foods', 0, NULL);

-- Beverages (CategoryID = 1)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID, IsHidden, ImagePath)
VALUES 
('BEV001', 'Coca-Cola', 100, 10000, 1, 0, NULL),
('BEV002', 'Pepsi', 120, 9500, 1, 0, NULL),
('BEV003', 'Sprite', 90, 10000, 1, 0, NULL),
('BEV004', 'Fanta', 85, 9750, 1, 0, NULL),
('BEV005', '7Up', 80, 10200, 1, 0, NULL),
('BEV006', 'Red Bull', 70, 15000, 1, 0, NULL),
('BEV007', 'Monster', 60, 16000, 1, 0, NULL),
('BEV008', 'Iced Tea', 100, 8500, 1, 0, NULL),
('BEV009', 'Lemonade', 95, 7000, 1, 0, NULL),
('BEV010', 'Water Bottle', 200, 5000, 1, 0, NULL);

-- Snacks (CategoryID = 2)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID, IsHidden, ImagePath)
VALUES 
('SNK001', 'Lays Chips', 150, 12000, 2, 0, NULL),
('SNK002', 'Doritos', 140, 13000, 2, 0, NULL),
('SNK003', 'Pringles', 130, 14000, 2, 0, NULL),
('SNK004', 'KitKat', 110, 6000, 2, 0, NULL),
('SNK005', 'Snickers', 100, 6500, 2, 0, NULL),
('SNK006', 'M&Ms', 120, 7000, 2, 0, NULL),
('SNK007', 'Twix', 90, 6000, 2, 0, NULL),
('SNK008', 'Oreo', 80, 8000, 2, 0, NULL),
('SNK009', 'Cheez-It', 70, 9000, 2, 0, NULL),
('SNK010', 'Trail Mix', 60, 10000, 2, 0, NULL);

-- Fruits (CategoryID = 3)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID, IsHidden, ImagePath)
VALUES 
('FRT001', 'Apple', 100, 5000, 3, 0, NULL),
('FRT002', 'Banana', 120, 4000, 3, 0, NULL),
('FRT003', 'Orange', 110, 4500, 3, 0, NULL),
('FRT004', 'Mango', 90, 6000, 3, 0, NULL),
('FRT005', 'Pineapple', 80, 7000, 3, 0, NULL),
('FRT006', 'Grapes', 95, 5500, 3, 0, NULL),
('FRT007', 'Strawberry', 85, 8000, 3, 0, NULL),
('FRT008', 'Blueberry', 70, 9000, 3, 0, NULL),
('FRT009', 'Watermelon', 60, 10000, 3, 0, NULL),
('FRT010', 'Papaya', 50, 6500, 3, 0, NULL);

-- Vegetables (CategoryID = 4)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID, IsHidden, ImagePath)
VALUES 
('VEG001', 'Carrot', 100, 3000, 4, 0, NULL),
('VEG002', 'Broccoli', 90, 4000, 4, 0, NULL),
('VEG003', 'Spinach', 85, 3500, 4, 0, NULL),
('VEG004', 'Cabbage', 80, 2500, 4, 0, NULL),
('VEG005', 'Potato', 200, 2000, 4, 0, NULL),
('VEG006', 'Tomato', 190, 2200, 4, 0, NULL),
('VEG007', 'Onion', 180, 1800, 4, 0, NULL),
('VEG008', 'Garlic', 150, 2500, 4, 0, NULL),
('VEG009', 'Cucumber', 160, 2800, 4, 0, NULL),
('VEG010', 'Lettuce', 170, 3200, 4, 0, NULL);

-- Dairy (CategoryID = 5)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID, IsHidden, ImagePath)
VALUES 
('DRY001', 'Milk', 100, 6000, 5, 0, NULL),
('DRY002', 'Yogurt', 90, 5500, 5, 0, NULL),
('DRY003', 'Cheddar Cheese', 80, 7000, 5, 0, NULL),
('DRY004', 'Butter', 70, 6500, 5, 0, NULL),
('DRY005', 'Cream', 60, 5000, 5, 0, NULL),
('DRY006', 'Cottage Cheese', 50, 6000, 5, 0, NULL),
('DRY007', 'Sour Cream', 45, 4500, 5, 0, NULL),
('DRY008', 'Ghee', 40, 8000, 5, 0, NULL),
('DRY009', 'Milk Powder', 55, 9000, 5, 0, NULL),
('DRY010', 'Condensed Milk', 60, 7500, 5, 0, NULL);

-- Bakery (CategoryID = 6)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID, IsHidden, ImagePath)
VALUES 
('BKY001', 'White Bread', 100, 4000, 6, 0, NULL),
('BKY002', 'Whole Grain Bread', 90, 4500, 6, 0, NULL),
('BKY003', 'Croissant', 80, 5000, 6, 0, NULL),
('BKY004', 'Baguette', 70, 4800, 6, 0, NULL),
('BKY005', 'Donut', 60, 3500, 6, 0, NULL),
('BKY006', 'Cake', 50, 8000, 6, 0, NULL),
('BKY007', 'Muffin', 40, 3800, 6, 0, NULL),
('BKY008', 'Bagel', 45, 4200, 6, 0, NULL),
('BKY009', 'Biscuit', 55, 4000, 6, 0, NULL),
('BKY010', 'Pastry', 60, 5500, 6, 0, NULL);

-- Meats (CategoryID = 7)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID, IsHidden, ImagePath)
VALUES 
('MEAT001', 'Chicken Breast', 100, 10000, 7, 0, NULL),
('MEAT002', 'Beef Steak', 90, 15000, 7, 0, NULL),
('MEAT003', 'Pork Chop', 85, 12000, 7, 0, NULL),
('MEAT004', 'Bacon', 80, 9000, 7, 0, NULL),
('MEAT005', 'Sausage', 70, 8000, 7, 0, NULL),
('MEAT006', 'Ground Beef', 60, 10500, 7, 0, NULL),
('MEAT007', 'Turkey', 50, 13000, 7, 0, NULL),
('MEAT008', 'Ham', 45, 11000, 7, 0, NULL),
('MEAT009', 'Ribs', 55, 14000, 7, 0, NULL),
('MEAT010', 'Lamb', 60, 16000, 7, 0, NULL);

-- Seafood (CategoryID = 8)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID, IsHidden, ImagePath)
VALUES 
('SF001', 'Salmon', 100, 14000, 8, 0, NULL),
('SF002', 'Shrimp', 90, 13000, 8, 0, NULL),
('SF003', 'Tuna', 85, 12500, 8, 0, NULL),
('SF004', 'Crab', 80, 15000, 8, 0, NULL),
('SF005', 'Lobster', 70, 20000, 8, 0, NULL),
('SF006', 'Squid', 60, 10000, 8, 0, NULL),
('SF007', 'Clam', 50, 9000, 8, 0, NULL),
('SF008', 'Oyster', 45, 11000, 8, 0, NULL),
('SF009', 'Mackerel', 55, 8500, 8, 0, NULL),
('SF010', 'Anchovy', 60, 7000, 8, 0, NULL);

-- Canned Goods (CategoryID = 9)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID, IsHidden, ImagePath)
VALUES 
('CAN001', 'Canned Beans', 100, 4000, 9, 0, NULL),
('CAN002', 'Canned Corn', 90, 3500, 9, 0, NULL),
('CAN003', 'Canned Tuna', 85, 5000, 9, 0, NULL),
('CAN004', 'Canned Tomatoes', 80, 3800, 9, 0, NULL),
('CAN005', 'Canned Soup', 70, 4500, 9, 0, NULL),
('CAN006', 'Canned Peas', 60, 3200, 9, 0, NULL),
('CAN007', 'Canned Pineapple', 50, 4800, 9, 0, NULL),
('CAN008', 'Canned Chicken', 45, 6000, 9, 0, NULL),
('CAN009', 'Canned Fruit Cocktail', 55, 5500, 9, 0, NULL),
('CAN010', 'Canned Mushroom', 60, 4200, 9, 0, NULL);

-- Frozen Foods (CategoryID = 10)
INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID, IsHidden, ImagePath)
VALUES 
('FRZ001', 'Frozen Pizza', 100, 10000, 10, 0, NULL),
('FRZ002', 'Frozen Vegetables', 90, 5000, 10, 0, NULL),
('FRZ003', 'Frozen Fries', 85, 4000, 10, 0, NULL),
('FRZ004', 'Frozen Chicken Nuggets', 80, 8000, 10, 0, NULL),
('FRZ005', 'Frozen Fish Fillet', 70, 9000, 10, 0, NULL),
('FRZ006', 'Frozen Burger Patty', 60, 7500, 10, 0, NULL),
('FRZ007', 'Frozen Dumplings', 50, 6500, 10, 0, NULL),
('FRZ008', 'Frozen Spring Rolls', 45, 6000, 10, 0, NULL),
('FRZ009', 'Frozen Pasta', 55, 5500, 10, 0, NULL),
('FRZ010', 'Frozen Fruit Mix', 60, 7000, 10, 0, NULL);

-- Giả sử RoleID = 0 là Customer, 1 là Admin
-- Password của tất cả là người dùng là Demo@123 (dùng md5 để mã hóa).
-- Tên username là tên + họ tên đệm viết tắt (VD: Nguyen Van Nam ==> NamNV). 
-- Password của từng thành viên là CE + MSSV + @.
INSERT INTO Users (Username, Password, FullName, Email, RoleID)
VALUES 
('user1', 'e14c05f0dc27e6be1fc127abaf474a59', N'Nguyen Van A', 'user1@gmail.com', 0),
('user2', 'e14c05f0dc27e6be1fc127abaf474a59', N'Le Thi B', 'user2@example.com', 0),
('user3', 'e14c05f0dc27e6be1fc127abaf474a59', N'Tran Van C', 'user3@example.com', 0),
('user4', 'e14c05f0dc27e6be1fc127abaf474a59', N'Pham Thi D', 'user4@example.com', 0),
('user5', 'e14c05f0dc27e6be1fc127abaf474a59', N'Ngo Van E', 'user5@example.com', 0),
('user6', 'e14c05f0dc27e6be1fc127abaf474a59', N'Huynh Thi F', 'user6@example.com', 0),
('user7', 'e14c05f0dc27e6be1fc127abaf474a59', N'Dang Van G', 'user7@example.com', 0),
('admin', 'e14c05f0dc27e6be1fc127abaf474a59', N'Quan Trị Vien', 'admin@gmail.com', 1),
('AnNHP', 'a61acd6c759f831eb8e2aa92098bf5cf', N'Nguyen Ho Phuoc An', 'AnNHPCE190747@example.com', 1),
('PhucDC', '7d6f97a810a93b6d5091d0bf81ac38a2', N'Dinh Cong Phuc', 'PhucDCCE190770@gmail.com', 1),
('ThoPD', '4fc5da50d877670f36d0081e18b95361', N'Phan Duc Tho', 'ThoPDCE191246@example.com', 1),
('TriLT', '9c766d74d3539593e0256781c23c40c0', N'Le Thien Tri', 'TriLTCE190149@gmail.com', 1),
('KhangVM', '40b2d50c63a3516fba5789a5a13d63d6', N'Vu Minh Khang', 'KhangVMCE191371@example.com', 1);

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

-- Admin cũng có cart để test (không cần thiết nhưng có thể dùng demo)
(8, 65, 2),
(8, 68, 1),
(8, 70, 3),
(8, 68, 1),
(8, 70, 3);

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

INSERT INTO Orders (UserID, OrderDate, StatusID, DiscountCodeID)
VALUES
-- User 1
(1, '2025-06-01', 4, 1),
(1, '2025-06-10', 2, NULL),

-- User 2
(2, '2025-06-02', 3, 3),
(2, '2025-06-15', 1, NULL),

-- User 3
(3, '2025-05-30', 4, 2),
(3, '2025-06-05', 0, NULL),

-- User 4
(4, '2025-06-03', 4, 5),

-- User 5
(5, '2025-06-12', 1, NULL),

-- User 6
(6, '2025-06-01', 3, 4),

-- User 7
(7, '2025-06-07', 2, NULL),

-- Admin (UserID 8)
(8, '2025-06-05', 4, 1),
(8, '2025-06-13', 3, NULL),
(8, '2025-06-14', 2, 6),
(8, '2025-06-11', 1, NULL),
(8, '2025-06-17', 4, 7),

-- Thêm 4 đơn lẻ để tăng số lượng
(3, '2025-06-18', 1, NULL),
(4, '2025-06-18', 3, 8),
(5, '2025-06-19', 4, NULL),
(7, '2025-06-20', 2, NULL);

-- OrderItems chưa dùng dc(chưa fix)
INSERT INTO OrderItems (OrderID, ProductID, Quantity, UnitPrice)
VALUES
(1, 1, 2, 15000),
(1, 5, 1, 30000),

(2, 10, 1, 25000),

(3, 12, 3, 20000),
(3, 14, 1, 50000),

(4, 7, 1, 35000),

(5, 3, 2, 10000),
(5, 8, 1, 45000),

(6, 2, 1, 18000),

(7, 22, 2, 25000),
(7, 23, 1, 29000),

(8, 30, 1, 15000),

(9, 35, 2, 20000),

(10, 40, 1, 60000),
(10, 41, 1, 25000),

(11, 44, 1, 17000),

(12, 50, 2, 40000),

(13, 55, 1, 22000),

(14, 60, 3, 28000),

(15, 65, 1, 19000),

(16, 70, 2, 30000),

(17, 75, 1, 27000),

(18, 80, 2, 26000),

(19, 85, 3, 31000);

INSERT INTO Reviews (UserID, ProductID, Rating, Comment, ReviewTime)
VALUES
(1, 1, 5, N'Rất hài lòng với sản phẩm!', '2025-06-15 09:15:00'),
(1, 5, 4, N'Sản phẩm tốt nhưng giao hàng hơi lâu.', '2025-06-16 11:30:00'),
(1, 80, 5, N'Giá tốt, chất lượng tốt.', '2025-06-18 14:45:00'),

(2, 10, 3, N'Tạm ổn, không quá xuất sắc.', '2025-06-12 10:20:00'),
(2, 35, 5, N'Sản phẩm như mô tả, giao nhanh.', '2025-06-13 16:10:00'),
(2, 40, 4, N'Sử dụng tốt, sẽ mua lại.', '2025-06-17 18:25:00'),

(3, 12, 2, N'Chất lượng không như mong đợi.', '2025-06-10 08:00:00'),
(3, 14, 4, N'Sản phẩm được đóng gói kỹ.', '2025-06-11 09:30:00'),
(3, 85, 5, N'Tuyệt vời!', '2025-06-19 17:50:00'),

(4, 22, 5, N'Hài lòng.', '2025-06-13 12:40:00'),
(4, 25, 4, N'Đáng tiền.', '2025-06-14 14:00:00'),
(4, 29, 3, N'Cũng ổn.', '2025-06-14 15:10:00'),

(5, 35, 5, N'Rất tốt.', '2025-06-15 13:00:00'),
(5, 38, 4, N'Sản phẩm đúng như mong đợi.', '2025-06-16 10:30:00'),

(6, 45, 3, N'Sản phẩm bình thường.', '2025-06-12 08:45:00'),
(6, 50, 4, N'Giao hàng nhanh, sản phẩm ổn.', '2025-06-12 10:00:00'),

(7, 55, 5, N'Rất thích!', '2025-06-14 16:20:00'),
(7, 60, 5, N'Chất lượng tuyệt vời.', '2025-06-16 19:00:00'),
(7, 58, 4, N'Sản phẩm đẹp, đóng gói cẩn thận.', '2025-06-17 20:30:00'),

-- Một số người đánh giá thêm
(1, 83, 5, N'Đáng đồng tiền.', '2025-06-18 11:00:00'),
(2, 90, 4, N'Sẽ ủng hộ tiếp.', '2025-06-18 12:00:00'),
(3, 92, 3, N'Không tệ.', '2025-06-18 13:00:00'),
(4, 95, 2, N'Có thể cải thiện.', '2025-06-19 14:00:00'),
(5, 97, 5, N'Rất hài lòng.', '2025-06-19 15:00:00'),
(6, 99, 5, N'Siêu phẩm!', '2025-06-19 16:00:00');

---End---