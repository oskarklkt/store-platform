package org.example.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.example.inventory.Service.InventoryService;
import org.example.inventory.dto.AvailabilityDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @Value("${demo.latencyMs:0}")
    private long latencyMs;

    @Value("${demo.failPercent:0}")
    private int failPercent;

    private final Random random = new Random();


    @PostMapping("/availability")
    public ResponseEntity<List<AvailabilityDto>> getAvailability(@RequestBody List<String> uniqIds)
            throws InterruptedException {

        simulateSlownessAndFailure();


        List<AvailabilityDto> availabilities = uniqIds.stream()
                .filter(uniqId -> inventoryService.getAvailabilities().containsKey(uniqId))
                .map(uniqId -> inventoryService.getAvailabilities().get(uniqId))
                .toList();

        return ResponseEntity.ok(availabilities);
    }

    private void simulateSlownessAndFailure() throws InterruptedException {
        if (latencyMs > 0) {
            Thread.sleep(latencyMs);
        }
        if (failPercent > 0 && random.nextInt(100) < failPercent) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "simulated inventory failure");
        }
    }
}
