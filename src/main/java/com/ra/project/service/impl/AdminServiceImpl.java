package com.ra.project.service.impl;

import com.ra.project.model.entity.Role;
import com.ra.project.model.entity.User;
import com.ra.project.repository.RoleRepository;
import com.ra.project.repository.UserRepository;
import com.ra.project.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public List<User> getUserWithPagingAndSorting(Integer page, Integer size, String orderBy, String direction) {
        Pageable pageable = null;

        if(orderBy!=null && !orderBy.isEmpty()){
            // co sap xep
            Sort sort = null;
            switch (direction){
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page-1, size, sort);
        }else{
            //khong sap xep
            pageable = PageRequest.of(page-1, size);
        }
        return userRepository.getAll(pageable).getContent();
    }

    @Override
    public User changStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new NoSuchElementException("User not found"));
        if(user!=null){
            user.setStatus(!user.getStatus());
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<User> getUserByFullName(String fullName) {
        return userRepository.findByFullNameContaining(fullName);
    }
}
