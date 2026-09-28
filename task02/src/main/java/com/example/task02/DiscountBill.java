package com.example.task02;

/**
 * Счет к оплате со скидкой.
 * Скидка задается в процентах.
 */
public class DiscountBill extends Bill {

    private final int discount; // скидка в процентах

    public DiscountBill(int discount) {
        this.discount = discount;
    }


    public int getDiscount() {
        return discount;
    }


    public int getDiscountPercent() {
        return discount;
    }

    public long getDiscountAmount() {
        return super.getPrice() * discount / 100;
    }



}