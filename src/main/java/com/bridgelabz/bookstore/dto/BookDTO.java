package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class BookDTO {

    private String bookName;
    private String authorName;
    private String bookDescription;
    private String bookImg;
    private Integer price;
    private int quantity;

}
