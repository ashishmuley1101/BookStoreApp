package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<OrderModel,Integer> {


    @Query(value = "SELECT * FROM mybookstore.order;", nativeQuery = true)
    List<OrderModel> listOrder();



    @Query(value = "SELECT * FROM order_model  WHERE user_id=:id", nativeQuery = true)
    List<OrderModel> findOrderByUserId(int id);


    @Modifying
    @Transactional
    @Query(value = "UPDATE order_model SET cancel=true WHERE order_id=:id", nativeQuery = true)
    void updateCancel(int id);
}
