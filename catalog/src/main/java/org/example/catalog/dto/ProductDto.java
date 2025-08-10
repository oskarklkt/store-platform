package org.example.catalog.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDto {
    private String uniqId;
    private String sku;
    private String title;
    private String description;
    private BigDecimal listPrice;
    private BigDecimal salePrice;
    private String category;
    private List<String> categoryTree;
    private Double averageRating;
    private String productUrl;
    private List<String> imageUrls;
    private String brand;
    private Integer totalReviews;
    private List<String> reviews;
}
