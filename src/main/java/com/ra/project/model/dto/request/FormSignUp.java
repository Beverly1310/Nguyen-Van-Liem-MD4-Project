package com.ra.project.model.dto.request;

import com.ra.project.model.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormSignUp {

    @Column(name = "username",length = 100)
    @Pattern(regexp = "^(?!.*[^\\w]).{6,100}$", message = "Username not valid")
    private String username;
    private String password;
    @Column(name = "fullname",length = 100)
    @NotBlank
    @NotEmpty
    private String fullName;
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$", message = "Email is not valid")
    private String email;
    @Pattern(regexp = "^(03|05|07|08|09)\\d{8}$", message = "Phone is not Valid")
    private String phone;
    private String avatar="https://bestnycacupuncturist.com/wp-content/uploads/2016/11/anonymous-avatar-sm.jpg";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt=new Date();
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt=new Date();
    @Column(name = "is_delete")
    private Boolean isDeleted=false;
    private String address;
    private Set<Role> roles;
}
