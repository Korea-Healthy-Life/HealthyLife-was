package com.project.healthy_life_was.healthy_life.service.implement;

import com.project.healthy_life_was.healthy_life.common.constant.ResponseMessage;
import com.project.healthy_life_was.healthy_life.dto.ResponseDto;
import com.project.healthy_life_was.healthy_life.dto.product.response.ProductDetailResponseDto;
import com.project.healthy_life_was.healthy_life.dto.product.response.ProductListResponseDto;
import com.project.healthy_life_was.healthy_life.entity.product.Product;
import com.project.healthy_life_was.healthy_life.repository.ProductRepository;
import com.project.healthy_life_was.healthy_life.repository.ReviewRepository;
import com.project.healthy_life_was.healthy_life.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService {

    public final ProductRepository productRepository;
    public final ReviewRepository reviewRepository;

    @Override
    public ResponseDto<List<ProductListResponseDto>> getAllProduct() {
        List<ProductListResponseDto> data = null;
        try {
            List<Product> products = productRepository.findAll();

            data = products.stream()
                    .map(product -> {
                        double averageRating = reviewRepository.findAverageRatingByProductId(product.getPId());
                        return new ProductListResponseDto(product, averageRating);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<ProductDetailResponseDto> getPIdProduct(Long pId) {
        ProductDetailResponseDto data = null;
        try {
            Optional<Product> optionalProduct = productRepository.findById(pId);
            if (optionalProduct.isEmpty()) {
               return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA);
            }
            Product product = optionalProduct.get();
            double averageRating = reviewRepository.findAverageRatingByProductId(product.getPId());
            data = new ProductDetailResponseDto(product, averageRating);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }
}




