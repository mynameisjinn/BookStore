package org.example.bookstore.vo;

import lombok.Data;

@Data
public class OrderDetailVO {
    private long orderDetailId;
    private String bookId;
    private String orderId;
    private String quantity;
    private String unitPrice;
}
