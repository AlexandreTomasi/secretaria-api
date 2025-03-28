package com.secretaria_api.service;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public String uploadFile(Long fotoPessoaId, MultipartFile file) {
        try {
            String fileName = "pessoa-" + fotoPessoaId + "-" + System.currentTimeMillis() +
                    getFileExtension(file.getOriginalFilename());

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar arquivo para MinIO", e);
        }
    }

    public byte[] getFile(String fileName) {
        try {
            GetObjectResponse object = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());

            return object.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao recuperar arquivo do MinIO", e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Falha ao deletar arquivo do MinIO", e);
        }
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    public String gerarLinkDownload(String hash) {
        try {
            if (hash == null) {
                return null;
            }

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(hash)
                            .expiry(5, TimeUnit.MINUTES) // Define expiração para 5 minutos
                            .build()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}