package com.project.healthy_life_was.healthy_life.dto.product.response;

import com.project.healthy_life_was.healthy_life.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDto {

    private Long pId;
    private String pName;
    private int pPrice;
    private String pImgUrl;
    private int averageRating;


    public ProductListResponseDto(Product product, double averageRating) {
        this.pId = product.getPId();
        this.pName = product.getPName();
        this.pPrice = product.getPPrice();
        this.pImgUrl = product.getPImgUrl();
        this.averageRating = (int) averageRating;
    }
}
