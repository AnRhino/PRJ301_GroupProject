/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
public class Order {

    private int id;
    private User user;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private OrderStatus status;
    private DiscountCode discount;
    private String phoneNumber;
    private String address;
    private List<OrderItem> orderItems;
    private int deliveryFee;

    public Order() {
    }

    public Order(int id, User user, LocalDateTime orderDate, LocalDateTime deliveryDate,
            OrderStatus status, DiscountCode discount, String phoneNumber, String address,
            List<OrderItem> orderItems) {
        this(id, user, orderDate, deliveryDate, status, discount, phoneNumber,
                address, orderItems, 0);
    }

    public Order(int id, User user, LocalDateTime orderDate, LocalDateTime deliveryDate,
            OrderStatus status, DiscountCode discount, String phoneNumber, String address,
            List<OrderItem> orderItems, int deliveryFee) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.discount = discount;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderItems = orderItems;
        this.deliveryFee = deliveryFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public DiscountCode getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountCode discount) {
        this.discount = discount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getSubtotal() {
        int subtotal = 0;
        for (OrderItem item : this.orderItems) {
            subtotal += item.getTotalAmount();
        }
        return subtotal + this.deliveryFee;
    }
    
    public int getDiscountedSubtotal() {
        
    }

}
