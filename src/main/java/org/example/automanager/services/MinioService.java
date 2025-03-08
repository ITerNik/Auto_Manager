package org.example.automanager.services;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.example.automanager.utils.MinioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class MinioService {
    private final MinioClient minioClient;

    private final MinioConfig minioConfig;

    @Autowired
    public MinioService(MinioConfig minioConfig) {
        this.minioClient = MinioClient.builder()
                .endpoint(minioConfig.getMinioUrl())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
        this.minioConfig = minioConfig;
    }

    public void upload() {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioConfig.getBucketName()).build());
            }

            ByteArrayInputStream fs = new ByteArrayInputStream("test.txt".getBytes());
            minioClient.putObject(PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object("test.txt")
                            .stream(fs, fs.available(), -1)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
