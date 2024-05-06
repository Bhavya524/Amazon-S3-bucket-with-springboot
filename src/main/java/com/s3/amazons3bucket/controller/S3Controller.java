package com.s3.amazons3bucket.controller;

import com.s3.amazons3bucket.config.GenericFileService;
import com.s3.amazons3bucket.model.S3ObjectResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class S3Controller {
    public final GenericFileService genericFileService;

    public S3Controller(GenericFileService genericFileService) {
        this.genericFileService = genericFileService;
    }

    @GetMapping("/download/{fileName}")
    public InputStreamResource downloadDocument(@PathVariable String fileName) {
        return genericFileService.downloadFile(fileName);
    }

    @PostMapping(value = "/uploadDoc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public S3ObjectResponse fileUpload(@RequestParam String fileName
            , @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        S3ObjectResponse objectResponse = null;
        if (null != fileName) {
            objectResponse = genericFileService.uploadFile(fileName, file);
        } else {
            objectResponse = genericFileService.uploadFile(file);
        }
        return objectResponse;
    }
}
