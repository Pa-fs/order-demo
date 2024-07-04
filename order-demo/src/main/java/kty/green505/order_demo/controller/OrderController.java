package kty.green505.order_demo.controller;

import kty.green505.order_demo.dto.CreateOrderRequest;
import kty.green505.order_demo.dto.CreateOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class OrderController {

    // DB가 아직 없으므로 전역변수로 해결
    private static Map<String, Boolean> orderStatusMap;

    @PostMapping(path = "/order")
    public ResponseEntity<CreateOrderResponse> getOrder(@RequestBody CreateOrderRequest createOrderRequest) {

        CreateOrderResponse response = CreateOrderResponse.builder()
                .orderId(UUID.randomUUID())
                .orderStatus("OrderSuccess")
                .message("Processing your order...")
                .build();

        orderStatusMap = new HashMap<>();
        orderStatusMap.put(response.getOrderId().toString(), true);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/check-status")
    public ResponseEntity<String> checkCompletedOrder() {
        if(orderStatusMap == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok("Order is completed by OrderId : " + orderStatusMap.keySet());
    }

}
