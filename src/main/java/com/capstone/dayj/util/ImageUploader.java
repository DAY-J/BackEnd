package com.capstone.dayj.util;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploader {
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileLocation;
    private final ResourceLoader resourceLoader;
    
    public String upload(MultipartFile image) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String extension =  StringUtils.getFilenameExtension(image.getOriginalFilename());
        String fileName = uuid + "." + Objects.requireNonNull(extension)
                .substring(extension.lastIndexOf('/') + 1);
        
        // 서비스 계정 키 파일 사용하여 클라이언트 초기화
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(
                        Files.newInputStream(resourceLoader
                                .getResource("classpath:gcp/" + keyFileLocation)
                                .getFile().toPath())))
                .build()
                .getService();
        
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(extension)
                .build();
        
        try (
                WriteChannel writer = storage.writer(blobInfo)
        ) {
            ByteBuffer buffer = ByteBuffer.wrap(image.getBytes());
            writer.write(buffer);
        }
        catch (StorageException se) {
            throw new IOException("Failed to upload file to GCP", se);
        }
        catch (Exception e) {
            throw new IOException("Unexpected error during file upload", e);
        }
        
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }
}