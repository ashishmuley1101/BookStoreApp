package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.repository.IBookRepository;
import com.bridgelabz.bookstore.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService  implements IBookService {

    @Autowired
    private IBookRepository bookRepo;


    @Override
    public BookModel createBook(BookDTO bookDto) {
        BookModel book = new BookModel(bookDto);
        bookRepo.save(book);
        return book;
    }

    @Override
    public List<BookModel> getAllBookData() {
        List<BookModel> getBooks=bookRepo.findAll();
        return getBooks;
    }

    @Override
    public BookModel updateRecordById(BookDTO bookDto, int id) {
        Optional<BookModel> book = bookRepo.findById(id);
        book.get().setAuthorName(bookDto.getAuthorName());
        book.get().setBookDescription(bookDto.getBookDescription());
        book.get().setBookImg(bookDto.getBookImg());
        book.get().setBookName(bookDto.getBookName());
        book.get().setPrice(bookDto.getPrice());
        book.get().setQuantity(bookDto.getQuantity());
        bookRepo.save(book.get());
        return book.get();
    }


    @Override
    public BookModel deleteBookRecord(int id) {
        Optional<BookModel> book = bookRepo.findById(id);
        if (book.isPresent()) {
            bookRepo.deleteById(id);
            return book.get();

        } else {
            return null;     //Order Record doesn't exists
        }
    }
    @Override
    public BookModel getBookModelById(int id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new BookStoreException("Book not found In the List"));
    }
    @Override
    public List<BookModel> sortedListOfBooksInAscendingOrder() {
        List<BookModel> getSortedList=  bookRepo.findAll(Sort.by(Sort.Direction.ASC,"price"));
        return getSortedList;
    }

    @Override
    public List<BookModel> sortedListOfBooksInDescendingOrder() {
        List<BookModel> getSortedListInDesc = bookRepo.findAll(Sort.by(Sort.Direction.DESC,"price"));
        return getSortedListInDesc;

    }

    @Override
    public List<BookModel> getBookModelDataByName(String bookName) {
        return bookRepo.findUserModelDataByEmailId(bookName);
    }

}
