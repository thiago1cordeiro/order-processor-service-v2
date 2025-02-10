package com.thiago.orderprocessor.controller.documentation;

import com.thiago.orderprocessor.dto.ErrorResponseDTO;
import com.thiago.orderprocessor.dto.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderControllerDocumentation {
    
    @Operation(
            summary = "Create new order",
            description = "Create new order, get distribution center and save in order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order Create success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid json order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))}),
            @ApiResponse(responseCode = "409", description = "Order Number exist in db",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error Server",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))})
        
    })
    ResponseEntity<Order> orderProcessor(@RequestBody final Order order);
    
    @Operation(
            summary = "Get order by id",
            description = "Search Order By id with distribution center")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Order success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))}),
            @ApiResponse(responseCode = "404", description = "Order not found in db",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error Server",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))})
        
    })
    ResponseEntity<Order> getOrderById(@PathVariable("orderId") final Integer orderId);
    
}
