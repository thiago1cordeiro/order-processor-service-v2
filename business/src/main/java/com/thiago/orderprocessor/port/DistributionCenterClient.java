package com.thiago.orderprocessor.port;

import com.thiago.orderprocessor.dto.DistributionCenter;

public interface DistributionCenterClient {
    DistributionCenter getDistributionCenterByItemId(Integer itemId);
}
