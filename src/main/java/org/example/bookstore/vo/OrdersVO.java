package org.example.bookstore.vo;

import lombok.Data;

@Data
public class OrdersVO {
    private long orderId;
    private String memberId;
    private String orderDate;
    private String totalPrice;
    private String orderState;
}
