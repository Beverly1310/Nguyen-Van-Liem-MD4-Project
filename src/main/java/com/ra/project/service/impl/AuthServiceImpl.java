package com.ra.project.service.impl;

import com.ra.project.model.cons.RoleName;
import com.ra.project.model.dto.request.FormSignUp;
import com.ra.project.model.entity.Role;
import com.ra.project.model.entity.User;
import com.ra.project.repository.RoleRepository;
import com.ra.project.repository.UserRepository;
import com.ra.project.security.jwt.JWTProvider;
import com.ra.project.service.AuthService;
import com.ra.project.util.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private FileUploadService fileUploadService;
    public User signup(FormSignUp formSignUp) {
        User user = User.builder()
                .email(formSignUp.getEmail())
                .phone(formSignUp.getPhone())
                .address(formSignUp.getAddress())
                .password(formSignUp.getPassword())
                .fullName(formSignUp.getFullName())
                .username(formSignUp.getUsername())
                .avatar(formSignUp.getAvatar())
                .createdAt(formSignUp.getCreatedAt())
                .updatedAt(formSignUp.getUpdatedAt())
                .isDeleted(formSignUp.getIsDeleted())
                .build();
//        if (file!=null && !file.isEmpty()) {
//            user.setAvatar(file.getOriginalFilename());
//        }
        Set<Role> roles = new HashSet<>();
        if (formSignUp.getRoles() != null && !formSignUp.getRoles().isEmpty()) {
            formSignUp.getRoles().forEach(role -> {
                if (role.getRoleName().toString().equals("ADMIN")) {
                    roles.add(roleRepository.findRoleByRoleName(role.getRoleName()).orElseThrow(() -> new NoSuchElementException("Role admin khong ton tai")));
                } else if (role.getRoleName().toString().equals("USER")) {
                    roles.add(roleRepository.findRoleByRoleName(role.getRoleName()).orElseThrow(() -> new NoSuchElementException("Role user khong ton tai")));
                }
            });
        } else {
            roles.add(roleRepository.findRoleByRoleName(RoleName.USER).orElseThrow(() -> new NoSuchElementException("Role user khong ton tai")));
        }
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }
}

