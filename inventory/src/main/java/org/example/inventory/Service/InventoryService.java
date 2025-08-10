package org.example.inventory.Service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.inventory.dto.AvailabilityDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryService {
    @Getter
    private final Map<String, AvailabilityDto> availabilities = new HashMap<>();

    @PostConstruct
    void load() throws Exception {
        CsvMapper mapperCsv = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        try (var is = getClass().getResourceAsStream("/data.csv")) {
            var it = mapperCsv.readerFor(AvailabilityDto.class).with(schema).readValues(is);
            while (it.hasNext()) {
                AvailabilityDto availabilityDto = (AvailabilityDto) it.next();
                availabilityDto.setAvailable((int) (Math.random() * 100));

                availabilities.put(availabilityDto.getUniqId(), availabilityDto);
            }
        }
    }
}
