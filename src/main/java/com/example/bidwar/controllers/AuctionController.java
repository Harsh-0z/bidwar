package com.example.bidwar.controllers;

import com.example.bidwar.dtos.AuctionCreateDto;
import com.example.bidwar.dtos.BidDto;
import com.example.bidwar.entities.AuctionItem;
import com.example.bidwar.services.AuctionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/auction")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;


    @PatchMapping("{itemId}/bid")
    public ResponseEntity<?> placeBid(@PathVariable Long itemId, @RequestBody BidDto bidDto) {
        try {
            AuctionItem updatedItem = auctionService.placeBid(itemId,bidDto.getBidderName(), bidDto.getAmount());
            return ResponseEntity.ok(updatedItem);
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflict! Someone placed a bid while you were viewing. Please refresh.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping(value="/{itemId}/image",consumes="multipart/form-data")
    public ResponseEntity<?> uploadImage(@PathVariable Long itemId, @RequestParam("file") MultipartFile file){
        try{
            auctionService.uploadImage(itemId, file);
            return ResponseEntity.ok("Image uploaded successfully");
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @GetMapping("/{itemId}/image")
    public ResponseEntity<?> getImage(@PathVariable Long itemId){
        try{
            byte[] imageData = auctionService.getImage(itemId);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)// Tells browser: "This is a picture!"
                    .body(imageData);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //post endpoint for creating auction

    @PostMapping("/create")
    //@Valid checked the dto we have created and check this /create data match all constraints or not .
    public ResponseEntity<?> createAuction(@Valid @RequestBody AuctionCreateDto dto){
        try{
            AuctionItem createdItem = auctionService.createAuction(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // for users part to check all active auctions
    @GetMapping("/active")
    public ResponseEntity<List<AuctionItem>> getActiveAuctions() {
        return ResponseEntity.ok(auctionService.getActiveAuctions());
    }

}
