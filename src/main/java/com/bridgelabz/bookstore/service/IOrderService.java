package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.OrderModel;

import javax.mail.MessagingException;
import java.util.List;

public interface IOrderService {

    OrderModel createOrder(OrderDTO orderDTO) throws MessagingException;

    List<OrderModel> getAllOrderRecords();

    OrderModel cancelOrderRecord(int userId) throws MessagingException;

    OrderModel getOrderItemByUserId(int orderId);

    OrderModel update(int id, OrderDTO orderDTO);
}
