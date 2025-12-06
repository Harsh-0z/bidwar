package com.example.bidwar.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuctionCreateDto {
    @NotBlank(message = "Item name is required")
    private String itemName;

    private String description;

    @NotNull(message = "Base price is required")
    @Min(value = 1, message = "Price must be positive")
    private Double basePrice;

    // We ask "How many hours should this run?" instead of a specific date
    // It's easier for the UI to send "24" (hours) than "2025-12-07T10:00:00"
    @Min(value = 1, message = "Duration must be at least 1 hour")
    private Integer durationInHours;
}
