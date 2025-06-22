USE master
GO

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
    UserID INT PRIMARY KEY IDENTITY,
    Username NVARCHAR(50) NOT NULL,
    Password NVARCHAR(100) NOT NULL,
    FullName NVARCHAR(100),
    Email NVARCHAR(100),
    RoleID INT FOREIGN KEY REFERENCES Roles(RoleID)
);

-- Categories table
CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY IDENTITY,
    CategoryName NVARCHAR(100)
);

-- Products table
CREATE TABLE Products (
    ProductID INT PRIMARY KEY IDENTITY,
    ProductCode NVARCHAR(20),
    ProductName NVARCHAR(100),
    Quantity INT,
    Price INT,
    CategoryID INT FOREIGN KEY REFERENCES Categories(CategoryID)
);

-- Carts table
CREATE TABLE Carts (
    CartItemID INT PRIMARY KEY IDENTITY,
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID),
    Quantity INT
);

-- DiscountTypes table
CREATE TABLE DiscountTypes (
    DiscountTypeID INT PRIMARY KEY,
    TypeName NVARCHAR(50)
);

-- DiscountCodes table
CREATE TABLE DiscountCodes (
    DiscountCodeID INT PRIMARY KEY IDENTITY,
    Code NVARCHAR(50) UNIQUE,
    DiscountValue DECIMAL(10,2),
    DiscountTypeID INT FOREIGN KEY REFERENCES DiscountTypes(DiscountTypeID),
    QuantityAvailable INT,
    ExpiryDate DATE,
    MinOrderValue DECIMAL(10,2)
);

-- Orders table
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY IDENTITY,
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    OrderDate DATE,
    Status NVARCHAR(50),
    DiscountCodeID INT FOREIGN KEY REFERENCES DiscountCodes(DiscountCodeID)
);

-- OrderDetails table
CREATE TABLE OrderDetails (
    OrderDetailID INT PRIMARY KEY IDENTITY,
    OrderID INT FOREIGN KEY REFERENCES Orders(OrderID),
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID),
    Quantity INT,
    UnitPrice INT
);

-- Reviews table
CREATE TABLE Reviews (
    ReviewID INT PRIMARY KEY IDENTITY,
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID),
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    Comment NVARCHAR(500),
    ReviewDate DATE
);

-- Insert sample data
INSERT INTO Roles VALUES (0, 'Customer'), (1, 'Admin');
