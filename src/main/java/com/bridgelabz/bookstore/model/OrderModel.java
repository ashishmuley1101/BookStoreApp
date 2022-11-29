package com.bridgelabz.bookstore.model;

import com.bridgelabz.bookstore.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor

public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @CreationTimestamp
    private LocalDate orderDate;
    private int price;
    private int quantity;
    private String address;
    private double totalPrice;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private BookModel book;

    @OneToOne
    @JoinColumn(name = "cartId")
    private CartModel cartModel;

    private boolean cancel;


    public OrderModel(OrderDTO orderDTO, BookModel bookModel, UserModel userModel, CartModel cartModel) {
        this.address = userModel.getAddress();
        this.price = bookModel.getPrice();
        this.quantity = cartModel.getQuantity();
        this.user = userModel;
        this.book = bookModel;
        this.cartModel = cartModel;
        this.totalPrice = cartModel.getTotalPrice();
    }

    public OrderModel() {

    }

    public OrderModel(int id, int quantity, String address, BookModel bookModel, UserModel userModel, Object cancel, Object bookName) {

    }

    public OrderModel(int id, Integer quantity, String address, BookModel bookModel, UserModel userModel, Object cancel) {
    }
}

