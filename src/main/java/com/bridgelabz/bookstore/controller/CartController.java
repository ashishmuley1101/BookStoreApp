package com.bridgelabz.bookstore.controller;


import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookstore/cart")
public class CartController {

    @Autowired
    ICartService cartService;


    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getCart() {
        List<CartModel> cartModel = cartService.getCart();
        ResponseDTO responseDTO = new ResponseDTO("Getting all successfully",cartModel);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<ResponseDTO> getcartById(@PathVariable int userId) {
       CartModel cartModel = cartService.getCartById(userId);
        ResponseDTO responseDTO = new ResponseDTO("Record getting by ID : "+userId+" successfully",cartModel);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> create(@RequestBody CartDTO cartDTO) {
        CartModel cartModel = cartService.createCart(cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Record inserted successfully",cartModel);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{cartId}")
    public ResponseEntity<ResponseDTO> updateByCartId(@PathVariable int cartId,@RequestBody CartDTO cartDTO){
        CartModel cartModel=cartService.update(cartId,cartDTO);
        ResponseDTO responseDTO= new ResponseDTO("Update cart by id "+cartId+" successfully..!",cartModel);
        return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable int cartId) {
        cartService.deleteById(cartId);
        ResponseDTO respDTO = new ResponseDTO("Deleted Successfully", "Deleted id: " + cartId);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @DeleteMapping("/empty")
    public ResponseEntity<ResponseDTO> deleteById() {
        String cartModel = cartService.emptyCart();
        ResponseDTO responseDTO = new ResponseDTO("Remove all cart item successfully..!", cartModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }


}
