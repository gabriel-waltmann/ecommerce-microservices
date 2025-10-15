package com.waltmann.order_service.service;

import com.waltmann.order_service.dto.InventoryResponse;
import com.waltmann.order_service.dto.OrderLineItemsDto;
import com.waltmann.order_service.dto.OrderRequest;
import com.waltmann.order_service.model.Order;
import com.waltmann.order_service.model.OrderLineItems;
import com.waltmann.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapOrderLineItemsDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        // Call Inventory Service, and place order if product is in stock
        String skuCodesParam = String.join(",", skuCodes);
        String url = "http://inventory-service/inventory?skuCodes=" + skuCodesParam;

        InventoryResponse[] inventoryResponseArray = restTemplate.getForObject(
                url,
                InventoryResponse[].class
        );

        if (inventoryResponseArray == null){
            throw new IllegalArgumentException("Internal server error works eureka  ");
        }

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::getIsInStock);

        if (!allProductsInStock) {
            throw new IllegalArgumentException("Product are not in stock");
        }

        orderRepository.save(order);
    }

    private OrderLineItems mapOrderLineItemsDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
