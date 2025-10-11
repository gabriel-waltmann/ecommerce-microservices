package com.waltmann.notion_service.repository;

import com.waltmann.notion_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
