package org.example.catalog.mapper;

import org.example.catalog.dto.ProductDto;
import org.example.catalog.dto.ProductRawDto;
import org.mapstruct.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "title", source = "nameTitle"),
            @Mapping(target = "listPrice", qualifiedByName = "toBigDecimal", source = "listPrice"),
            @Mapping(target = "salePrice", qualifiedByName = "toBigDecimal", source = "salePrice"),
            @Mapping(target = "categoryTree", qualifiedByName = "splitCategories", source = "categoryTree"),
            @Mapping(target = "averageRating", qualifiedByName = "leadingDouble", source = "averageProductRating"),
            @Mapping(target = "imageUrls", qualifiedByName = "splitImageUrls", source = "productImageUrls"),
            @Mapping(target = "totalReviews", qualifiedByName = "digitsToInt", source = "totalNumberReviews"),
            @Mapping(target = "reviews", qualifiedByName = "parseReviews", source = "reviews")
    })
    ProductDto toDto(ProductRawDto raw);

    @Named("toBigDecimal")
     static BigDecimal toBigDecimal(String s) {
        if (s == null || s.isBlank()) return null;
        String t = s.replace("$", "").replace(",", "").trim();
        try { return new BigDecimal(t); } catch (Exception e) { return null; }
    }

    @Named("digitsToInt")
     static Integer digitsToInt(String s) {
        if (s == null) return null;
        String t = s.replaceAll("[^0-9]", "");
        return t.isEmpty() ? null : Integer.valueOf(t);
    }

    @Named("leadingDouble")
     static Double leadingDouble(String s) {
        if (s == null) return null;
        Matcher m = Pattern.compile("^\\s*([0-9]+(?:\\.[0-9]+)?)").matcher(s);
        return m.find() ? Double.valueOf(m.group(1)) : null;
    }

    @Named("splitCategories")
     static List<String> splitCategories(String s) {
        if (s == null || s.isBlank()) return List.of();
        String[] parts = s.split("\\|");
        List<String> out = new ArrayList<>(parts.length);
        for (String p : parts) {
            String t = p.trim();
            if (!t.isEmpty()) out.add(t);
        }
        return out;
    }

    @Named("splitImageUrls")
     static List<String> splitImageUrls(String s) {
        if (s == null || s.isBlank()) return List.of();
        String decoded = s.replace("&amp;", "&").trim();
        String[] parts = decoded.split("\\s*,\\s*");
        List<String> out = new ArrayList<>(parts.length);
        for (String p : parts) {
            String t = URLDecoder.decode(p.trim(), StandardCharsets.UTF_8);
            if (!t.isEmpty()) out.add(t);
        }
        return out;
    }

    @Named("parseReviews")
     static List<String> parseReviews(String s) {
        if (s == null || s.isBlank()) return List.of();
        try {
            String jsonish = s.replace("=>", ":");
            com.fasterxml.jackson.databind.ObjectMapper om = new com.fasterxml.jackson.databind.ObjectMapper();
            var root = om.readTree(jsonish);
            var arr = root.get("review");
            if (arr != null && arr.isArray()) {
                List<String> out = new ArrayList<>();
                arr.forEach(node -> node.fields().forEachRemaining(e -> out.add(e.getValue().asText())));
                return out;
            }
        } catch (Exception ignore) {
            var m = Pattern.compile("=>\\s*\"([^\"]+)\"").matcher(s);
            List<String> out = new ArrayList<>();
            while (m.find()) out.add(m.group(1));
            if (!out.isEmpty()) return out;
        }
        return List.of();
    }
}

