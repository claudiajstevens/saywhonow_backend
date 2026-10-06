package com.example.saywhonow_backend.service;

import com.example.saywhonow_backend.domain.Lineup;
import com.example.saywhonow_backend.models.FestivalLineupPosterInfo;
import com.example.saywhonow_backend.repository.FestivalRepository;
import com.example.saywhonow_backend.repository.LineupRepository;
import com.example.saywhonow_backend.utils.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
// import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
// import software.amazon.awssdk.services.s3.model.S3Object;
// import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
// import java.util.List;

@Service
public class ImageService {

    final int NAME = 0;
    final int YEAR = 1;
    
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    Region region = Region.US_EAST_1;

    // private final S3Client s3Client;

    // public ImageService(S3Client s3Cleint){
    //     this.s3Client = s3Client;
    // }

    @Autowired
    LineupRepository lineupRepository;

    @Autowired 
    FestivalRepository festivalRepository;


    // public void updateDatabaseWithImageUrls(S3Client s3Client) {
    // public void updateDatabaseWithImageUrls(String bucket, String key) {
    //     S3Client s3Client = S3Client.builder()
    //         .credentialsProvider(DefaultCredentialsProvider.create())
    //         .build();

    //     ListObjectsV2Response listResponse = s3Client.listObjectsV2(builder -> builder.bucket(bucketName));

    //     List<S3Object> objects = listResponse.contents();

    //     for (S3Object object : objects) {
    //         String imageUrl = getObjectUrl(s3Client, object.key());
    //         String imageName = object.key();

    //         FestivalLineupPosterInfo lineupPosterInfo = parseImageName(imageName);

    //         // get the lineup the lineup poster goes to
    //         Integer festivalId = festivalRepository.findIdByNameIgnoreCase(lineupPosterInfo.getFestivalName());
    //         Lineup lineup = lineupRepository.findByFestivalIdAndYear(festivalId, lineupPosterInfo.getYear());

    //         lineup.setLineupPoster(imageUrl);
    //         lineupRepository.save(lineup);

    //     }
    // }

    // public String s3UploadAndSaveUrlToDatabase(String s3ObjectKey, byte[] data){

    //     System.out.println("In service controller");
    //     System.out.println("File name: " + s3ObjectKey);

    //     S3Client s3Client = getClient();
    //     s3Client.listBuckets().toString();

    //     System.out.println(s3Client.listBuckets().toString());
        
    //     try {

    //         PutObjectRequest objectRequest = PutObjectRequest.builder()
    //             .bucket(bucketName)
    //             .key(s3ObjectKey)
    //             .build();

    //         System.out.println("Made put object request");

    //         s3Client.putObject(objectRequest, RequestBody.fromBytes(data));

    //         System.out.println("s3client put object");

    //         String s3ImageUrl = "https://" + bucketName + ".s3.amazonaws.com/" + s3ObjectKey;

    //         System.out.println(s3ImageUrl);

    //         FestivalLineupPosterInfo lineupPosterInfo = parseImageName(s3ObjectKey);

    //         // add url to database
    //         Integer festivalId = festivalRepository.findIdByNameIgnoreCase(lineupPosterInfo.getFestivalName());
    //         Lineup lineup = lineupRepository.findByFestivalIdAndYear(festivalId, lineupPosterInfo.getYear());

    //         lineup.setLineupPoster(s3ImageUrl);
    //         lineupRepository.save(lineup);

    //         return "Image URL %s uploaded successfully" + s3ImageUrl + "uploaded successfully";
            
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error uploading image to S3 bucket", e);
    //     }

    // }


    public String s3UploadAndSaveUrlToDatabase(MultipartFile file){

        String message = "";

        try {
            S3Util.uploadFile(bucketName, file.getInputStream());
            message = "Your file has been uploaded successfully!";
        } catch (Exception e) {
            message = "Error uploading file: " + e.getMessage();
        }
        
        System.out.println(message);

        String uploadToDatabaseMessage = addUrlToDatabase(file.getOriginalFilename());
        System.out.println(uploadToDatabaseMessage);
        return message;
    }

    
    // private String getObjectUrl(S3Client s3Client, String key){
    //     return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toString();
    // }

    private FestivalLineupPosterInfo parseImageName(String imageName){
        String[] info = imageName.split("_");

        if(info.length == 2){
            String festivalName = info[NAME].replace("-", " ");
            Integer festivalYear = Integer.parseInt(info[YEAR]);
            System.out.println("Festival Name: " + festivalName );
            System.out.println("Festival year: " + festivalYear);
            return new FestivalLineupPosterInfo(festivalName, festivalYear);
        } else {
            // handle invalid image name
            throw new IllegalArgumentException("Invalid image name format: " + imageName);
        }
    }

    private S3Client getClient() {
        return S3Client.builder()
            .region(region)
            .build();
    }

    public String addUrlToDatabase(String fileName){
        String url = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;

        FestivalLineupPosterInfo lineupPosterInfo = parseImageName(fileName);

        // add url to database
        Integer festivalId = festivalRepository.findIdByNameIgnoreCase(lineupPosterInfo.getFestivalName());
        Lineup lineup = lineupRepository.findByFestivalIdAndYear(festivalId, lineupPosterInfo.getYear());

        lineup.setLineupPoster(url);
        lineupRepository.save(lineup);

        return "Image URL %s uploaded successfully" + url + "uploaded successfully";
    }
}
