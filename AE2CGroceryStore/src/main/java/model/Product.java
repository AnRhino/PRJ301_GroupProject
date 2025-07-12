/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class Product {

    private int productID;
    private String productCode;
    private String productName;
    private int quantity;
    private int price;
    private int sumQuality;
    private Category category;
    private String coverImg;
    private boolean isHidden;

    public Product() {
    }

    public Product(int productID, String productCode, String productName,
            int quantity, int price, Category category, String coverImg, boolean isHidden) {
        this.productID = productID;
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.coverImg = coverImg;
        this.isHidden = isHidden;
    }

    public Product(int productID, String productCode, String productName, int quantity, int price, Category category) {
        this.productID = productID;
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public Product(int productID) {
        this.productID = productID;
    }

    public Product(int productID, String productName, String coverImg, int sumQuality) {
        this.productID = productID;
        this.sumQuality = sumQuality;
        this.coverImg = coverImg;
        this.productName = productName;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public boolean isIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }
}
