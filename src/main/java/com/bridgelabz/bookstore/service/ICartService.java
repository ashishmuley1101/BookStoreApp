package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.model.CartModel;

import java.util.List;
import java.util.Optional;

public interface ICartService {

    CartModel getCartById(int id);

    List<CartModel> getCart();

    CartModel createCart(CartDTO cartDTO);

    CartModel update( int id,CartDTO cartDTO);

    void deleteById(int id);

    String emptyCart();
}