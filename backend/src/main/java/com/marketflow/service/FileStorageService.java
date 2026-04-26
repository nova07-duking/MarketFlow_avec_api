package com.marketflow.service;

import com.marketflow.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileStorageService {

    private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);
    private final Path uploadPath;

    public FileStorageService(@Value("${app.upload.dir}") String uploadDir) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le répertoire d'upload : " + uploadDir, e);
        }
    }

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("Le fichier est vide");
        }

        // Vérifier le type de fichier
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BadRequestException("Seules les images sont autorisées");
        }

        // Générer un nom unique
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID().toString() + extension;

        try {
            Path targetLocation = this.uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Fichier stocké : {}", fileName);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Impossible de stocker le fichier : " + fileName, e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = this.uploadPath.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
            log.info("Fichier supprimé : {}", fileName);
        } catch (IOException e) {
            log.error("Erreur lors de la suppression du fichier : {}", fileName, e);
        }
    }

    public String getFileUrl(String fileName) {
        return "/uploads/products/" + fileName;
    }
}
