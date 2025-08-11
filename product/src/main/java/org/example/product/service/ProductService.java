package org.example.product.service;

import lombok.RequiredArgsConstructor;
import org.example.product.clients.CatalogClient;
import org.example.product.clients.InventoryClient;
import org.example.product.dto.AvailabilityDto;
import org.example.product.dto.AvailableProductDto;
import org.example.product.dto.ProductDto;
import org.example.product.exception.ProductNotAvailableException;
import org.example.product.mapper.AvailableProductMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final InventoryClient inventoryClient;
    private final CatalogClient catalogClient;
    private final AvailableProductMapper availableProductMapper;

    public AvailableProductDto getAvailableProductByUniqueId(String uniqueId) {
        AvailabilityDto availabilityDto = inventoryClient
                .getAvailability(Collections.singletonList(uniqueId)).getFirst();

        if (availabilityDto.getAvailable() == 0) {
            throw new ProductNotAvailableException(uniqueId);
        }

        ProductDto productDto = catalogClient.getProductById(uniqueId);

        return availableProductMapper.toAvailableProduct(productDto, availabilityDto);

    }

    public List<AvailableProductDto> getAvailableProductsBySku(String sku) {
        List<ProductDto> products = catalogClient.getProductsBySku(sku);

        List<String> ids = products.stream().map(ProductDto::getUniqId).toList();

        List<AvailabilityDto> availabilities = inventoryClient.getAvailability(ids);

        Map<String, AvailabilityDto> availabilityMap = availabilities.stream()
                .collect(Collectors.toMap(AvailabilityDto::getUniqId, a -> a));

        return products.stream()
                .map(product -> {
                    AvailabilityDto availability = availabilityMap.get(product.getUniqId());
                    return availability != null
                            ? availableProductMapper.toAvailableProduct(product, availability)
                            : null;
                })
                .filter(Objects::nonNull)
                .filter(dto -> dto.getAvailable() != null && dto.getAvailable() > 0)
                .toList();

    }
}
