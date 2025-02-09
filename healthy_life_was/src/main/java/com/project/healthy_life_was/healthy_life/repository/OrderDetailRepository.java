package com.project.healthy_life_was.healthy_life.repository;

import com.project.healthy_life_was.healthy_life.entity.order.Order;
import com.project.healthy_life_was.healthy_life.entity.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("""
    SELECT od
    FROM OrderDetail od
    WHERE od.order.user.username = :username
    OR od.order.orderDate BETWEEN :startOrderDate AND :endOrderDate
""")
    List<OrderDetail> findAllByUser_usernameAndStartAndEnd(
            @Param("username") String username,
            @Param("startOrderDate") Date startOrderDate,
            @Param("endOrderDate") Date endOrderDate
    );

    Optional<OrderDetail> findByOrder(Order order);
}
