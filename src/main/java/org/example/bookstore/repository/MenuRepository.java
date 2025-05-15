package org.example.bookstore.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.bookstore.vo.MemberVO;
import org.example.bookstore.vo.MenuVO;

import java.util.List;

@Mapper
public interface MenuRepository {
    List<MenuVO> selectMenu(@Param("role") String role);
}
