package com.example.market.repository;

import com.example.market.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findAllByUserIdOrderByDateTimeDesc(int user_id);

    void deleteByOrderId(int orderId);

    void deleteAllByUserId(int user_id);

    int countOrdersByUserId(int user_id);
}
