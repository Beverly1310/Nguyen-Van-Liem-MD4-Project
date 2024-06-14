package com.ra.project.controller;

import com.ra.project.model.dto.response.ResponseData;
import com.ra.project.model.entity.Role;
import com.ra.project.model.entity.User;
import com.ra.project.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/user")
    public ResponseEntity<ResponseData<List<User>>> getAllUsers(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("orderBy") String orderBy,
                                                                @RequestParam("direction") String direction) {
        List<User> users = adminService.getUserWithPagingAndSorting(page, size, orderBy, direction);
        return new ResponseEntity<>(new ResponseData<>("success",users, HttpStatus.OK), HttpStatus.OK);
    }
    @PostMapping("/users/{userId}")
    public ResponseEntity<ResponseData<User>> changeStatus(@PathVariable("userId") Long userId) {
        User user = adminService.changStatus(userId);
        return new ResponseEntity<>(new ResponseData<>("success",user, HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<ResponseData<List<Role>>> getAllRoles(){
        List<Role> roles = adminService.getAllRoles();
        return new ResponseEntity<>(new ResponseData<>("success",roles, HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<ResponseData<List<User>>> getUserByFullName(@RequestParam("fullName") String fullName) {
        List<User> users = adminService.getUserByFullName(fullName);
        return new ResponseEntity<>(new ResponseData<>("success",users, HttpStatus.OK), HttpStatus.OK);
    }
}
