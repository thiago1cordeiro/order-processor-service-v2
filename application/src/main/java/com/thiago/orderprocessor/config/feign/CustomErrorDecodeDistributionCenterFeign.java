package com.thiago.orderprocessor.config.feign;

import com.thiago.orderprocessor.exception.BadRequestException;
import com.thiago.orderprocessor.exception.CustomsNotFoundException;
import com.thiago.orderprocessor.exception.InternalServerErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecodeDistributionCenterFeign implements ErrorDecoder {
    
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new BadRequestException(STR."Bad Request: \{response.reason()}");
            case 404:
                return new CustomsNotFoundException(STR."Item does not have CD");
            default:
                return new InternalServerErrorException("Internal Server Error");
        }
    }
}
