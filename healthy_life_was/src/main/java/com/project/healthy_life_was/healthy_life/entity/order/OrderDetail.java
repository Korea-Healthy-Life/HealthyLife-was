package com.project.healthy_life_was.healthy_life.entity.order;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_detail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id", nullable = false, updatable = false)
    private Long orderDetailId;

}