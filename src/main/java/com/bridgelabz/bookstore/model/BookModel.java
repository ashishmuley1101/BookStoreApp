package com.bridgelabz.bookstore.model;


import com.bridgelabz.bookstore.dto.BookDTO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int bookId;

    private String bookName;

    private String authorName;

    private String bookDescription;

    private String bookImg;
    private int price;

    private int quantity;

    public BookModel(BookDTO bookDTO) {

        this.bookName = bookDTO.getBookName();
        this.authorName = bookDTO.getAuthorName();
        this.bookDescription = bookDTO.getBookDescription();
        this.bookImg = bookDTO.getBookImg();
        this.price = bookDTO.getPrice();
        this.quantity = bookDTO.getQuantity();

    }

    public BookModel() {

    }
}


