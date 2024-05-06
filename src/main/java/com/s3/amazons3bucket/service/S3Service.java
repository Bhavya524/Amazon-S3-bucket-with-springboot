package com.s3.amazons3bucket.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.s3.amazons3bucket.config.GenericFileService;
import com.s3.amazons3bucket.model.S3ObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class S3Service implements GenericFileService {

    private final AmazonS3Client s3client;

    @Value("${com.alyasra.aws.bucket}")
    private String bucketName;

    public S3Service(AmazonS3Client s3client) {
        this.s3client = s3client;
    }

    @Override
    public S3ObjectResponse uploadFile(String objectName, MultipartFile file) throws IOException {
        s3client.putObject(bucketName, objectName, file.getInputStream(), new ObjectMetadata());
        return new S3ObjectResponse(objectName
                , s3client.getResourceUrl(bucketName, objectName));
    }

    @Override
    public S3ObjectResponse uploadFile(MultipartFile file) throws IOException {
        String extension = getFileExtension(file);
        String objectName = UUID.randomUUID() + "." + extension;
        s3client.putObject(bucketName, objectName, file.getInputStream(), new ObjectMetadata());
        return new S3ObjectResponse(objectName
                , s3client.getResourceUrl(bucketName, objectName));
    }

    private static String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            int lastDotIndex = originalFilename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                return originalFilename.substring(lastDotIndex + 1);
            }
        }
        return null;
    }


    @Override
    public InputStreamResource downloadFile(String objectName) {
        s3client.getObject(new GetObjectRequest(bucketName, objectName));
        boolean isExist = s3client.doesObjectExist(bucketName, objectName);
        S3ObjectInputStream s3ObjectInputStream = null;
        if (isExist) {
            S3Object s3Object = s3client.getObject(bucketName, objectName);
            var objectContent = s3Object.getObjectContent();
            s3ObjectInputStream = new S3ObjectInputStream(objectContent, null);
        }
        return new InputStreamResource(Objects.requireNonNull(s3ObjectInputStream));
    }
}
