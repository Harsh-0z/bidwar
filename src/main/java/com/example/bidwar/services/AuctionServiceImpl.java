package com.example.bidwar.services;

import com.example.bidwar.dtos.AuctionCreateDto;
import com.example.bidwar.entities.AuctionItem;
import com.example.bidwar.entities.UserEntity;
import com.example.bidwar.repositories.AuctionItemRepository;
import com.example.bidwar.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionItemRepository itemRepo;
    private final UserRepository userRepo;


    @Override
    @Transactional
    public AuctionItem placeBid(Long itemId, String bidderName, Double newAmount) {
        AuctionItem item = itemRepo.findById(itemId).orElseThrow(() -> new RuntimeException("item not found"));

        UserEntity bidder = userRepo.findByUsername(bidderName).orElseThrow(() -> new RuntimeException("user not found"));

        //Time check logic
        //fetching auction end time from item itself
        if(LocalDateTime.now().isAfter(item.getAuctionEndTime())){
            throw new RuntimeException("Auction has ended! No more bids allowed.");
        }
        //amount check from item itself
        if(newAmount<=item.getCurrentPrice()){
            throw new RuntimeException("Bid must be higher than current price!");
        }

        //update
        item.setCurrentPrice(newAmount);
        item.setHighestBidder(bidder);


        return itemRepo.save(item);
    }

    @Override
    @Transactional
    public void uploadImage(Long itemId, MultipartFile file) throws IOException {
        AuctionItem item = itemRepo.findById(itemId).orElseThrow(() -> new RuntimeException("item not found"));

        item.setItemImage(file.getBytes());

        itemRepo.save(item);
    }

    @Override
    public byte[] getImage(Long itemId) {
        AuctionItem item = itemRepo.findById(itemId).orElseThrow(() -> new RuntimeException("item not found"));

        if(item.getItemImage()==null){
            throw new RuntimeException("No image found for this item");
        }
        return item.getItemImage();

    }

    @Override
    public AuctionItem createAuction(AuctionCreateDto dto) {
        AuctionItem item = AuctionItem.builder()
                .itemName(dto.getItemName())
                .description(dto.getDescription())
                .currentPrice(dto.getBasePrice())
                .status("ACTIVE")
                .auctionEndTime(LocalDateTime.now().plusHours(dto.getDurationInHours()))
                .version(0)
                .build();

        return itemRepo.save(item);
    }

    @Override
    public List<AuctionItem> getActiveAuctions() {
        return itemRepo.findByStatus("ACTIVE");
    }


}
