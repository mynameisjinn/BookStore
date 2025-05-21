package org.example.bookstore.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.bookstore.vo.AuthorVO;
import org.example.bookstore.vo.BookVO;
import org.example.bookstore.vo.CategoryVO;
import org.example.bookstore.vo.PublisherVO;

import java.awt.print.Book;
import java.util.List;

@Mapper
public interface BookRepository {
    List<CategoryVO> selectCategory();

    List<AuthorVO> selectAuthor(String searchVal);

    List<PublisherVO> selectPublisher(String searchVal);

    void saveBook(BookVO vo);

    List<BookVO> selectBookList();

    BookVO selectBookByBookId(int bookId);

    void updateBook (BookVO vo);

    List<BookVO> selectBookListBySearchVar(String searchVal);

    void deleteBookOne(int bookId);

    void deleteBookList(List<Integer> ids);
}
