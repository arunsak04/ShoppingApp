package dev.arun.shoppingapp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.arun.shoppingapp.exception.OrderNotFoundException;
import dev.arun.shoppingapp.model.ErrorResponse;
import dev.arun.shoppingapp.model.Inventory;
import dev.arun.shoppingapp.model.Order;
import dev.arun.shoppingapp.model.Transaction;
import dev.arun.shoppingapp.model.User;
import dev.arun.shoppingapp.service.CouponService;
import dev.arun.shoppingapp.service.InventoryService;
import dev.arun.shoppingapp.service.OrderService;
import dev.arun.shoppingapp.service.TransactionService;
import dev.arun.shoppingapp.service.UserService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	 @Autowired
    private   OrderService orderService;
	  @Autowired
    private   InventoryService inventoryService;
	  @Autowired
    private   TransactionService transactionService;
	  @Autowired
    private   CouponService couponService;
	  @Autowired
    private   UserService userService;
    
  
    

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            throw new OrderNotFoundException(id);
        }
    }
    
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getOrderId(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userId}/order")
    public ResponseEntity<Map<String, Object>> createOrder(@PathVariable Long userId,
                                                           @RequestParam Long productId,
                                                           @RequestParam int quantity,
                                                           @RequestParam String couponCode) {
        Map<String, Object> response = new HashMap<>();

        // Check if the product is present in inventory
        Inventory optionalProduct = inventoryService.getProductById(productId);
        if (optionalProduct==null) {
            response.put("description", "Product not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        System.out.println(optionalProduct);
        Inventory product = optionalProduct;
        long availableQuantity = product.getAvailable();
        if (availableQuantity < quantity) {
            response.put("description", "Only " + availableQuantity + " left in stock");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        System.out.println(product);
        Optional<User> optionalUser=userService.getUserById(userId);
        // check user is present
        if(!optionalUser.isPresent()) {
        	response.put("description", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        User user=optionalUser.get();
        // check for user is used coupon before
        if(user.getCouponsUsed()>0) {
        	response.put("description", "Sorry, you've already redeemed this coupon before");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        System.out.println(user);
        // Check if the coupon is present
        if (couponService.existByCouponCode(couponCode)==false) {
            response.put("description", "Invalid coupon");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        int discount = couponService.getDiscountByCouponName(couponCode);

        System.out.println(couponCode);
        // Apply coupon discount
        double productPrice = product.getPrice();
        double discountedAmount = productPrice * quantity * (1 - (discount / 100.0));

        // Generate order ID and transaction ID
        String orderId = generateOrderId();
        String transactionId = generateTransactionId();

        // Save order to database
        Order order = new Order();
        order.setUserId(userId);
//        order.setProductId(productId);
        order.setOrderId(generateOrderId());
        order.setQuantity(quantity);
        order.setAmount(discountedAmount);
        order.setCoupon(couponCode);
        order.setOrderedDate(new Date()); // Set current date
        orderService.createOrder(order);

        // Save transaction to database
        Transaction transaction = new Transaction();
        transaction.setUserid(userId);
        transaction.setOrderId(orderId);
        transaction.setAmount(discountedAmount);
        transaction.setDate(new Date()); // Set current date
//        transaction.setCoupon(couponCode);
        transaction.setTransactionId(generateTransactionId());
        transaction.setStatus("successful");
        transactionService.saveTransaction(transaction);
        
        long orderedQuantity=user.getOrderedQuantity()+quantity;
        long couponsUsed=user.getCouponsUsed()+1;
        user.setCouponsUsed(couponsUsed);
        user.setOrderedQuantity(orderedQuantity);
        userService.updateUser(userId,user);
        
        long updatedOrders=product.getOrderd()+quantity;
        int updatedqty=product.getAvailable()-quantity;
        product.setOrderd(updatedOrders);
        product.setAvailable(updatedqty);
        inventoryService.updateInventory(product);

        // Prepare response
        response.put("orderId", orderId);
        response.put("transactionId", transactionId);
        response.put("status", "successful");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{userId}/{orderId}/pay")
    public ResponseEntity<Map<String, Object>> processPayment(@PathVariable Long userId,
                                                              @PathVariable Long orderId,
                                                              @RequestParam double amount) {
        Map<String, Object> response = new HashMap<>();
        // Mock payment status randomly
        int statusCode = (int) (Math.random() * 6) + 1;
        switch (statusCode) {
            case 1:
                response.put("status", "successful");
                break;
            case 2:
                response.put("status", "failed");
                response.put("description", "Payment Failed as amount is invalid");
                break;
            case 3:
                response.put("status", "failed");
                response.put("description", "Payment Failed from bank");
                break;
            case 4:
                response.put("status", "failed");
                response.put("description", "Payment Failed due to invalid order id");
                break;
            case 5:
                response.put("status", "failed");
                response.put("description", "No response from payment server");
                break;
            case 6:
                response.put("status", "failed");
                response.put("description", "Order is already paid for");
                break;
        }
        response.put("userId", userId);
        response.put("orderId", orderId);
        response.put("transactionId", "TRAN" + (int) (Math.random() * 1000000));
        
//        return ResponseEntity.status(statusCode == 1 ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
        // Set appropriate HTTP status code based on payment status
        HttpStatus httpStatus = HttpStatus.OK;
        if (statusCode != 1) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (statusCode == 2 || statusCode == 3 || statusCode == 4 || statusCode == 5) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (statusCode == 6) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        }

        return ResponseEntity.status(httpStatus).body(response);
    }
    
    
    private String generateOrderId() {
        // Generate a unique order ID using a prefix and a random 4-digit suffix
        String orderIdPrefix = "ORD";
        int randomSuffix = new Random().nextInt(10000); // Generate a random 4-digit number
        return orderIdPrefix + String.format("%04d", randomSuffix);
    }

    private String generateTransactionId() {
        // Generate a unique transaction ID using a prefix and a random 4-digit suffix
        String transactionIdPrefix = "TRAN";
        int randomSuffix = new Random().nextInt(10000); // Generate a random 4-digit number
        return transactionIdPrefix + String.format("%04d", randomSuffix);
    }
    
    
}
