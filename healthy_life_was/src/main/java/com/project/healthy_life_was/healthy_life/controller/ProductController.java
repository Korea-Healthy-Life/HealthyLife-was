package com.project.healthy_life_was.healthy_life.controller;

import com.project.healthy_life_was.healthy_life.common.constant.ApiMappingPattern;
import com.project.healthy_life_was.healthy_life.dto.ResponseDto;
import com.project.healthy_life_was.healthy_life.dto.product.response.ProductDetailResponseDto;
import com.project.healthy_life_was.healthy_life.dto.product.response.ProductListResponseDto;
import com.project.healthy_life_was.healthy_life.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.PRODUCT)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final String PRODUCT_GET_ALL = "/all";
    private final String PRODUCT_GET_PID = "/{pId}";
    private final String PRODUCT_GET_P_CATEGORY_ID = "/{pCategoryId}";
    private final String PRODUCT_GET_CATEGORY_DETAILS = "/{CategoryDetailId}";
    private final String PRODUCT_GET_PHYSIQUE_ID = "/{physiqueId}";

    @GetMapping(PRODUCT_GET_ALL)
    public ResponseEntity<ResponseDto<List<ProductListResponseDto>>> getAllProduct () {
        ResponseDto<List<ProductListResponseDto>> response = productService.getAllProduct();
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(PRODUCT_GET_PID)
    public ResponseEntity<ResponseDto<ProductDetailResponseDto>> getPIdProduct (@PathVariable Long pId) {
        ResponseDto<ProductDetailResponseDto> response = productService.getPIdProduct(pId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }
}
