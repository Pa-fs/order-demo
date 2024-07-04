package kty.green505.order_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderRequest {

    private final Long id;
    private final String product;
    private final int quantity;
    private final int price;

}
