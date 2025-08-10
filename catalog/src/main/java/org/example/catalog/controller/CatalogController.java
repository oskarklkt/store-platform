package org.example.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.example.catalog.dto.ProductDto;
import org.example.catalog.exception.NoSuchElementException;
import org.example.catalog.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {
        ProductDto product = catalogService.getByUniqueId(id)
                .orElseThrow(() -> new NoSuchElementException(id));

        return ResponseEntity.ok(product);
    }

    @GetMapping("/products/{sku}")
    public ResponseEntity<List<ProductDto>> getProductsBySku(@PathVariable String sku) {
        List<ProductDto> products = catalogService.getBySku(sku);
        return ResponseEntity.ok(products);
    }





}
