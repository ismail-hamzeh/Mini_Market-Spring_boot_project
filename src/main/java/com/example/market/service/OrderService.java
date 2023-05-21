package com.example.market.service;

import com.example.market.model.Orders;

import java.util.List;

public interface OrderService {
    Orders save(Orders orders);

    List<Orders> findAllByUserId(int user_id);

    void deleteOrderByOrderId(int orderId);

    void deleteAllByUserId(int user_id);

    int countOrdersByUserId(int user_id);
}
