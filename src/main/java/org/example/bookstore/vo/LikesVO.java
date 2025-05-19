package org.example.bookstore.vo;

import lombok.Data;

@Data
public class LikesVO {
    private int likeId;
    private String memberId;
    private String bookId;
}
