package dev.arun.shoppingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Long orderId;
    private String desciption;
}
