package org.example.bookstore.service;

import org.example.bookstore.repository.BookRepository;
import org.example.bookstore.vo.AuthorVO;
import org.example.bookstore.vo.CategoryVO;
import org.example.bookstore.vo.PublisherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<CategoryVO> selectCategory(){
        return bookRepository.selectCategory();
    }

    public List<AuthorVO> selectAuthor(String searchVal) { return bookRepository.selectAuthor(searchVal); }

    public List<PublisherVO> selectPublisher(String searchVal) { return bookRepository.selectPublisher(searchVal); }
}
