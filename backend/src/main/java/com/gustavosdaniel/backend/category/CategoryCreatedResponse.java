package com.gustavosdaniel.backend.category;

import com.gustavosdaniel.backend.commun.ActiveOrInactive;

public record CategoryCreatedResponse(

        String id,
        String name,
        String imageName,
        ActiveOrInactive isActive
) {
}
