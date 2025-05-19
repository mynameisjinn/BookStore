package org.example.bookstore.vo;

import lombok.Data;

@Data
public class BookVO {
    private int id;
    private String title;
    private int authorId;
    private String bookOrigin;
    private String price;
    private String publishedDate;
    private int publisherId;
    private int categoryId;
    private String imgPath;

}
