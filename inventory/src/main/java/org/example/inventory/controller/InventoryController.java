package org.example.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.example.inventory.Service.InventoryService;
import org.example.inventory.dto.AvailabilityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/availability")
    public ResponseEntity<List<AvailabilityDto>> getAvailability(@RequestBody List<String> uniqIds) {
        List<AvailabilityDto> availabilities = uniqIds.stream()
                .filter(uniqId -> inventoryService.getAvailabilities().containsKey(uniqId))
                .map(uniqId -> inventoryService.getAvailabilities().get(uniqId))
                .toList();

        return ResponseEntity.ok(availabilities);
    }
}
