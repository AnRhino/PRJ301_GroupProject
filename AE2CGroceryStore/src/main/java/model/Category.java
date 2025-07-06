/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class Category {
    
    private int categoryID;
    private String categoryName;
    private String coverImg;
    private boolean isHidden;

    public Category() {
    }

    public Category(int categoryID, String categoryName, String coverImg, boolean isHidden) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.coverImg = coverImg;
        this.isHidden = isHidden;
    }

    public Category(int categoryID, String categoryName, boolean isHidden) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.isHidden = isHidden;
    }

    public Category(int categoryID, String categoryName, String coverImg) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.coverImg = coverImg;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public boolean checkIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }
    
}
