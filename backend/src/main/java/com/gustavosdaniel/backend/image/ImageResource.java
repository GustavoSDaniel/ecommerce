package com.gustavosdaniel.backend.image;
import org.springframework.core.io.Resource;

public record ImageResource(
        Resource resource,
        String contentType
) {}