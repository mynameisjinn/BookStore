package org.example.bookstore.service;

import org.example.bookstore.repository.AdminRepository;
import org.example.bookstore.repository.MenuRepository;
import org.example.bookstore.vo.CategoryVO;
import org.example.bookstore.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<CategoryVO> selectCategory(){
        return adminRepository.selectCategory();
    }
}
