package com.gustavosdaniel.backend.category;

import com.gustavosdaniel.backend.commun.ActiveOrInactive;
import org.springframework.web.multipart.MultipartFile;

public record CategoryCreatedResponse(

        String id,
        String name,
        MultipartFile imageName,
        ActiveOrInactive isActive
) {
}
