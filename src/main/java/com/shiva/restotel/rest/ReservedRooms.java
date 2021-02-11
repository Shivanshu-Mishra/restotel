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
    CommandLineRunner reserve(RoomBookingRegisterV1 register){
        return args -> {
            log.info("Owner Room Registration "+register.save(new RoomBookingV1("Shiva", "2021-01-01", 7L)));
            log.info("Manger Room Registration "+register.save(new RoomBookingV1("Marcus Aurelius","2021-01-01",1L)));
        };
    }

    @Bean
    CommandLineRunner reserve2(RoomBookingRegisterV2 register,RoomRegister roomRegister){
        return args -> {
            Room room=new Room(1L,RoomType.AC);
            log.info("Register room"+roomRegister.save(room));
            log.info("Security Officier Registration"+register.save(new RoomBookingV2("Porus","Him001",room)));
            log.info("Account Officier Registration"+register.save(new RoomBookingV2("Nandini","Her001",room)));
        };
    }
}
