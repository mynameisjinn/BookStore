package org.example.bookstore.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.bookstore.vo.MemberVO;

import java.util.List;

@Mapper
public interface AccountRepository {
    List<MemberVO> selectMemberAll();

    int selectMemberByEmail(String email);

    MemberVO findUserByEmail(String email);
    void insertMember(MemberVO member);
}
