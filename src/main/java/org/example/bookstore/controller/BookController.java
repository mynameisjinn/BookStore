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

    private final BookService bookService;

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

    @Operation(summary="모달 내 작가 조회 - 검색어 사용", description="도서 등록시 모낼 내에서 작가조회")
    @GetMapping("/author")
    public ResponseEntity<?> authorList(@RequestParam("searchVal") String searchVal) {
        try {
            List<AuthorVO> authors = bookService.selectAuthor(searchVal);
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            throw new CustomBadRequestException("작가 목록 조회 실패");
        }
    }

    @Operation(summary="모달 내 작가 조회 - 전체", description="도서 등록시 모낼 내에서 작가조회. 화면단에서 검색어 필터링")
    @GetMapping("/author/all")
    public ResponseEntity<?> authorAll() {
        try {
            List<AuthorVO> authors = bookService.selectAuthorAll();
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            throw new CustomBadRequestException("작가 전체 조회 실패");
        }
    }

    @Operation(summary="모달 내 출판사 조회 - 검색어 사용", description="도서 등록시 모낼 내에서 출판사조회")
    @GetMapping("/publisher")
    public ResponseEntity<?> publisherList(@RequestParam("searchVal") String searchVal) {
        try {
            List<PublisherVO> publishers = bookService.selectPublisher(searchVal);
            return ResponseEntity.ok(publishers);
        } catch (Exception e) {
            throw new CustomBadRequestException("출판사 목록 조회 실패");
        }
    }

    @Operation(summary="모달 내 출판사 조회 - 전체", description="도서 등록시 모낼 내에서 출판사조회. 화면단에서 검색어 필터링")
    @GetMapping("/publisher/all")
    public ResponseEntity<?> publisherAll() {
        try {
            List<PublisherVO> publishers = bookService.selectPublisherAll();
            return ResponseEntity.ok(publishers);
        } catch (Exception e) {
            throw new CustomBadRequestException("출판사 목록 전체 조회 실패");
        }
    }


    @Operation(summary="도서 등록", description="관리자페이지 도서 등록")
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

    @Operation(summary="도서 조회", description="관리자페이지 도서조회 - 등록된 모든 도서 들고오기")
    @GetMapping("/list")
    public ResponseEntity<?> selectBookList() {
        try {
            List<BookVO> bookList = bookService.selectBookList();

            return ResponseEntity.ok(bookList);
        } catch (Exception e) {
            throw new CustomBadRequestException("도서 목록 불러오기 실패");
        }
    }
    @Operation(summary="등록한 도서 상세", description="등록된 도서의 id 값을 사용하여 도서 가져오기")
    @GetMapping("/edit/{id}")
    public ResponseEntity<BookVO> getBookById(@PathVariable("id") int bookId) {
        BookVO book = bookService.selectBookByBookId(bookId);
        return ResponseEntity.ok(book);
    }

    @Operation(summary="등록한 도서 수정", description="등록된 도서의 id 값을 사용하여 도서 수정")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateBook(@ModelAttribute BookVO vo,
                                             @RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
        try {
            bookService.updateBook(vo,imgFile);
            return ResponseEntity.ok("도서 수정 성공");
        } catch (Exception e) {
            throw new CustomBadRequestException("도서 수정 실패");
        }
    }

    @Operation(summary="단일 도서 삭제", description="행에 있는 삭제 아이콘을 통한 단일 도서 삭제 ")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteBookOne(@RequestBody BookVO vo) {
        try {
            bookService.deleteBookOne(vo);
            return ResponseEntity.ok("도서 삭제 성공");
        } catch (Exception e) {
            throw new CustomBadRequestException("도서 삭제 실패");
        }
    }

    @Operation(summary="다건 도서 삭제", description="'삭제'버튼을 통한 체크리스트에 포함된 도서 삭제 ")
    @PostMapping("/delete-list")
    public ResponseEntity<String> deleteBookList(@RequestBody List<BookVO> bookList) {
        try {
            bookService.deleteBookList(bookList);
            return ResponseEntity.ok("도서 리스트 삭제 성공");
        } catch (Exception e) {
            throw new CustomBadRequestException("도서 리스트 삭제 실패");
        }
    }

}
