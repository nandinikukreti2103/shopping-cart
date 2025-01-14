package com.shopping_cart.service;

import com.shopping_cart.dto.ImageDto;
import com.shopping_cart.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);

    Image getImageById(Long imageId);

    Image update(MultipartFile file, Long imageId);

    void delete(Long imageId);

    ResponseEntity<Resource> downloadImage(Long imageId) throws Exception;
}
