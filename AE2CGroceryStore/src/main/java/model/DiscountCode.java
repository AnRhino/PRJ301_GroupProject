/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
public class DiscountCode {

    private int id;
    private String code;
    private double value;
    private int type;
    private int quantity;
    private Date expiryDate;
    private double minOrderValue;

    public DiscountCode() {
    }

    public DiscountCode(int id, String code, double value, int type, int quantity, Date expiryDate, double minOrderValue) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.type = type;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.minOrderValue = minOrderValue;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(double minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

}
