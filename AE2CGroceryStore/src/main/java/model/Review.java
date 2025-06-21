/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class Review {
    
    private int reviewID;
    private int userID;
    private int productID;
    private int rating;
    private String comment;
    private Date date;

    public Review() {
    }

    public Review(int reviewID, int userID, int productID, int rating, String comment, Date date) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.productID = productID;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
