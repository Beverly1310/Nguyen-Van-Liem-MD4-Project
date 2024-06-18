package com.ra.project.model.dto.request;

import com.ra.project.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRequest {
    @NotEmpty(message = "Phone is empty")
    @NotBlank(message = "Phone is blank")
    private String phone;
    @NotEmpty(message = "Receive name is empty")
    @NotBlank(message = "Receive name is blank")
    private String receiveName;
    @NotEmpty(message = "Address is empty")
    @NotBlank(message = "Address is blank")
    private String fullAddress;
}

