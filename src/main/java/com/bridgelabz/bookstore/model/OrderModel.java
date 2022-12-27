package com.bridgelabz.bookstore.model;

import com.bridgelabz.bookstore.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor

public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @CreationTimestamp
    private LocalDate orderDate;

    private int quantity;
    private String address;
    private double totalPrice;

    private int userId;
    @ManyToMany
    @JoinColumn(name = "bookId")
    private List<BookModel> book;

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval=true)
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<CartModel> cartModel;

    private boolean cancel;


//    public OrderModel(String address,
//                      List<BookModel> bookModel, UserModel userModel, List<CartModel> cartModel,
//              double totalPrice, int totalQuantity ) {
//        this.address = address;
//        this.quantity = totalQuantity;
//        this.user = userModel;
//        this.book = bookModel;
//        this.cartModel = cartModel;
//        this.totalPrice = totalPrice;
//    }

    public OrderModel() {

    }

    public OrderModel(int id, int quantity, String address, BookModel bookModel, UserModel userModel, Object cancel, Object bookName) {

    }

    public OrderModel(int id, Integer quantity, String address, BookModel bookModel, UserModel userModel, Object cancel) {
    }

    public OrderModel(int userId, String address, List<CartModel> cartModel, List<BookModel> orderedBooks, int totalOrderQty, double totalOrderPrice) {
        this.userId=userId;
        this.address = address;
        this.quantity = totalOrderQty;

        this.book = orderedBooks;
        this.cartModel = cartModel;
        this.totalPrice = totalOrderPrice;
    }
}

