package com.example.bidwar.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="AUCTION_ITEMS")
public class AuctionItem {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String itemName;

    private String description;

    @Lob // for large object
    @Column(length = 10000000) // allow upto 10mb
    private byte[] itemImage;

    // bidding info
    @Min(0)
    private Double currentPrice;

    // who is winning the bid ??
    // if null no one has bid yet
    @ManyToOne
    @JoinColumn(name="highest_bidder_id")
    private UserEntity highestBidder;

    //@Future from jakarta bean validation
    @Future(message="Auction end time must be in the future")// Ensures the date must be greater than the current date/time
    private LocalDateTime auctionEndTime;

    @Builder.Default
    private String status="ACTIVE";

    @Version // for concurrency
    private Integer version;

}
