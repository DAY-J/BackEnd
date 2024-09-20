package com.capstone.dayj.util;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageUploader {
    private static final Logger logger = LoggerFactory.getLogger(ImageUploader.class);
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileLocation;
    private final ResourceLoader resourceLoader;
    
    public String upload(MultipartFile image) throws IOException {
        logger.info("Starting image upload");
        
        String uuid = UUID.randomUUID().toString();
        String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());
        String fileName = uuid + "." + Objects.requireNonNull(extension)
                .substring(extension.lastIndexOf('/') + 1);
        
        Storage storage;
        try {
            logger.info("Initializing Storage client");
            storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(resourceLoader
                            .getResource("classpath:gcp/" + keyFileLocation)
                            .getInputStream()))
                    .build()
                    .getService();
            logger.info("Storage client initialized successfully");
        }
        catch (Exception e) {
            logger.error("Failed to initialize Storage client", e);
            throw new IOException("Failed to initialize Storage client", e);
        }
        
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(extension)
                .build();
        
        try (WriteChannel writer = storage.writer(blobInfo)) {
            ByteBuffer buffer = ByteBuffer.wrap(image.getBytes());
            writer.write(buffer);
            logger.info("File uploaded successfully to {}", fileName);
        }
        catch (StorageException se) {
            logger.error("Failed to upload file to GCP", se);
            throw new IOException("Failed to upload file to GCP", se);
        }
        catch (Exception e) {
            logger.error("Unexpected error during file upload", e);
            throw new IOException("Unexpected error during file upload", e);
        }
        
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }
}
