package com.shopping_cart.service.impl;

import com.shopping_cart.dto.ImageDto;
import com.shopping_cart.entity.Image;
import com.shopping_cart.entity.Product;
import com.shopping_cart.exception.ImageNotFoundException;
import com.shopping_cart.repository.ImageRepository;
import com.shopping_cart.service.ImageService;
import com.shopping_cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for(MultipartFile file : files){
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));

                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return savedImageDto;
    }

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(()-> new ImageNotFoundException("Image not found with this id: " + imageId));
    }

    @Override
    public Image update(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return image;
    }

    @Override
    public void delete(Long imageId) {
        imageRepository.findById(imageId)
                .ifPresentOrElse(imageRepository::delete, ()-> {
                    throw  new ImageNotFoundException("Image does not exist with this id: " + imageId);
                });

    }

    @Override
    public ResponseEntity<Resource> downloadImage(Long imageId) throws Exception{
        // Fetch the image from the database by its ID
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("Image not found with ID: " + imageId));

        // Get the byte array from the Blob
        byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        ByteArrayResource resource = new ByteArrayResource(imageBytes);

        // Set the response headers
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType())) // Set the Content-Type based on the fileType in the database
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getFileName() + "\"") // Inline or attachment
                .body(resource);
    }
}

