package com.example.saywhonow_backend.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;
import com.example.saywhonow_backend.models.S3EventPayload;
// import com.example.saywhonow_backend.service.ImageService;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S3EventHandler implements RequestHandler<S3Event, String>{

    private static final Logger logger = LoggerFactory.getLogger(S3EventHandler.class);

    // private final ImageService imageService;
    private final RestTemplate restTemplate;

    // public S3EventHandler(ImageService imageService, RestTemplate restTemplate) {
    public S3EventHandler(RestTemplate restTemplate) {
        // this.imageService = imageService;
        this.restTemplate = restTemplate;
    }
    
    @Override
    public String handleRequest(S3Event s3event, Context context) {

        try {
            S3EventNotificationRecord record = s3event.getRecords().get(0);
            String srcBucket = record.getS3().getBucket().getName();
            String srcKey = record.getS3().getObject().getUrlDecodedKey();

            S3Client s3Client = S3Client.builder().build();
            HeadObjectResponse headObject = getHeadObject(s3Client, srcBucket, srcKey);

            logger.info("Successfully retrieved " + srcBucket + "/" + srcKey + " of type " + headObject.contentType());

            // call the image service to update the database with the image URLs
            // imageService.updateDatabaseWithImageUrls(s3Client);
            // imageService.updateDatabaseWithImageUrls(srcBucket, srcKey);

            // Call the Spring Boot application endpoint
            invokeSpringBootEndpoint(srcBucket, srcKey);

            return "Ok";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

    private HeadObjectResponse getHeadObject(S3Client s3Client, String bucket, String key) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();

        return s3Client.headObject(headObjectRequest);
    }

    private void invokeSpringBootEndpoint(String bucket, String key) {
        // Construct the payload
        S3EventPayload payload = new S3EventPayload(bucket, key);

        // Make HTTP POST request to Spring boot application
        String springBootEndpoint = "http://localhost:8080/api/upload-image";
        restTemplate.postForEntity(springBootEndpoint, payload, String.class);   
    }
}
