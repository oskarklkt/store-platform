package org.example.catalog.repository;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.catalog.dto.ProductDto;
import org.example.catalog.dto.ProductRawDto;
import org.example.catalog.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogRepository {

    private final ProductMapper mapper;
    @Getter
    private final List<ProductDto> products = new ArrayList<>();

    @PostConstruct
    void load() throws Exception {
        CsvMapper mapperCsv = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        try (var is = getClass().getResourceAsStream("/data.csv")) {
            var it = mapperCsv.readerFor(ProductRawDto.class).with(schema).readValues(is);
            while (it.hasNext()) {
                products.add(mapper.toDto((ProductRawDto) it.next()));
            }
        }
    }
}
