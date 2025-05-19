package org.example.bookstore.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.bookstore.vo.AuthorVO;
import org.example.bookstore.vo.CategoryVO;
import org.example.bookstore.vo.PublisherVO;

import java.util.List;

@Mapper
public interface BookRepository {
    List<CategoryVO> selectCategory();

    List<AuthorVO> selectAuthor(String searchVal);

    List<PublisherVO> selectPublisher(String searchVal);
}
