//package dev.arun.shoppingapp.exception;
//
//public class OrderNotFoundException extends RuntimeException {
//
//    public OrderNotFoundException(String message) {
//        super(message);
//    }
//}

package dev.arun.shoppingapp.exception;

import lombok.Data;

@Data
public class OrderNotFoundException extends RuntimeException {

    private final Long orderId;

    public OrderNotFoundException(Long orderId) {
        super("Order not found");
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}

