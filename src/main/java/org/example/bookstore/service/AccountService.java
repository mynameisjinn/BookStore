package org.example.bookstore.service;

import org.example.bookstore.repository.AccountRepository;
import org.example.bookstore.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<MemberVO> selectMemberInfo(){

        return accountRepository.selectMemberAll();
    }

    public boolean checkExistMember(String email) {

        boolean exist = (accountRepository.selectMemberByEmail(email) > 0) ? true : false;

        return exist;
    }

    public MemberVO findByEmail(String email) {

        return accountRepository.findUserByEmail(email);
    }

    public void saveMember(MemberVO vo) {
        accountRepository.insertMember(vo);
    }
}
