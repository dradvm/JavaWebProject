package com.JavaWebProject.JavaWebProject.services;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class CloudStorageService {
    
    public String getProfileImg(String role, String img) {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = null;
        if (role.equals("Customer")) {
            blob = bucket.get("customer/" + img);
        }
        else if (role.equals("Caterer")) {
            blob = bucket.get("caterer/" + img);
        }
        if (blob != null) {
            return blob.signUrl(5, TimeUnit.MINUTES).toString();
        }
        return null;
    }
    
    public String getBannerImg(String img) {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get("banner/" + img);
        if (blob != null) {
            return blob.signUrl(5, TimeUnit.MINUTES).toString();
        }
        return null;
    }
}
