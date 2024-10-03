package com.capstone.dayj.util;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageUploader {
    @Value("${spring.cloud.gcp.storage.bucket}") // GCP 버킷 이름
    private String bucketName;
    @Value("${spring.cloud.gcp.storage.credentials.location}") // 키 파일 위치
    private String keyFileLocation;
    private final ResourceLoader resourceLoader;
    
    public String upload(MultipartFile image) throws IOException {
        log.info("Starting image upload");
        
        String uuid = UUID.randomUUID().toString(); // 랜덤한 파일 이름을 위한 UUID 생성
        String extension = StringUtils.getFilenameExtension(image.getOriginalFilename()); // 확장자
        String fileName = uuid + "." + Objects.requireNonNull(extension)
                .substring(extension.lastIndexOf('/') + 1);
        
        Storage storage;
        
        try {
            log.info("Initializing Storage client");
            storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(resourceLoader
                            .getResource("classpath:gcp/" + keyFileLocation) // key 파일 읽어서 버킷 연결
                            .getInputStream()))
                    .build()
                    .getService();
            log.info("Storage client initialized successfully");
        }
        catch (Exception e) {
            log.error("Failed to initialize Storage client", e);
            throw new IOException("Failed to initialize Storage client", e);
        }
        
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName) // 이미지 빌드
                .setContentType(extension)
                .build();
        
        try (WriteChannel writer = storage.writer(blobInfo)) { // 버킷에 이미지 저장
            ByteBuffer buffer = ByteBuffer.wrap(image.getBytes());
            writer.write(buffer);
            log.info("File uploaded successfully to {}", fileName);
        }
        catch (StorageException se) {
            log.error("Failed to upload file to GCP", se);
            throw new IOException("Failed to upload file to GCP", se);
        }
        catch (Exception e) {
            log.error("Unexpected error during file upload", e);
            throw new IOException("Unexpected error during file upload", e);
        }
        
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName); // 이미지의 공유 URL 반환
    }
}
