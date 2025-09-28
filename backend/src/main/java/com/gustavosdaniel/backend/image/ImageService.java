package com.gustavosdaniel.backend.image;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface ImageService {

    String uploadImage(MultipartFile file, String subDirectory, String fileName) throws ErrorValidateImage, IOException;

    List<String> saveMultipartImage(List<MultipartFile> files, String subDirectory, String baseFileName) throws ErrorValidateImage, IOException;

    void deleteImage(String imageName, String subDirectory);

    ImageResource loadAsResource(String subDirectory, String imageName) throws ExceptionErrorUploadImage;
}