package com.project.healthy_life_was.healthy_life.service;

import com.project.healthy_life_was.healthy_life.dto.ResponseDto;
import com.project.healthy_life_was.healthy_life.dto.product.response.ProductDetailResponseDto;
import com.project.healthy_life_was.healthy_life.dto.product.response.ProductListResponseDto;

import java.util.List;

public interface ProductService {
    ResponseDto<List<ProductListResponseDto>> getAllProduct();

    ResponseDto<ProductDetailResponseDto> getPIdProduct(Long pId);
}
