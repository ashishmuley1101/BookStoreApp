package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.OrderModel;

import javax.mail.MessagingException;
import java.util.List;

public interface IOrderService {

    OrderModel createOrder(int userId,OrderDTO orderDTO) throws MessagingException;

    List<OrderModel> getAllOrderRecords();

    void cancelOrderRecord(int orderId) throws MessagingException;

    List<OrderModel> getOrderItemByUserId(int userId);

    OrderModel update(int id, OrderDTO orderDTO);

    OrderModel getOrderItemById(int id);


}
