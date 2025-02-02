package com.project.healthy_life_was.healthy_life.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_Cateogry")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_category_id")
    private Long pCategoryId;

    @Column(name = "p_category_name")
    private Long pCategoryName;

}
