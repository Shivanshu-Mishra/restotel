package com.shiva.restotel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfig {
    public static final Contact DEFAULT_CONTACT = new Contact("Shivanshu Mishra", "https://github.com/Shivanshu-Mishra/restotel", "shivanshu.mishra97@gmail.com");
    public static final ApiInfo DEFAULT_API_INFO=new ApiInfo("Restotel Api Documentation", "Api Documentation for Restotel", "1.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    private static final Set<String> DEFAULT_CONSUMES_PRODUCES=new HashSet<String>(Arrays.asList("application/json"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_CONSUMES_PRODUCES)
                .consumes(DEFAULT_CONSUMES_PRODUCES);
    }
}
