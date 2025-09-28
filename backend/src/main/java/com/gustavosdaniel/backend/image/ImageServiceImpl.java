package com.gustavosdaniel.backend.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageUtil imageUtil;
    private final String baseUploadDirectory;

    public ImageServiceImpl(ImageUtil imageUtil, @Value("${application.upload.base-directory:uploads}") String baseUploadDirectory) {
        this.imageUtil = imageUtil;
        this.baseUploadDirectory = baseUploadDirectory;
    }

    @Override
    public String uploadImage(MultipartFile file, String subDirectory, String fileName) throws ErrorValidateImage, IOException {
        imageUtil.validateImage(file);

        String fullUploadDirectory = baseUploadDirectory + File.separator + subDirectory;
        imageUtil.createDirectoryIfNotExists(fullUploadDirectory);

        String uniqueFileName = imageUtil.generateUniqueFileName(file, fileName);
        Path filePath = Paths.get(fullUploadDirectory, uniqueFileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        log.info("Upload de imagem bem-sucedido: {}", uniqueFileName);
        return uniqueFileName;
    }

    @Override
    public List<String> saveMultipartImage(List<MultipartFile> files, String subDirectory, String baseFileName) throws ErrorValidateImage, IOException {
        List<String> savedFileNames = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            String fileName = baseFileName + "_" + (i + 1);
            String savedName = uploadImage(files.get(i), subDirectory, fileName);
            savedFileNames.add(savedName);
        }
        return savedFileNames;
    }

    @Override
    public void deleteImage(String imageName, String subDirectory) {
        if (imageName == null || imageName.isBlank() || imageName.startsWith("default-")) {
            return;
        }
        try {
            Path filePath = imageUtil.getImagePath(imageName, subDirectory);
            if (Files.deleteIfExists(filePath)) {
                log.info("Imagem deletada: {}", filePath);
            } else {
                log.debug("Imagem não encontrada para deletar: {}", filePath);
            }
        } catch (IOException e) {
            log.error("Erro de I/O ao deletar imagem: {}/{}", subDirectory, imageName, e);
            throw new RuntimeException("Não foi possível deletar a imagem: " + imageName, e);
        }
    }

    @Override
    public ImageResource loadAsResource(String subDirectory, String imageName) throws ExceptionErrorUploadImage {
        try {
            Path imagePath = imageUtil.getImagePath(imageName, subDirectory);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = imageUtil.determineContentType(imageName);
                return new ImageResource(resource, contentType);
            } else {
                throw new ExceptionErrorUploadImage("Imagem não encontrada ou ilegível: " + imageName);
            }
        } catch (IOException e) {
            throw new ExceptionErrorUploadImage("Ocorreu um erro ao carregar a imagem: " + imageName);
        }
    }
}
