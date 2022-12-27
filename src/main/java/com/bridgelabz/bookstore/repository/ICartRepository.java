package com.bridgelabz.bookstore.repository;


import com.bridgelabz.bookstore.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<CartModel,Integer> {

    @Query(value = "SELECT * FROM cart_model WHERE user_id=:userId AND book_id=:bookId", nativeQuery = true)
    CartModel findCartByUserIdAndBookId(int bookId, int userId);

    @Query(value = "select * from cart_model where user_id=:userId",nativeQuery = true)
    List<CartModel> findByUserId(int userId);
}
