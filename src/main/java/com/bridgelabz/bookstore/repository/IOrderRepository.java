package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<OrderModel,Integer> {


    @Query(value = "SELECT * FROM mybookstore.order;", nativeQuery = true)
    List<OrderModel> listOrder();

//    @Query(value = "SELECT * FROM order_model  WHERE user_id=:id", nativeQuery = true)
//    List<OrderModel> findAllByUserId(int id);
}
