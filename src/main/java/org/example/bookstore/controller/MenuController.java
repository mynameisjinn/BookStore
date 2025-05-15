package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.service.MenuService;
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
@RequestMapping("/menu")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Value("${role.admin}")
    private String admin;

    @Value("${role.user}")
    private String user;
    @GetMapping("/get")
    public ResponseEntity<List<MenuVO>> selectMenu(@RequestParam String roleName){
        try {
            if(!roleName.equals(admin)) roleName = user;

            List<MenuVO> menus = menuService.selectMenu(roleName);
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
