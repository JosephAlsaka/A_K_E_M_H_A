package com.grad.akemha.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService { //for converting the image into URL
    //In CloudinaryService, I make the dynamic type for the folder, so I can put the image into folder that I want.
    @Resource
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, String folderName, String userID) {
        try {
            String imageName = System.currentTimeMillis() + "-" + userID;
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);

            //to change the name
            options.put("public_id", imageName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            System.out.println("public Id is " + publicId);
            return cloudinary.url().secure(true).generate(publicId);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String destroyFile(String publicId) {
        try{
            //        Deleting an image with the public ID of sample:
            Map uploadedFile = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }
}
