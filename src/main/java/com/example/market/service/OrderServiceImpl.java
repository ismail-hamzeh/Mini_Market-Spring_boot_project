package com.example.market.service;

import com.example.market.model.Orders;
import com.example.market.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public List<Orders> findAllByUserId(int user_id) {
        return orderRepository.findAllByUserIdOrderByDateTimeDesc(user_id);
    }

    @Override
    public void deleteOrderByOrderId(int orderId) {
        orderRepository.deleteByOrderId(orderId);
    }

    @Override
    public void deleteAllByUserId(int user_id) {
        orderRepository.deleteAllByUserId(user_id);
    }

    @Override
    public int countOrdersByUserId(int user_id) {
        return orderRepository.countOrdersByUserId(user_id);
    }
}
