package com.project.healthy_life_was.healthy_life.service.implement;

import com.project.healthy_life_was.healthy_life.common.constant.ResponseMessage;
import com.project.healthy_life_was.healthy_life.dto.ResponseDto;
import com.project.healthy_life_was.healthy_life.dto.cart.CartItemDto;
import com.project.healthy_life_was.healthy_life.dto.cart.request.CartAddRequestDto;
import com.project.healthy_life_was.healthy_life.dto.cart.request.CartUpdateQuantityRequestDto;
import com.project.healthy_life_was.healthy_life.dto.cart.response.CartAddResponseDto;
import com.project.healthy_life_was.healthy_life.dto.cart.response.CartDetailResponseDto;
import com.project.healthy_life_was.healthy_life.dto.cart.response.CartUpdateResponseDto;
import com.project.healthy_life_was.healthy_life.entity.cart.Cart;
import com.project.healthy_life_was.healthy_life.entity.product.Product;
import com.project.healthy_life_was.healthy_life.entity.user.User;
import com.project.healthy_life_was.healthy_life.repository.CartRepository;
import com.project.healthy_life_was.healthy_life.repository.ProductRepository;
import com.project.healthy_life_was.healthy_life.repository.UserRepository;
import com.project.healthy_life_was.healthy_life.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImplement implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseDto<CartAddResponseDto> createCart(String username, Long pId, CartAddRequestDto dto) {
        CartAddResponseDto data = null;
        int quantity = dto.getProductQuantity();


        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "username"));
            Product product = productRepository.findById(pId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "pId"));
        if (quantity > product.getPStockStatus()) {
            return ResponseDto.setFailed(ResponseMessage.PURCHASE_INVENTORY);
        }
            Cart cart = Cart.builder()
                    .product(product)
                    .user(user)
                    .productQuantity(quantity)
                    .productPrice(product.getPPrice() * quantity)
                    .build();
            cartRepository.save(cart);

            data = new CartAddResponseDto(cart);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<CartDetailResponseDto> getCartUser(String username) {
       try {
           List<Cart> carts = cartRepository.findByUser_Username(username);
           List<CartItemDto> cartItems = carts.stream()
                   .map(cart -> new CartItemDto(
                           cart.getCartId(),
                           cart.getProduct().getPId(),
                           cart.getProductQuantity(),
                           cart.getProductPrice(),
                           cart.getProduct().getPImgUrl()
                   ))
                   .toList();
           CartDetailResponseDto cartDetailResponseDto = new CartDetailResponseDto(cartItems);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, cartDetailResponseDto);
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
       }
    }
    @Override
    public ResponseDto<CartUpdateResponseDto> updateCart(String username, Long cartId, CartUpdateQuantityRequestDto dto) {
        CartUpdateResponseDto data = null;
        int quantity = dto.getProductQuantity();
        try {
            Optional<Cart> cartOptional = cartRepository.findById(cartId);
            if(cartOptional.isEmpty()) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA);
            }

            Cart cart = cartOptional.get();

            Product product = productRepository.findById(cart.getProduct().getPId())
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "pId"));
            if (quantity > product.getPStockStatus()) {
                return ResponseDto.setFailed(ResponseMessage.PURCHASE_INVENTORY);
            }
            if(!cart.getUser().getUsername().equals(username)){
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            cart.setProductQuantity(dto.getProductQuantity());
            cart.setProductPrice(dto.getProductQuantity() * cart.getProduct().getPPrice());

            cartRepository.save(cart);
            data = new CartUpdateResponseDto(cart);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<Object> deletePIdCart(String username, Long cartId) {
        try {
            Cart cart = cartRepository.findByUser_UsernameAndCartId(username, cartId);
            if(cart == null) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA);
            }
            cartRepository.deleteById(cartId);
        } catch (Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }

    @Override
    public ResponseDto<Object> deleteCartAll(String username) {
        try {
            List<Cart> carts = cartRepository.findByUser_Username(username);
            if(carts == null) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA);
            }
            cartRepository.deleteAll(carts);
        } catch (Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }
}
