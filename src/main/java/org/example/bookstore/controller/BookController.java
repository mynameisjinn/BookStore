package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.exception.CustomBadRequestException;
import org.example.bookstore.service.BookService;
import org.example.bookstore.vo.AuthorVO;
import org.example.bookstore.vo.CategoryVO;
import org.example.bookstore.vo.PublisherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/book")
@Tag(name = "Admin - Book", description = "관리자 책 관리 API")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary="책 카테고리조회", description="관리자페이지 책 카테고리조회")
    @GetMapping("/category")
    public ResponseEntity<?> selectCategory(){
        try {
            List<CategoryVO> categories = bookService.selectCategory();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new CustomBadRequestException("카테고리 데이터 로드 실패");
        }
    }

    @GetMapping("/author")
    public ResponseEntity<?> authorList(@RequestParam("searchVal") String searchVal) {
        try {
            List<AuthorVO> authors = bookService.selectAuthor(searchVal);
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            throw new CustomBadRequestException("작가 목록 조회 실패");
        }
    }

    @GetMapping("/publisher")
    public ResponseEntity<?> publisherList(@RequestParam("searchVal") String searchVal) {
        try {
            List<PublisherVO> publishers = bookService.selectPublisher(searchVal);
            return ResponseEntity.ok(publishers);
        } catch (Exception e) {
            throw new CustomBadRequestException("출판사 목록 조회 실패");
        }
    }
}
