package org.example.bookstore.vo;

import lombok.Data;

@Data
public class CategoryVO {

  private int categoryId;
  private String name;
  private int parentId;
  private int depth;

}
