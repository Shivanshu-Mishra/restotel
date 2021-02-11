package com.shiva.restotel.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfiguration {
    private static final Logger LOG= LoggerFactory.getLogger(EmployeeController.class);

    @Bean
    public CommandLineRunner getOwner(EmployeeRegister register){
        return args -> {
            LOG.info("Owner"+register.save(new Employee("Shivanshu","CEO",40,78000)));
            LOG.info("Manager"+register.save(new Employee("Marcus Aurelius","Manager",38,6000)));
        };
    }
}
