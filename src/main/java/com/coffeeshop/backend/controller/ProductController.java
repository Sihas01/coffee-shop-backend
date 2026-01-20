package com.coffeeshop.backend.controller;

import com.coffeeshop.backend.model.Product;
import com.coffeeshop.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Adjust this based on your security needs
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Product> createProduct(
            @ModelAttribute Product product,
            @RequestParam("image") org.springframework.web.multipart.MultipartFile image) {
        Product createdProduct = productService.createProduct(product, image);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
}
