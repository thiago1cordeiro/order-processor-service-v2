package com.thiago.orderprocessor.client;

import com.thiago.orderprocessor.config.feign.FeignConfig;
import com.thiago.orderprocessor.dto.DistributionCenter;
import com.thiago.orderprocessor.port.DistributionCenterClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "DistributionCenterService",
        url = "${beeceptor.baseurl}",
        configuration = FeignConfig.class
)
public interface DistributionCenterClientImpl extends DistributionCenterClient {
    @Override
    @GetMapping("/distribuitioncenters")
    DistributionCenter getDistributionCenterByItemId(@RequestParam("itemId")Integer itemId);
    
}
