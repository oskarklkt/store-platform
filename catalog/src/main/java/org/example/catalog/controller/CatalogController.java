package org.example.catalog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.catalog.dto.ProductDto;
import org.example.catalog.exception.NoSuchElementException;
import org.example.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/v1/catalog")
@RequiredArgsConstructor
@Slf4j
public class CatalogController {

    @Value("${demo.latencyMs:0}")
    private long latencyMs;

    @Value("${demo.failPercent:0}")
    private int failPercent;

    private final Random random = new Random();

    private final CatalogService catalogService;

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) throws InterruptedException {

        simulateSlownessAndFailure();

        ProductDto product = catalogService.getByUniqueId(id)
                .orElseThrow(() -> new NoSuchElementException(id));

        return ResponseEntity.ok(product);
    }

    @GetMapping("/products/{sku}")
    public ResponseEntity<List<ProductDto>> getProductsBySku(@PathVariable String sku) throws InterruptedException {

        log.warn("HIT");
        simulateSlownessAndFailure();

        List<ProductDto> products = catalogService.getBySku(sku);
        return ResponseEntity.ok(products);
    }

    private void simulateSlownessAndFailure() throws InterruptedException {
        if (latencyMs > 0) {
            Thread.sleep(latencyMs);
        }
        if (failPercent > 0 && random.nextInt(100) < failPercent) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "simulated inventory failure");
        }
    }
}
