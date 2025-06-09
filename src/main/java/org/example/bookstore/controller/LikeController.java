package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.exception.CustomBadRequestException;
import org.example.bookstore.service.LikeService;
import org.example.bookstore.vo.BookVO;
import org.example.bookstore.vo.LikesVO;
import org.example.bookstore.vo.MenuVO;
import org.example.bookstore.vo.UserMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Tag(name = "Like API", description = "사용자 관심 도서 관련 API - 도서 좋아요, 메뉴 즐겨찾기")
@RequestMapping("/like")
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/book")
    public ResponseEntity<?> like(@RequestBody LikesVO vo) {
        try {
            likeService.insertUserLike(vo);
            return ResponseEntity.ok("좋아요 성공");
        } catch (Exception e){
            throw new CustomBadRequestException("좋아요 실패");
        }
    }

    @DeleteMapping("book")
    public ResponseEntity<?> dislike(@RequestBody LikesVO vo) {
        try {
            likeService.deleteUserLike(vo);
            return ResponseEntity.ok("좋아요 성공");
        } catch (Exception e){
            throw new CustomBadRequestException("좋아요 실패");
        }
    }

    @GetMapping("/books")
    public ResponseEntity<?> likeBooks(@RequestParam("memberId")int memberId) {
        try {
            List<BookVO> userLikeBookList = likeService.selectUserLikeList(memberId);
            return ResponseEntity.ok(userLikeBookList);
        } catch (Exception e){
            throw new CustomBadRequestException("좋아요 실패");
        }
    }


    @PostMapping("/menu")
    public ResponseEntity<?> favoriteMenu(@RequestBody UserMenuVO vo) {
        try {
            likeService.insertUserFavoriteMenu(vo);
            return ResponseEntity.ok("즐겨찾기 등록 성공");
        } catch (Exception e){
            throw new CustomBadRequestException("즐겨찾기 등록 실패");
        }
    }

    @DeleteMapping("menu")
    public ResponseEntity<?> CancelFavoriteMenu(@RequestBody UserMenuVO vo) {
        try {
            likeService.deleteUserFavoriteMenu(vo);
            return ResponseEntity.ok("즐겨찾기 삭제 성공");
        } catch (Exception e){
            throw new CustomBadRequestException("즐겨찾기 삭제 실패");
        }
    }

    @GetMapping("/menus")
    public ResponseEntity<?> favoriteMenus(@RequestParam("memberId")int memberId) {
        try {
            List<MenuVO> list = likeService.selectUserFavoriteMenuList(memberId);
            return ResponseEntity.ok(list);
        } catch (Exception e){
            throw new CustomBadRequestException("즐겨찾기 목록 조회 실패");
        }
    }



}
