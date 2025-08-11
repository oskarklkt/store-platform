package org.example.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.product.dto.AvailableProductDto;
import org.example.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product/{uniqueId}")
    public ResponseEntity<AvailableProductDto> getAvailableByUniqueId(@PathVariable String uniqueId) {
        return ResponseEntity.ok(productService.getAvailableProductByUniqueId(uniqueId));
    }

    @GetMapping("/products/{sku}")
    public ResponseEntity<List<AvailableProductDto>> getAvailableBySku(@PathVariable String sku) {
        return ResponseEntity.ok(productService.getAvailableProductsBySku(sku));
    }
}
