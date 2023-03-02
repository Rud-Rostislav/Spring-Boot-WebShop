package com.rrs.spring_site.services;

import com.rrs.spring_site.models.Photo;
import com.rrs.spring_site.models.Product;
import com.rrs.spring_site.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(Product product,
                           MultipartFile file1,
                           MultipartFile file2,
                           MultipartFile file3) throws IOException {
        Photo photo1;
        Photo photo2;
        Photo photo3;

        if (file1.getSize() != 0) {
            photo1 = toPhotoEntity(file1);
            photo1.setPreviewImage(true);
            product.addPhoto(photo1);
        }

        if (file2.getSize() != 0) {
            photo2 = toPhotoEntity(file2);
            product.addPhoto(photo2);
        }

        if (file3.getSize() != 0) {
            photo3 = toPhotoEntity(file3);
            product.addPhoto(photo3);
        }

        if(file1.getSize() == 0 && file2.getSize() == 0 && file3.getSize() == 0) {
            productRepository.save(product);
        } else {
            Product productFromDb = productRepository.save(product);
            productFromDb.setPreviewPhotoId(productFromDb.getPhotos().get(0).getId());
            productRepository.save(product);
        }


    }

    private Photo toPhotoEntity(MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setName(file.getName());
        photo.setOriginalFileName(file.getOriginalFilename());
        photo.setContentType(file.getContentType());
        photo.setSize(file.getSize());
        photo.setBytes(file.getBytes());
        return photo;
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

}
