package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.OrderModel;
import com.bridgelabz.bookstore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
//@RequestMapping("/bookstore/order")
public class OrderController {

    @Autowired
    IOrderService orderService;


    @GetMapping("/getAllOrder")
    public ResponseEntity<ResponseDTO> getAllOrderRecords() {
        List<OrderModel> newOrder = orderService.getAllOrderRecords();
        ResponseDTO responseDto = new ResponseDTO("All records retrieved successfully !", newOrder);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getOrderByUserId/{userId}")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable int userId) {
        List<OrderModel> order = orderService.getOrderItemByUserId(userId);
        ResponseDTO responseDto = new ResponseDTO("Order details for user successful", order);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/bookOrder/{userId}")
    public ResponseEntity<ResponseDTO> createOrder(@PathVariable int userId,@RequestBody OrderDTO orderDTO) throws MessagingException {
        OrderModel newOrder = orderService.createOrder(userId,orderDTO);
        ResponseDTO responseDTO = new ResponseDTO("Order registered successfully !", newOrder);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/cancelOrder/{id}")
    public ResponseEntity<ResponseDTO> cancelOrderRecord(@PathVariable int id) throws MessagingException {
         orderService.cancelOrderRecord(id);
        orderService.cancelOrderRecord(id);
        ResponseDTO responseDTO = new ResponseDTO("Order cancel for id successful", "Order cancelled for id: "+id);

        return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<ResponseDTO> updateByCartId(@PathVariable int oerderId, @RequestBody OrderDTO orderDTO) {
        OrderModel orderModel = orderService.update(oerderId, orderDTO);
        ResponseDTO responseDTO = new ResponseDTO("Update cart by id " + oerderId + " successfully..!", orderModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }


}
