package org.example.product.mapper;

import org.example.product.dto.AvailabilityDto;
import org.example.product.dto.AvailableProductDto;
import org.example.product.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvailableProductMapper {

    @Mapping(source = "product.uniqId", target = "uniqId")
    @Mapping(source = "product.sku", target = "sku")
    @Mapping(source = "product.title", target = "title")
    @Mapping(source = "product.description", target = "description")
    @Mapping(source = "product.listPrice", target = "listPrice")
    @Mapping(source = "product.salePrice", target = "salePrice")
    @Mapping(source = "product.category", target = "category")
    @Mapping(source = "product.categoryTree", target = "categoryTree")
    @Mapping(source = "product.averageRating", target = "averageRating")
    @Mapping(source = "product.productUrl", target = "productUrl")
    @Mapping(source = "product.imageUrls", target = "imageUrls")
    @Mapping(source = "product.brand", target = "brand")
    @Mapping(source = "product.totalReviews", target = "totalReviews")
    @Mapping(source = "product.reviews", target = "reviews")
    @Mapping(source = "availability.available", target = "available")
    AvailableProductDto toAvailableProduct(ProductDto product, AvailabilityDto availability);
}
