package org.example.bookstore.vo;

import lombok.Data;

@Data
public class BookVO {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private String bookOrigin;
    private String price;
    private String publishedDate;
    private String publisherId;

}
