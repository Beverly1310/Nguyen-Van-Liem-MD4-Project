package com.ra.project.security.principal;

import com.ra.project.model.entity.Role;
import com.ra.project.model.entity.User;
import com.ra.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NoSuchElementException("Khong ton tai username"));
        CustomUserDetail userDetail = CustomUserDetail.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .email(user.getEmail())
                .phone(user.getPhone())
                .status(user.getStatus())
                .avatar(user.getAvatar())
                .authorities(functionConvertRoleToGrandAuthorities(user.getRoles()))
                .build();

        return userDetail;
    }

    private Collection<? extends GrantedAuthority> functionConvertRoleToGrandAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().toString())).toList();
    }}
