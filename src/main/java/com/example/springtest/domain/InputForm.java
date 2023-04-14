package com.example.springtest.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputForm {
    private long boardNumber;
    private String title;
    private String content;
    private MultipartFile multipartFile;
}
