package com.example.bidwar.config;


import com.example.bidwar.entities.AuctionItem;
import com.example.bidwar.entities.UserEntity;
import com.example.bidwar.repositories.AuctionItemRepository;
import com.example.bidwar.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

// automatically runs due to command line runner it will create one admin user and regular user
//one dummy auction items
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final AuctionItemRepository itemRepo;

    @Override
    public void run(String... args) throws Exception {
        if(userRepo.count()==0) { // only seed if db is empty
            UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .password("{noop}admin123")
                    .role("ROLE_ADMIN")
                    .build();


            UserEntity user = UserEntity.builder()
                    .username("ravi")
                    .password("{noop}ravi123")
                    .role("ROLE_USER")
                    .build();

            userRepo.save(admin);
            userRepo.save(user);

            AuctionItem item = AuctionItem.builder()
                    .itemName("Vintage Camera")
                    .description("A classic 1950s camera in good condition.")
                    .currentPrice(100.00)
                    .status("ACTIVE")
                    .auctionEndTime(LocalDateTime.now().plusHours(2)) // Ends in 2 hours
                    .version(0) // Start version at 0
                    .build();

            itemRepo.save(item);

            System.out.println("Data seeding completed:Admin and user created");

        }

    }



}
