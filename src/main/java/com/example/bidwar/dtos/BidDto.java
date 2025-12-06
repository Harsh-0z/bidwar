package com.example.bidwar.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BidDto {
    @NotNull(message="Bidder name is required")
    private String bidderName;

    @Min(value=1,message="Bid amount must be positive")
    private Double amount;
}
