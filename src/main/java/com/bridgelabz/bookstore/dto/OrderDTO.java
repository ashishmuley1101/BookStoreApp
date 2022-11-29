package com.bridgelabz.bookstore.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class OrderDTO {

    private int cartId;

    private int quantity;

    private String address;



}
