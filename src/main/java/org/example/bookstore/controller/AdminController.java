package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.exception.CustomBadRequestException;
import org.example.bookstore.service.AdminService;
import org.example.bookstore.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
@Tag(name = "Admin", description = "관리자 권한 관련 API")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;


    @Operation(summary="카테고리조회", description="관리자페이지 카테고리조회")
    @GetMapping("/category")
    public ResponseEntity<?> selectCategory(){
        try {
            List<CategoryVO> categories = adminService.selectCategory();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new CustomBadRequestException("카테고리 데이터 로드 실패");
        }
    }

    /*@PostMapping
    public ResponseEntity<?> addCategory() {
        try {


        }catch (Exception e) {
            throw new CustomBadRequestException("카테고리 데이터 추가 실패");
        }
    }*/
}
