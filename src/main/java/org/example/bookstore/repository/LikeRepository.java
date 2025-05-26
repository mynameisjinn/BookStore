package org.example.bookstore.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.bookstore.vo.BookVO;
import org.example.bookstore.vo.CategoryVO;
import org.example.bookstore.vo.LikesVO;

import java.util.List;

@Mapper
public interface LikeRepository {

//    List<B>
    void insertUserLike(LikesVO vo);

    void deleteUserLike(LikesVO vo);

    List<LikesVO> selectUserLikeList(int memberId);

}
