package org.example.bookstore.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.bookstore.vo.CategoryVO;
import org.example.bookstore.vo.MenuVO;

import java.util.List;

@Mapper
public interface AdminRepository {
    List<CategoryVO> selectCategory();
}
