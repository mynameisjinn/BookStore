package org.example.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.repository.LikeRepository;
import org.example.bookstore.vo.BookVO;
import org.example.bookstore.vo.LikesVO;
import org.example.bookstore.vo.MenuVO;
import org.example.bookstore.vo.UserMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//@Transactional

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public void insertUserLike(LikesVO vo) {
        // 중복 검사 후 insert 하기 !
        likeRepository.insertUserLike(vo);
    }

    public void deleteUserLike(LikesVO vo) {
        // 중복 검사 후 insert 하기 !
        likeRepository.deleteUserLike(vo);
    }

    public List<BookVO> selectUserLikeList(int memberId) {
        return likeRepository.selectUserLikeList(memberId);
    }



    public void insertUserFavoriteMenu(UserMenuVO vo) {
        likeRepository.insertUserFavoriteMenu(vo);
    }


    public void deleteUserFavoriteMenu(UserMenuVO vo) {
        likeRepository.deleteUserFavoriteMenu(vo);
    }

    public List<UserMenuVO> selectUserFavoriteMenuList(int memberId) {
        return likeRepository.selectUserFavoriteMenuList(memberId);
    }


}
