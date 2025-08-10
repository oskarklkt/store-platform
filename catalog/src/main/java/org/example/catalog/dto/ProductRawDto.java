package org.example.catalog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductRaw {
    @JsonProperty("uniq_id")
    private String uniqId;
    private String sku;

    @JsonProperty("name_title")
    private String nameTitle;

    private String description;

    @JsonProperty("list_price")
    private String listPrice;

    @JsonProperty("sale_price")
    private String salePrice;

    private String category;

    @JsonProperty("category_tree")
    private String categoryTree;

    @JsonProperty("average_product_rating")
    private String averageProductRating;

    @JsonProperty("product_url")
    private String productUrl;

    @JsonProperty("product_image_urls")
    private String productImageUrls;

    private String brand;

    @JsonProperty("total_number_reviews")
    private String totalNumberReviews;

    @JsonProperty("Reviews")
    private String reviews;
}
