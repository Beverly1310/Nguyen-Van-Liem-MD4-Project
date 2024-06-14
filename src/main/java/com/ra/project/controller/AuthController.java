package com.ra.project.controller;

import com.ra.project.model.dto.request.FormSignIn;
import com.ra.project.model.dto.request.FormSignUp;
import com.ra.project.model.dto.response.JWTResponse;
import com.ra.project.model.dto.response.ResponseData;
import com.ra.project.model.entity.User;
import com.ra.project.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseData<User>> signUp(@RequestBody FormSignUp formSignUp) {
        User user = authService.signup(formSignUp);
        return new ResponseEntity<>(new ResponseData<>("success",user, HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseData<JWTResponse>> signIn(@RequestBody FormSignIn formSignIn) {
        JWTResponse jwtResponse = authService.login(formSignIn);
         return new ResponseEntity<>(new ResponseData<>("success",jwtResponse, HttpStatus.OK),HttpStatus.OK);
    }
}
