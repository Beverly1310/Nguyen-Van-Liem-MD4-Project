package com.ra.project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@PasswordDoesNotMatch
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
