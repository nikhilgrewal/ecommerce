package com.project.queryservice;

import com.project.dao.OrderStatus;
import com.project.dao.OrderView;
import com.project.entity.Order;
import com.project.repository.OrderRepository;
import com.project.repository.ProductRepository;
import com.project.utils.EntityToDaoMapper;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.utils.DaoToEntityMapper.convertOrderViewToOrder;
import static com.project.utils.DaoToEntityMapper.convertProductViewToProduct;
import static com.project.utils.EntityToDaoMapper.convertOrderToOrderView;

@Service
@Log4j2
public class OrderQueryService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderQueryService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public String createOrder(OrderView orderView) {
        Order order = convertOrderViewToOrder(orderView);
        order.setReferenceId(UUID.randomUUID());
        orderView.getOrderProductViewList().stream()
                .map( orderProductView -> {
                    return productRepository.save(convertProductViewToProduct(orderProductView.getProductView()));
                });
        order.getOrderProducts().forEach(orderProduct -> {orderProduct.setOrder(order);});
        orderRepository.save(order);
        return order.getReferenceId().toString();
    }

    @Transactional
    public Optional<OrderView> updateOrder(UUID orderReferenceId, OrderStatus orderStatus) {
        Optional<Order> order = orderRepository.findByReferenceId(orderReferenceId);
        if (order.isEmpty()) {
            return Optional.empty();
        }
        order.get().setStatus(orderStatus);
        orderRepository.save(order.get());
        return Optional.of(convertOrderToOrderView(order.get()));
    }

    public List<OrderView> fetchOrdersByStatusAndCreatedAtBefore(LocalDateTime cutOffTime, OrderStatus orderStatus) {
        return orderRepository.findAllByOrderStatusAndCreatedAt(cutOffTime, orderStatus)
                .stream()
                .map(EntityToDaoMapper::convertOrderToOrderView)
                .toList();
    }

    @Transactional
    public Optional<OrderView> deleteOrderPermanently(UUID orderReferenceId) {
        Optional<Order> order = orderRepository.findByReferenceId(orderReferenceId);
        if (order.isEmpty()) {
            return Optional.empty();
        }
        orderRepository.delete(order.get());
        return Optional.of(EntityToDaoMapper.convertOrderToOrderView(order.get()));
    }
}
