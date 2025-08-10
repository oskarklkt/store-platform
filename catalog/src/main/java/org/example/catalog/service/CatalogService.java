package org.example.catalog.service;

import lombok.RequiredArgsConstructor;
import org.example.catalog.dto.ProductDto;

import org.example.catalog.repository.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public List<ProductDto> getBySku(String sku) {
        return catalogRepository.getProducts().stream()
                .filter(product -> product.getSku().equals(sku))
                .toList();
    }

    public Optional<ProductDto> getByUniqueId(String id) {
        return catalogRepository.getProducts().stream()
                .filter(product -> product.getUniqId().equals(id))
                .findFirst();
    }
}
