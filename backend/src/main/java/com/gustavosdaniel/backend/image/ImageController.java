package com.gustavosdaniel.backend.image;


import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/images")
public class ImageController {


    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{subDirectory}/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String subDirectory,
                                             @PathVariable String imageName) throws ExceptionErrorUploadImage {

            ImageResource imageResource = imageService.loadAsResource(subDirectory, imageName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(imageResource.contentType()))
                    .cacheControl(CacheControl.maxAge(Duration.ofDays(30)))
                    .body(imageResource.resource());

    }
}
