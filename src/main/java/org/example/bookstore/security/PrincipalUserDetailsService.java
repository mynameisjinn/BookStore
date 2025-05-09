package org.example.bookstore.security;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.repository.AccountRepository;
import org.example.bookstore.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository; // MyBatis 또는 JPA에 맞게 바꿔도 됨

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberVO member = accountRepository.findUserByEmail(email); // email로 사용자 조회
        if (member == null) throw new UsernameNotFoundException("존재하지 않는 사용자");
        return new PrincipalUserDetails(member);
    }
}
