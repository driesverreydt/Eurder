package com.switchfully.projects.eurder.repository;

import com.switchfully.projects.eurder.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepositoryInterface extends JpaRepository<Order, UUID> {
}
