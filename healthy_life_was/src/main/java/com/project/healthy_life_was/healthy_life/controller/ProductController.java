package com.project.healthy_life_was.healthy_life.controller;

import com.project.healthy_life_was.healthy_life.common.constant.ApiMappingPattern;
import com.project.healthy_life_was.healthy_life.dto.ResponseDto;
import com.project.healthy_life_was.healthy_life.dto.product.response.ProductDetailResponseDto;
import com.project.healthy_life_was.healthy_life.dto.product.response.ProductListResponseDto;
import com.project.healthy_life_was.healthy_life.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.PRODUCT)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final String PRODUCT_GET_ALL = "/all";
    private final String PRODUCT_GET_PID = "/{pId}";
    private final String PRODUCT_GET_P_CATEGORY_ID = "/category";
    private final String PRODUCT_GET_CATEGORY_DETAILS = "/category/category-detail";
    private final String PRODUCT_GET_PHYSIQUE_ID = "/{physiqueId}";
    private final String PRODUCT_GET_P_NAME = "/search";

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

    @GetMapping(PRODUCT_GET_P_CATEGORY_ID)
    public ResponseEntity<ResponseDto<List<ProductListResponseDto>>>  getCategoryProduct (@RequestParam String pCategoryName) {
        ResponseDto<List<ProductListResponseDto>> response = productService.getPCategoryProduct(pCategoryName);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(PRODUCT_GET_CATEGORY_DETAILS)
    public ResponseEntity<ResponseDto<List<ProductListResponseDto>>>  getCategoryDetailProduct (@RequestParam String pCategoryDetailName) {
        ResponseDto<List<ProductListResponseDto>> response = productService.getCategoryDetailProduct(pCategoryDetailName);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(PRODUCT_GET_P_NAME)
    public ResponseEntity<ResponseDto<List<ProductListResponseDto>>> getPNameProduct (@RequestParam String pName) {
        ResponseDto<List<ProductListResponseDto>> response = productService.getPNameProduct(pName);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

//    @GetMapping(PRODUCT_GET_PHYSIQUE_ID)
//    public ResponseEntity<ResponseDto<List<ProductListResponseDto>>> getPhysiqueProduct (@AuthenticationPrincipal String username, @PathVariable String physiqueId) {
//        ResponseDto<List<ProductListResponseDto>> response = productService.getPhysiqueProduct(username, physiqueId);
//        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
//        return ResponseEntity.status(status).body(response);
//    }


}
