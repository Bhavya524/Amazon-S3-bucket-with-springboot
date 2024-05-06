package com.s3.amazons3bucket.config;

import com.s3.amazons3bucket.model.S3ObjectResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GenericFileService {
    S3ObjectResponse uploadFile(String keyName, MultipartFile file) throws IOException;

    S3ObjectResponse uploadFile(MultipartFile file) throws IOException;

    InputStreamResource downloadFile(String fileName);
}
