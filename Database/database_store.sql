USE master
GO

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'GroceryStore')
BEGIN
	ALTER DATABASE GroceryStore SET OFFLINE WITH ROLLBACK IMMEDIATE;
	ALTER DATABASE GroceryStore SET ONLINE;
	DROP DATABASE GroceryStore;
END

CREATE DATABASE GroceryStore
GO

USE GroceryStore
GO

-- Roles table
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY,
    RoleName NVARCHAR(50)
);

-- Users table
CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    Username NVARCHAR(50) NOT NULL UNIQUE,
    Password NVARCHAR(100) NOT NULL,
    FullName NVARCHAR(100),
    Email NVARCHAR(100),
    RoleID INT FOREIGN KEY REFERENCES Roles(RoleID),
	ImagePath VARCHAR(100)
);

-- Categories table
CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY IDENTITY(1,1),
    CategoryName NVARCHAR(100),
	IsHidden BIT NOT NULL DEFAULT 0,
	ImagePath VARCHAR(100)
);

-- Products table
CREATE TABLE Products (
    ProductID INT PRIMARY KEY IDENTITY(1,1),
    ProductCode NVARCHAR(20) NOT NULL UNIQUE,
    ProductName NVARCHAR(100) NOT NULL,
    Quantity INT NOT NULL,
    Price INT NOT NULL,
    CategoryID INT FOREIGN KEY REFERENCES Categories(CategoryID) NOT NULL,
	IsHidden BIT NOT NULL DEFAULT 0,
	ImagePath VARCHAR(100)
);

-- Carts table
CREATE TABLE Carts (
    CartItemID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT FOREIGN KEY REFERENCES Users(UserID) NOT NULL,
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID) NOT NULL,
    Quantity INT NOT NULL
);

-- DiscountTypes table
CREATE TABLE DiscountTypes (
    DiscountTypeID INT PRIMARY KEY,
    TypeName NVARCHAR(50)
);

-- DiscountCodes table
CREATE TABLE DiscountCodes (
    DiscountCodeID INT PRIMARY KEY IDENTITY(1,1),
    Code NVARCHAR(50) UNIQUE NOT NULL,
    DiscountValue DECIMAL(10,2) DEFAULT 0,
    DiscountTypeID INT FOREIGN KEY REFERENCES DiscountTypes(DiscountTypeID) NOT NULL,
    QuantityAvailable INT,
    ExpiryDate DATE,
    MinOrderValue DECIMAL(10,2) DEFAULT 0,
	IsHidden BIT DEFAULT 0
);

-- StatusType table
CREATE TABLE StatusType (
	StatusID INT PRIMARY KEY,
	StatusDescription VARCHAR(50)
);

-- Orders table
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT FOREIGN KEY REFERENCES Users(UserID) NOT NULL,
    OrderDate DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
	DeliveryDate DATE,
    StatusID INT FOREIGN KEY REFERENCES StatusType(StatusID) DEFAULT 1,
    DiscountCodeID INT FOREIGN KEY REFERENCES DiscountCodes(DiscountCodeID),
	PhoneNumber VARCHAR(15),
	[Address] VARCHAR(255),
	Constraint CK_DeliveryDate CHECK(DeliveryDate >= OrderDate)
);

-- OrderItems table
CREATE TABLE OrderItems (
    OrderItemID INT PRIMARY KEY IDENTITY(1,1),
    OrderID INT FOREIGN KEY REFERENCES Orders(OrderID),
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID),
    Quantity INT,
    UnitPrice INT
);

-- Reviews table
CREATE TABLE Reviews (
    ReviewID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID),
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    Comment NVARCHAR(500),
    ReviewTime DATETIME DEFAULT GETDATE()
);

-- Insert sample data for Roles table
INSERT INTO Roles VALUES (0, 'Customer'), (1, 'Admin');

-- Insert sample data for StatusType sample
INSERT INTO StatusType VALUES
(0, 'Cancelled'),
(1, 'Pending'),
(2, 'Processing'),
(3, 'Shipped'),
(4, 'Completed');