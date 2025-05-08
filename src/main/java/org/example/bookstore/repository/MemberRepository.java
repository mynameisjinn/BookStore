package org.example.bookstore.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.bookstore.vo.MemberVO;

import java.util.List;

@Mapper
public interface MemberRepository {
    List<MemberVO> selectMemberAll();
}
