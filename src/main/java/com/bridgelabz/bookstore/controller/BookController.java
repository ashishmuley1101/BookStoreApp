package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bookstore/book")
public class BookController {
    @Autowired
    IBookService bookService;



    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllBookData() {
        List<BookModel> listOfBooks = bookService.getAllBookData();
        ResponseDTO responseDto = new ResponseDTO("Data retrieved successfully :", listOfBooks);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping("/get/{bookId}")
    public ResponseEntity<ResponseDTO> getBookModelById(@PathVariable int bookId) {
        BookModel bookModel = bookService.getBookModelById(bookId);
        ResponseDTO responseDto = new ResponseDTO("Success Call for Book Id!!!", bookModel);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/searchBookByBookName/{bookName}")
    public ResponseEntity<ResponseDTO> getBookModelDataByName(@PathVariable("bookName") String bookName) {
        List<BookModel> bookModelDataList = null;
        bookModelDataList = bookService.getBookModelDataByName(bookName);
        ResponseDTO respDTO = new ResponseDTO("Get call success", bookModelDataList);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> addUserInBookStore(@RequestBody BookDTO bookDto) {
        BookModel newBook = bookService.createBook(bookDto);
        ResponseDTO responseDto = new ResponseDTO("Created the new book in book store", newBook);
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateBookById/{bookId}")
    public ResponseEntity<ResponseDTO> updateRecordById(@PathVariable int bookId, @Valid @RequestBody BookDTO bookDTO) {
        BookModel updateRecord = bookService.updateRecordById(bookDTO,bookId);
        ResponseDTO responseDto = new ResponseDTO(" Book Record updated successfully by Id", updateRecord);
        return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity<ResponseDTO> deleteRecordById(@PathVariable int bookId) {
        BookModel bookModel = bookService.deleteBookRecord(bookId);
        ResponseDTO responseDto = new ResponseDTO("Record deleted successfully !", bookModel);
        return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/sortAsc")
    public ResponseEntity<ResponseDTO> getBooksInAscendingOrder() {
        List<BookModel> listOfBooks = bookService.sortedListOfBooksInAscendingOrder();
        ResponseDTO responseDto = new ResponseDTO("Data retrieved successfully :", listOfBooks);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @GetMapping("/sortDesc")
    public ResponseEntity<ResponseDTO> getBooksInDescendingOrder() {
        List<BookModel> listOfBooks = bookService.sortedListOfBooksInDescendingOrder();
        ResponseDTO responseDto = new ResponseDTO("Data retrieved successfully :", listOfBooks);
        return new ResponseEntity(responseDto, HttpStatus.OK);

    }
}
