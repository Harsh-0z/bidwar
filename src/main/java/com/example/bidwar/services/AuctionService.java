package com.example.bidwar.services;


import com.example.bidwar.dtos.AuctionCreateDto;
import com.example.bidwar.entities.AuctionItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AuctionService {
    AuctionItem placeBid(Long itemId, String bidderName, Double newAmount);

    // for image
    void uploadImage(Long itemId, MultipartFile file) throws IOException;

    //getImage
    byte[] getImage(Long itemId);

    //createAuction logic for admin
    AuctionItem createAuction(AuctionCreateDto dto);

    // for users to see available items to be bid
    List<AuctionItem> getActiveAuctions();
}
