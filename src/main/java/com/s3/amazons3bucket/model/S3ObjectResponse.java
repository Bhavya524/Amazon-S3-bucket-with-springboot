package com.s3.amazons3bucket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class S3ObjectResponse {
    private String objectName;
    private String objectPath;

    public S3ObjectResponse(String objectName, String objectPath){
        this.objectName = objectName;
        this.objectPath = objectPath;
    }
}
