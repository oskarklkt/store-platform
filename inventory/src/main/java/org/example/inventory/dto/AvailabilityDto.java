package org.example.inventory.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailabilityDto {
    @JsonAlias("uniq_id")
    private String uniqId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer available;
}
