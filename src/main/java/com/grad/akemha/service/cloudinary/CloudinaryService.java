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

    public String uploadFile(MultipartFile file, String folderName) {
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            System.out.println("public Id is " + publicId);
            return cloudinary.url().secure(true).generate(publicId);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public String destroyFile(String folderName, String publicId) {
        try{
//            HashMap<Object, Object> options = new HashMap<>();
//            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            //        Deleting an image with the public ID of sample:
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }
}
