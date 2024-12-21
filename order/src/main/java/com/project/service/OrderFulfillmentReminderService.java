package com.project.service;

import com.project.OrderPropertyHolder;
import com.project.dao.OrderStatus;
import com.project.dao.OrderView;
import com.project.queryservice.OrderQueryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class OrderFulfillmentReminderService {

    private final OrderQueryService orderQueryService;
    private final NotificationService notificationService;
    private final OrderPropertyHolder orderPropertyHolder;

    public OrderFulfillmentReminderService(OrderQueryService orderQueryService, NotificationService notificationService, OrderPropertyHolder orderPropertyHolder) {
        this.orderQueryService = orderQueryService;
        this.notificationService = notificationService;
        this.orderPropertyHolder = orderPropertyHolder;
    }

    @Scheduled(cron = "0 0 * * * ?") // Runs every hour
    public void sendOrderReminders() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(orderPropertyHolder.getOrderReminderCutOffTime());
        List<OrderView> pendingOrders = orderQueryService.fetchOrdersByStatusAndCreatedAtBefore(cutoffTime, OrderStatus.CREATED);

        pendingOrders.forEach(order -> {
            String message = "Dear Customer,\n\nYour order (ID: " + order.getReferenceId() + ") is still pending. Please fulfill it at the earliest.";
            notificationService.sendNotification(order.getUserView().getEmail(), "Order Fulfillment Reminder", message);
        });
    }
}

