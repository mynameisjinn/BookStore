package org.example.bookstore.service;

import org.example.bookstore.repository.MenuRepository;
import org.example.bookstore.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<MenuVO> selectMenu(String role){
        return menuRepository.selectMenu(role);
    }
}
