package org.example.bookstore.vo;

import lombok.Data;

@Data
public class UserMenuVO {

  private long id;
  private long memberId;
  private long menuId;
  private String path;
}
