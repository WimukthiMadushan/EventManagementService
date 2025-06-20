package com.eventmanagemnt.eventmanagementservice.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        System.out.println(uploadResult.get("secure_url").toString());
        return uploadResult.get("secure_url").toString();
    }

    public String uploadThumbnail(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "quality", "auto:low",
                "width", 200,
                "height", 200,
                "crop", "scale"
        ));
        System.out.println(uploadResult.get("secure_url").toString());
        return uploadResult.get("secure_url").toString();
    }
}
