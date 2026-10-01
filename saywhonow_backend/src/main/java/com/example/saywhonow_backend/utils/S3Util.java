package com.example.saywhonow_backend.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3Util {
    @Value("${aws.s3.bucket-name}")
    private static String bucketName;

    public static void uploadFile(String fileName, InputStream inputStream) 
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        
        S3Client s3Client = S3Client.builder().build();

        PutObjectRequest request = PutObjectRequest.builder() 
                                                    .bucket(bucketName)
                                                    .key(fileName)
                                                    .build();

        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
    }
}
