/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private boolean isHidden;

    public static final int DELIVERY_FEE_PERCENT = 10;

    public Order() {
    }

    public Order(int id, User user, LocalDateTime orderDate, LocalDateTime deliveryDate,
            OrderStatus status, DiscountCode discount, String phoneNumber, String address,
            List<OrderItem> orderItems) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.discount = discount;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderItems = orderItems;
    }

    public Order(int id, User user, LocalDateTime orderDate, LocalDateTime deliveryDate, OrderStatus status, DiscountCode discount, String phoneNumber, String address, List<OrderItem> orderItems, boolean isHidden) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.discount = discount;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderItems = orderItems;
        this.isHidden = isHidden;
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

    public String getOrderDateToString() {
        return orderDate.format(DateTimeFormatter.ISO_DATE);
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryDateToString() {
        return deliveryDate.format(DateTimeFormatter.ISO_DATE);
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

    public boolean isIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    /**
     * Return the total value of all items in an order before any discounts or
     * delivery fees are applied.
     *
     * @return the total value of all items
     */
    public int getSubtotal() {
        int subtotal = 0;
        for (OrderItem item : this.orderItems) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public int getDeliveryFee() {
        return this.getSubtotal() * DELIVERY_FEE_PERCENT / 100;
    }

    public int getDiscountValue() {
        int subtotal = this.getSubtotal();
        if (this.discount == null || this.discount.getMinOrderValue() > subtotal) {
            return 0;
        }
        switch (this.discount.getType()) {
            case 0:
                return subtotal * this.discount.getValue() / 100;
            case 1:
                return Math.min(subtotal, this.discount.getValue());
            default:
                return this.getDeliveryFee();
        }
    }

    /**
     * Return the final amount a customer needs to pay for an order after all
     * calculations are applied.
     *
     * @return the total payment
     */
    public int getTotalPayment() {
        return this.getSubtotal() * (100 + DELIVERY_FEE_PERCENT) / 100 - this.getDiscountValue();
    }

    public static void main(String[] args) {
        Order order = new Order(0, null, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null);

        System.out.println(order.getOrderDateToString());
    }

}
