/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
public class DiscountCode {

    private int id;
    private String code;
    private int value;
    private int type;
    private int quantity;
    private LocalDate expiryDate;
    private int minOrderValue;
    private int isHidden;

    public DiscountCode() {
    }

    public DiscountCode(int id, int value, int type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }

    public DiscountCode(int id, String code, int value, int type, int quantity, LocalDate expiryDate, int minOrderValue) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.type = type;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.minOrderValue = minOrderValue;
    }

    public DiscountCode(int id, String code, int value, int type, int quantity, LocalDate expiryDate, int minOrderValue, int isHidden) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.type = type;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.minOrderValue = minOrderValue;
        this.isHidden = isHidden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(int minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public int getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(int isHidden) {
        this.isHidden = isHidden;
    }

}
