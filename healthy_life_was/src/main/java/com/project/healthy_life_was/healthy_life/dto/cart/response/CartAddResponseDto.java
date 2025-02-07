package com.project.healthy_life_was.healthy_life.dto.cart.response;

import com.project.healthy_life_was.healthy_life.entity.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartAddResponseDto {

    private Long cartId;

    private Long pId;

    private int productQuantity;

    private int productPrice;

    private String pImgUrl;

    public CartAddResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
        this.pId = cart.getProduct().getPId();
        this.productQuantity = cart.getProductQuantity();
        this.productPrice = cart.getProductPrice();
        this.pImgUrl = cart.getProduct().getPImgUrl();
    }
}
