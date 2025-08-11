package org.example.product.clients;

import org.example.product.dto.AvailabilityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "inventory", path = "/v1")
public interface InventoryClient {
    @PostMapping("/availability")
    List<AvailabilityDto> getAvailability(@RequestBody List<String> uniqIds);
}
