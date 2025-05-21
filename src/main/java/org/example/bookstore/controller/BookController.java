package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.exception.CustomBadRequestException;
import org.example.bookstore.service.BookService;
import org.example.bookstore.vo.AuthorVO;
import org.example.bookstore.vo.BookVO;
import org.example.bookstore.vo.CategoryVO;
import org.example.bookstore.vo.PublisherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    @Operation(summary="책 카테고리 추가", description="관리자페이지 책 카테고리 추가")
    @PostMapping ("/category/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryVO vo){
        try {
//            List<CategoryVO> categories = bookService.selectCategory();
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            throw new CustomBadRequestException("카테고리 추가 실패");
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


    @PostMapping("/upload")
    public ResponseEntity<?> uploadBook(@ModelAttribute BookVO vo,
            @RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
        try {
            bookService.saveBook(vo,imgFile);
            return ResponseEntity.ok("도서 등록 성공");
        } catch (Exception e) {
            throw new CustomBadRequestException("도서 등록 실패");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> selectBookList() {
        try {
            List<BookVO> bookList = bookService.selectBookList();

            return ResponseEntity.ok(bookList);
        } catch (Exception e) {
            throw new CustomBadRequestException("도서 목록 불러오기 실패");
        }
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<BookVO> getBookById(@PathVariable("id") int bookId) {
        BookVO book = bookService.selectBookByBookId(bookId);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateBook(@ModelAttribute BookVO vo,
                                             @RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
        try {
            bookService.updateBook(vo,imgFile);
            return ResponseEntity.ok("수정 성공");
        } catch (Exception e) {
            throw new CustomBadRequestException("수정 실패");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookOne(@PathVariable("id") int bookId) {
        try {
            bookService.deleteBookOne(bookId);
            return ResponseEntity.ok("삭제 성공");
        } catch (Exception e) {
            throw new CustomBadRequestException("삭제 실패");
        }
    }

    @PostMapping("/delete-list")
    public ResponseEntity<String> deleteBookList(@RequestBody List<Integer> bookIdList) {
        try {
            bookService.deleteBookList(bookIdList);
            return ResponseEntity.ok("삭제 성공");
        } catch (Exception e) {
            throw new CustomBadRequestException("삭제 실패");
        }
    }

}
