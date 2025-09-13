package com.scm.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.service.ImageService;
import com.scm.util.AppConstants;

@Service
public class ImageServiceImpl implements ImageService {

    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }
    @Override
    public String uploadImage(MultipartFile contactImage) {
         
        String filename = UUID.randomUUID().toString();
        System.out.println("filename is \n\n\n"+filename+"\n\n\n");
        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id", filename
            ));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.getUrlFromPublicId(filename);
    }
    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary.
        url()
        .transformation(
            new Transformation<>()
            .width(AppConstants.CONTACT_IMAGE_WIDTH)
            .height(AppConstants.CONTACT_IMAGE_HEIGHT)
            .crop(AppConstants.CONTACT_IMAGE_CROP)
        )
        .generate(publicId);
    }

}
