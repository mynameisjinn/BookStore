package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.service.AdminService;
import org.example.bookstore.service.MenuService;
import org.example.bookstore.vo.CategoryVO;
import org.example.bookstore.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @GetMapping("/category")
    public ResponseEntity<List<CategoryVO>> selectCategory(){
        try {

            List<CategoryVO> categories = adminService.selectCategory();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
