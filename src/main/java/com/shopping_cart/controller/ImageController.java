package com.shopping_cart.controller;

import com.shopping_cart.dto.ImageDto;
import com.shopping_cart.entity.Image;
import com.shopping_cart.response.ApiResponse;
import com.shopping_cart.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ApiResponse saveImages(@RequestParam("file") List<MultipartFile> files, @RequestParam Long productId){
            List<ImageDto> imageDto = imageService.saveImages(files,productId);
            return new ApiResponse(true,"upload success!",imageDto);
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws Exception {
        return imageService.downloadImage(imageId);
    }

    @PutMapping("/{imageId}/update")
    public ApiResponse updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
            Image image = imageService.getImageById(imageId);
            if(image != null){
                imageService.update(file, imageId);
                return new ApiResponse(true,"update success!", null);
        }
    return new ApiResponse(false,"update failed!",INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{imageId}/delete")
    public ApiResponse deleteImage(@PathVariable Long imageId){
            Image image = imageService.getImageById(imageId);
            if(image != null){
                imageService.delete(imageId);
                return new ApiResponse(true,"delete success!", null);
            }
            return new ApiResponse(false,"delete failed!",INTERNAL_SERVER_ERROR);
    }
}
