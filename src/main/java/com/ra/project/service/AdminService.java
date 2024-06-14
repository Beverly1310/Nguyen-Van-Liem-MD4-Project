package com.ra.project.service;

import com.ra.project.model.entity.Role;
import com.ra.project.model.entity.User;

import java.util.List;

public interface AdminService {
    List<User> getUserWithPagingAndSorting(Integer page,Integer size,String orderBy,String direction);
    User changStatus(Long id);
    List<Role> getAllRoles();
    List<User> getUserByFullName(String fullName);
}
