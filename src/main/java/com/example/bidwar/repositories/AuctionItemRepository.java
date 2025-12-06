package com.example.bidwar.repositories;

import com.example.bidwar.entities.AuctionItem;
import com.example.bidwar.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionItemRepository extends JpaRepository<AuctionItem,Long> {

    // to show the user's ongoing auction based on status
    List<AuctionItem> findByStatus(String status);

}
