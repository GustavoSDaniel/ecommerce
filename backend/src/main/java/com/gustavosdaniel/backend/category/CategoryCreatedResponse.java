package com.gustavosdaniel.backend.category;

import org.springframework.web.multipart.MultipartFile;

public record CategoryCreatedResponse(

        Integer id,
        String name,
        MultipartFile imageName,
        Boolean isActive
) {
}
