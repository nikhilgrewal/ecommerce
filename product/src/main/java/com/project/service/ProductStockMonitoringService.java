package com.project.service;

import com.project.ProductPropertyHolder;
import com.project.dao.ProductView;
import com.project.queryservice.ProductQueryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProductStockMonitoringService {

    private final ProductQueryService productQueryService;
    private final ProductPropertyHolder productPropertyHolder;
    private final NotificationService notificationService;

    public ProductStockMonitoringService(ProductQueryService productQueryService, ProductPropertyHolder productPropertyHolder, NotificationService notificationService) {
        this.productQueryService = productQueryService;
        this.productPropertyHolder = productPropertyHolder;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void monitorProductStock() {
        List<ProductView> lowStockProducts = productQueryService
                .fetchAllProductWithStock(productPropertyHolder.getProductLowStockThreshold());

        if (!lowStockProducts.isEmpty()) {
            String message = "The following products have low stock:\n" +
                    lowStockProducts.stream()
                            .map(product -> "Product: " + product.getName() + ", Stock: " + product.getStock())
                            .collect(Collectors.joining("\n"));

            notificationService.sendNotification(productPropertyHolder.getAdminEmail(), "Low Stock Alert", message);
        }
    }
}
