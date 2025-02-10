package com.thiago.orderprocessor.config.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    
    @Bean
    public ErrorDecoder customErrorDecoder() {
        return new CustomErrorDecodeDistributionCenterFeign();
    }

}