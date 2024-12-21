package com.project.controllor;

import com.project.controllor.request.CreateOrderRequest;
import com.project.dao.OrderStatus;
import com.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.project.utils.ResponseProcessorUtil.processResult;

@RestController
@RequestMapping("/order")
public class OrderControllor {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestHeader("userId") String userId,
            @RequestBody CreateOrderRequest createOrderRequest) {
        return processResult(orderService.createOrder(userId, createOrderRequest));
    }

    @PutMapping("/{orderReferenceId}")
    public ResponseEntity<?> updateOrderStatus(
            @RequestHeader("userId") String userId,
            @PathVariable UUID orderReferenceId,
            @RequestParam("orderStatus")OrderStatus orderStatus) {
        return processResult(orderService.updateOrderStatus(userId, orderReferenceId, orderStatus));
    }

    @GetMapping("/fetch-all/{userId}")
    public ResponseEntity<?> fetchAllOrdersForUser(@PathVariable String userId) {
        return processResult(orderService.fetchAllOrderForUser(userId));
    }

    /* This api is written by assuming that we need to delete the order permanently*/
    @DeleteMapping("/{orderReferenceId}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID orderReferenceId) {
        return processResult(orderService.deleteOrderPermanently(orderReferenceId));
    }

}
