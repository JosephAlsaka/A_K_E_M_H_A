package com.grad.akemha.service.cloudinary;

import com.grad.akemha.dto.ImageModel;
import com.grad.akemha.entity.Image;
import com.grad.akemha.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageService {
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ImageRepository imageRepository;


    public ResponseEntity<Map> uploadImage(ImageModel imageModel) {
        try {
//            if (imageModel.getName().isEmpty()) {
//                return ResponseEntity.badRequest().build();
//            }
            if (imageModel.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Image image = new Image();
//            image.setName(imageModel.getName());
            image.setImageUrl(cloudinaryService.uploadFile(imageModel.getFile(), "folder_1", "5"));
            if (image.getImageUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            imageRepository.save(image);
            return ResponseEntity.ok().body(Map.of("url", image.getImageUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<Map> destroyImage(String folderName,String publicId) {
        try {
            cloudinaryService.destroyFile("folder_1", "folder_1/aae6ykjms2hgffbkl2gz");
            return ResponseEntity.ok().body(Map.of("process", "destroy"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
