package org.example.product.clients;

import org.example.product.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "catalog", path = "/v1/catalog")
public interface CatalogClient {

    @GetMapping("/product/{id}")
    ProductDto getProductById(@PathVariable("id") String id);

    @GetMapping("/products/{sku}")
    List<ProductDto> getProductsBySku(@PathVariable("sku") String sku);
}
