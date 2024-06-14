package com.ra.project.service;

import com.ra.project.model.dto.request.FormSignIn;
import com.ra.project.model.dto.request.FormSignUp;
import com.ra.project.model.dto.response.JWTResponse;
import com.ra.project.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {

    User signup(FormSignUp formSignUp);
    JWTResponse login(FormSignIn formSignIn);
}
