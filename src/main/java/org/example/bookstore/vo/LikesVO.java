package org.example.bookstore.vo;

import lombok.Data;

@Data
public class LikesVO {
    private long likeId;
    private String memberId;
    private String bookId;
}
