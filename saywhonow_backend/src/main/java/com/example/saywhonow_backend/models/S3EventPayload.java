package com.example.saywhonow_backend.models;

public class S3EventPayload {
    
    private String bucket;
    private String key;

    public S3EventPayload() {
    }
    
    public S3EventPayload(String bucket, String key) {
        this.bucket = bucket;
        this.key = key;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    
}
