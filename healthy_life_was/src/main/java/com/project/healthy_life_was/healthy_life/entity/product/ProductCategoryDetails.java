package com.project.healthy_life_was.healthy_life.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_Cateogry_detail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_category_details_id")
    private Long pCategoryDetailsId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "p_id", nullable = false)
//    @JsonIgnore
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "p_category_id", nullable = false)
//    @JsonIgnore
    private ProductCategory productCategory;

    @Column(name = "p_category_name", nullable = false)
    private String pCategoryDetailName;

}
