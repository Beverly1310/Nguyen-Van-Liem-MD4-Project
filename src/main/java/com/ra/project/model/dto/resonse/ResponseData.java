package com.ra.project.model.dto.resonse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseData<T> {
    private String message;
    private T content;
    private HttpStatus httpStatus;
}
