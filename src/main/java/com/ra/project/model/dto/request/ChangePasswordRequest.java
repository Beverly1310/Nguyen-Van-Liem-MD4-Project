package com.ra.project.model.dto.request;

import com.ra.project.validator.ConfirmPasswordMatching;
import com.ra.project.validator.OldPasswordMatching;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ConfirmPasswordMatching(password = "newPassword",confirmPassword = "confirmPassword")
public class ChangePasswordRequest {
    @OldPasswordMatching
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
