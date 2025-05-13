package org.example.bookstore.vo;

import lombok.Data;

@Data
public class MenuVO {

  private long menuId;
  private String name;
  private String path;
  private long parentId;
  private long depth;

}
