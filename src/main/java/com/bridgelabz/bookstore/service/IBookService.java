package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.model.UserModel;

import java.util.List;

public interface IBookService {


    BookModel createBook(BookDTO bookDTO);

    List<BookModel> getAllBookData();

    BookModel updateRecordById(BookDTO bookDTO, int bookId);

    BookModel deleteBookRecord(int bookId);

    BookModel getBookModelById(int bookId);

    List<BookModel> sortedListOfBooksInAscendingOrder();

    List<BookModel> sortedListOfBooksInDescendingOrder();

    List<BookModel> getBookModelDataByName(String bookName);

}
