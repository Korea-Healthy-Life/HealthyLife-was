package com.project.healthy_life_was.healthy_life.dto.cart.response;

import com.project.healthy_life_was.healthy_life.entity.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateResponseDto {
    private Long cartId;

    private Long pId;

    private String username;

    private int productQuantity;

    private int productPrice;

    private String pImgUrl;

    public CartUpdateResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
        this.pId = cart.getProduct().getPId();
        this.username = cart.getUser().getUsername();
        this.productQuantity = cart.getProductQuantity();
        this.productPrice = cart.getProductPrice();
        this.pImgUrl = cart.getProduct().getPImgUrl();
    }
}
