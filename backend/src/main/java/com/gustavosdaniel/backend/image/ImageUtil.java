package com.gustavosdaniel.backend.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Component
public class ImageUtil {

    private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

    @Value("${application.upload.base-directory:uploads}")
    private String baseUploadDirectory;

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    public void validateImage(MultipartFile file) throws ErrorValidateImage {

        if (file == null || file.isEmpty()) {
            throw new ErrorValidateImage("Imagem é obrigatória");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            long maxSizeInMB = MAX_FILE_SIZE / 1024 / 1024;
            throw new ErrorValidateImage("O arquivo é muito grande. O tamanho máximo é de " + maxSizeInMB + "MB");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            throw new ErrorValidateImage("Nome do arquivo inválido");
        }
        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!IMAGE_EXTENSIONS.contains(extension)) {
            throw new ErrorValidateImage("Extensão informada inválida. Use: " + String.join(", ", IMAGE_EXTENSIONS));
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ErrorValidateImage("Arquivo não é uma imagem válida");
        }
    }

    public String generateUniqueFileName(MultipartFile file, String baseName) {
        String extension = getFileExtension(file.getOriginalFilename());
        String sanitizedBaseName = sanitizeFileName(baseName);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomSuffix = String.valueOf((int) (Math.random() * 1000));
        return sanitizedBaseName + "_" + timestamp + "_" + randomSuffix + "." + extension;
    }

    public void createDirectoryIfNotExists(String directory) throws IOException {

        Path uploadPath = Paths.get(directory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            log.info("Directory created: {}", uploadPath);
        }
    }

    public Path getImagePath(String imageName, String subDirectory) throws IOException {

        if (imageName == null || imageName.isBlank() || subDirectory == null || subDirectory.isBlank()) {
            throw new IOException("Nome da imagem ou subdiretório inválido.");
        }

        Path baseDirectory = Paths.get(baseUploadDirectory).toAbsolutePath().normalize();
        Path filePath = baseDirectory.resolve(Paths.get(subDirectory, imageName)).normalize();

        if (!filePath.startsWith(baseDirectory)) {
            throw new IOException("Acesso negado: Tentativa de acessar arquivo fora do diretório permitido.");
        }
        return filePath;
    }

    public String determineContentType(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "image/jpeg";
        };
    }

    private String sanitizeFileName(String fileName) {

        return fileName.replaceAll("[^a-zA-Z0-9\\-_]",
                "_").toLowerCase().replaceAll("_+", "_");
    }

    private String getFileExtension(String filename) {

        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}