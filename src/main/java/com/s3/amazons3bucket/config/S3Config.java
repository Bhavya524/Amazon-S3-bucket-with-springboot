package com.s3.amazons3bucket.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.s3.amazons3bucket.service.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "com.alyasra", name = "fs", havingValue = "AWS")
public class S3Config {

    @Value("${com.alyasra.aws.access}")
    private String awsAccessKey;

    @Value("${com.alyasra.aws.secret}")
    private String awsSecretKey;

    @Value("${com.alyasra.aws.region}")
    private String awsAccountRegion;
    @Bean
    public S3Service s3Service(){
        return new S3Service(s3client());
    }

    public AmazonS3Client s3client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(awsAccountRegion)
                .build();
    }
}
