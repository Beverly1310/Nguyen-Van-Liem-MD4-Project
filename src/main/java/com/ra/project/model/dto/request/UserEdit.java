package com.ra.project.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEdit {
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
    private String avatar;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt=new Date();

}
