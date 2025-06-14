package org.example.bookstore.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.bookstore.vo.*;

import java.util.List;

@Mapper
public interface LikeRepository {

//    List<B>
    void insertUserLike(LikesVO vo);
    void deleteUserLike(LikesVO vo);
    List<BookVO> selectUserLikeList(int memberId);


    void insertUserFavoriteMenu(UserMenuVO vo);
    void deleteUserFavoriteMenu(UserMenuVO vo);
    List<MenuVO> selectUserFavoriteMenuList(int memberId);
}
