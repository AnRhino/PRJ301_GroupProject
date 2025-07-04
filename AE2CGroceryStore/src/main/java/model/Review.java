/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class Review {
    
    private int reviewID;
    private User user;
    private Product product;
    private int rating;
    private String comment;
    private LocalDateTime date;

    public Review() {
    }

    public Review(int reviewID, User user, Product product, int rating, String comment, LocalDateTime date) {
        this.reviewID = reviewID;
        this.user = user;
        this.product = product;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    
}
