package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.exception.CustomBadRequestException;
import org.example.bookstore.service.BookService;
import org.example.bookstore.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Tag(name = "User API", description = "사용자 페이지 API")
@RestController
public class UserController {

    @Autowired
    private BookService bookService;

    @Operation(summary="도서 검색", description="사용자 페이지 도서 조회 검색기준 - 도서명,출판사,작가명")
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchBook(@PathVariable("keyword")String searchVar) {
        try {
            List<BookVO> books = bookService.searchBook(searchVar);
            return ResponseEntity.ok(books);
        } catch (Exception e){
            throw new CustomBadRequestException("도서 검색 실패");
        }
    }


    @Operation(summary="도서 조회", description="사용자 페이지 카테고리별 도서 조회")
    @PostMapping("/books")
    public ResponseEntity<?> selectBookList(@RequestBody List<Integer> list) {
        try {
            List<BookVO> books = bookService.selectBookListByCategoryId(list);
            return ResponseEntity.ok(books);
        } catch (Exception e){
            throw new CustomBadRequestException("카테고리별 도서 조회 실패");
        }
    }


}
