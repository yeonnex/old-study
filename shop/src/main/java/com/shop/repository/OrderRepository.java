package com.shop.repository;

import com.shop.entity.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("select o from Orders o " + "where o.member.email = :email " + "order by o.orderDate desc")
    List<Orders> findOrders(@Param("email")String email, Pageable pageable);

    @Query("select count(o) from Orders o " + "where o.member.email = :email")
    Long countOrder(@Param("email") String email);
}
