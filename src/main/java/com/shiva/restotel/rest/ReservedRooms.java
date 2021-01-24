package com.shiva.restotel.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservedRooms {
    private static final Logger log= LoggerFactory.getLogger(ReservedRooms.class);

    @Bean
    CommandLineRunner reserve(Register register){
        return args -> {
            log.info("Owner Room Registration "+register.save(new RoomBooking("Shiva", "2021-01-01", 7L)));
            log.info("Manger Room Registration "+register.save(new RoomBooking("Marcus Aurelius","2021-01-01",1L)));
        };
    }
}
