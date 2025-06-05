package org.example.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.repository.AccountRepository;
import org.example.bookstore.security.JwtUtil;
import org.example.bookstore.security.PrincipalUserDetails;
import org.example.bookstore.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final AccountRepository accountRepository;

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

    public Map<String, Object> login(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        PrincipalUserDetails principal = (PrincipalUserDetails) userDetails;
        MemberVO member = principal.getMember();

        MemberVO loginUser = new MemberVO();
        loginUser.setEmail(member.getEmail());
        loginUser.setAddress(member.getAddress());
        loginUser.setId(member.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        result.put("user", loginUser);
        result.put("role", principal.getRole().getRole());

        return result;
    }
}
