package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.service.MenuService;
import org.example.bookstore.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/menu")
@RestController
public class MenuController {

    @Autowired
    MenuService menuService;
    @GetMapping("/get")
    public ResponseEntity<List<MenuVO>> selectMenu() {
        try {
            List<MenuVO> menus = menuService.selectMenu();
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
