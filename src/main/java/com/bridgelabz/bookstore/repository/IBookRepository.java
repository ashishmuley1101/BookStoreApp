package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBookRepository extends JpaRepository<BookModel,Integer> {

//    @Query(value = "select * from book_model ORDER BY price ASC,", nativeQuery = true)
//    List<BookModel> getSortedListOfBooksInAsc();

//    @Query(value = "select * from book_model ", nativeQuery = true)
//    List<BookModel> getSortedListOfBooksInDesc();

    @Query(value = "select * from book_model WHERE book_name=:bookName",nativeQuery = true)
    List<BookModel> findUserModelDataByEmailId(String bookName);


}
