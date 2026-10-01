package com.example.saywhonow_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import com.example.saywhonow_backend.models.S3EventPayload;
import com.example.saywhonow_backend.service.ImageService;

@RestController
@RequestMapping("/api/upload-image")
public class ImageController {
    
    @Autowired
    private ImageService imageService;

    // @Autowired
    // private S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    // @PostMapping
    // public ResponseEntity<String> handleImageUpload(@RequestBody S3EventPayload payload ){
    //     try {
    //         // Extract necessary information from the payload
    //         String bucket = payload.getBucket();
    //         String key = payload.getKey();

    //         // Call the ImageService to update the database
    //         imageService.updateDatabaseWithImageUrls(bucket, key);
            
    //         return ResponseEntity.ok("Database updated successfully");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating database");
    //     }
    // }

    @PostMapping("/s3")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
        System.out.println("In image controller");
        try {
            // String message = imageService.s3UploadAndSaveUrlToDatabase(file.getOriginalFilename(), file.getBytes());
            String message = imageService.s3UploadAndSaveUrlToDatabase(file);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image to s3 bucket");
        }

    }


}
