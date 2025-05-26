package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.exception.CustomBadRequestException;
import org.example.bookstore.service.LikeService;
import org.example.bookstore.vo.LikesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Tag(name = "Like API", description = "사용자 좋아요 API")
@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody LikesVO vo) {
        try {
            likeService.insertUserLike(vo);
            return ResponseEntity.ok("좋아요 성공");
        } catch (Exception e){
            throw new CustomBadRequestException("좋아요 실패");
        }
    }

    @DeleteMapping("like")
    public ResponseEntity<?> dislike(@RequestBody LikesVO vo) {
        try {
            likeService.deleteUserLike(vo);
            return ResponseEntity.ok("좋아요 성공");
        } catch (Exception e){
            throw new CustomBadRequestException("좋아요 실패");
        }
    }

    @GetMapping("/get/like")
    public ResponseEntity<?> like(@RequestParam("memberId")int memberId) {
        try {
            List<LikesVO> userLikeBookList = likeService.selectUserLikeList(memberId);
            return ResponseEntity.ok(userLikeBookList);
        } catch (Exception e){
            throw new CustomBadRequestException("좋아요 실패");
        }
    }



}
