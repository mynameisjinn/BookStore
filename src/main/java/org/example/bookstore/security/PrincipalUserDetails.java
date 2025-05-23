package org.example.bookstore.security;

import lombok.Getter;
import org.example.bookstore.repository.AccountRepository;
import org.example.bookstore.vo.MemberVO;
import org.example.bookstore.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class PrincipalUserDetails implements UserDetails {

    @Getter
    private final MemberVO member;

    @Getter
    private final RoleVO role;

    public PrincipalUserDetails(MemberVO member, RoleVO role) {
        this.member = member;
        this.role = role;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.getRole()));
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}
