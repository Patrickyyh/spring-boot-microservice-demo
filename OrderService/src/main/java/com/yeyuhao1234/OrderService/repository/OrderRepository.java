package com.yeyuhao1234.OrderService.repository;

import com.yeyuhao1234.OrderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order , Long> {

}
