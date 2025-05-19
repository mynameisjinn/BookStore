package org.example.bookstore.vo;

import lombok.Data;

@Data
public class MenuVO {

  private int menuId;
  private String name;
  private String path;
  private int parentId;
  private int depth;

}
