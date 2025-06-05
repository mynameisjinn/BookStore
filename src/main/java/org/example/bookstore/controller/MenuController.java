package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "/Menu", description = "메뉴 관련 API")
@RequestMapping("/menu")
@RestController
public class MenuController {

    private final MenuService menuService;

    @Value("${role.admin}")
    private String admin;

    @Value("${role.user}")
    private String user;

    @Operation(summary="메뉴조회", description="권한별 메뉴조회")
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
