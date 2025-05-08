package org.example.bookstore.service;

import org.example.bookstore.repository.MemberRepository;
import org.example.bookstore.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<MemberVO> selectMemberInfo(){

        return memberRepository.selectMemberAll();
    }

    public boolean checkExistMember(String email) {

        boolean exist = (memberRepository.selectMemberByEmail(email) > 0) ? true : false;

        return exist;
    }
}
