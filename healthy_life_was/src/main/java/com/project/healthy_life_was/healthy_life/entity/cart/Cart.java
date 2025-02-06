package com.project.healthy_life_was.healthy_life.entity.cart;

import com.project.healthy_life_was.healthy_life.entity.product.Product;
import com.project.healthy_life_was.healthy_life.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false, updatable = false)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "p_id", nullable = false)
    private Product product;

    @Column(name = "product_quantity", nullable =false)
    @Builder.Default
    private int productQuantity = 1;

    @Column(name = "product_price", nullable = false)
    private int productPrice;

}
