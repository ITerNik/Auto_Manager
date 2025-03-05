package org.example.automanager.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MinioConfig {
    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.access}")
    private String accessKey;

    @Value("${minio.secret}")
    private String secretKey;

    @Value("${minio.bucket_name}")
    private String bucketName;
}
