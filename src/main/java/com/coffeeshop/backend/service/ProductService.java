package com.coffeeshop.backend.service;

import com.coffeeshop.backend.model.Product;
import com.coffeeshop.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product, org.springframework.web.multipart.MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.storeFile(image);
            product.setImagePath("/uploads/" + fileName);
        }
        return productRepository.save(product);
    }
}
