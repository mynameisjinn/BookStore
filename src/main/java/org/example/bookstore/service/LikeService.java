package org.example.bookstore.service;

import org.example.bookstore.repository.LikeRepository;
import org.example.bookstore.vo.BookVO;
import org.example.bookstore.vo.LikesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

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



}
