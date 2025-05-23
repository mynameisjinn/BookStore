package org.example.bookstore.vo;

import lombok.Data;

@Data
public class CartVO {
    private long cartId;
    private long memberId;
    private String bookId;
    private String quantity;
}
