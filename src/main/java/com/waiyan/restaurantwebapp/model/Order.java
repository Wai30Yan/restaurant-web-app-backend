package com.waiyan.restaurantwebapp.model;

public class Order {
    public String itemId;
    public Integer count;

    public Order(String itemId, Integer count) {
        this.itemId = itemId;
        this.count = count;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Order{" +
                "count=" + count +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}
