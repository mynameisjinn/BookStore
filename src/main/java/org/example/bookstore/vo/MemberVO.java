package org.example.bookstore.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MemberVO {
    private int id;
    private String address;
    private String email;
    private String password;
    private String roleId;

}
