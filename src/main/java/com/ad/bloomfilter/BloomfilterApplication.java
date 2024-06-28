package com.ad.bloomfilter;

import org.apache.coyote.http11.filters.IdentityOutputFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BloomfilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloomfilterApplication.class, args);
    }

}
