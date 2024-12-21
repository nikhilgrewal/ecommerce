package com.project.service;

import com.project.controllor.request.CreateProductRequest;
import com.project.controllor.request.UpdateProductRequest;
import com.project.dao.ProductView;
import com.project.queryservice.ProductQueryService;
import com.project.response.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static com.project.response.Response.*;
import static com.project.utils.ProductErrorMessages.PRODUCT_NOT_FOUND;
import static com.project.utils.ProductMapper.*;

@Service
@Log4j2
public class ProductService {

    private final ProductQueryService productQueryService;

    @Autowired
    public ProductService(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    public Map<Response, Object> createProduct(CreateProductRequest createProductRequest) {
        String productReferenceId = productQueryService.createProduct(convertCreateProductRequestToProductView(createProductRequest));
        return Map.of(DATA, productReferenceId);
    }

    public Map<Response, Object> updateProduct(UpdateProductRequest updateProductRequest, UUID productReferenceId) {
        ProductView productView = convertUpdateProductRequestToProductView(updateProductRequest);
        productView.setReferenceId(productReferenceId);

        return productQueryService.updateProduct(productView)
                .<Map<Response, Object>>map(view -> Map.of(DATA, view))
                .orElseGet(() -> Map.of(ERROR, PRODUCT_NOT_FOUND, ERROR_CODE, HttpStatus.NOT_FOUND));
    }

    public Map<Response, Object> fetchAllProduct(String name, BigDecimal minPrice, BigDecimal maxPrice, Integer stock, Pageable pageable) {
        return Map.of(DATA, productQueryService.fetchAllProductsWithFilters(name, minPrice, maxPrice, stock, pageable));
    }

    public Map<Response, Object> deleteProduct(UUID productReferenceId) {
        ProductView productView = ProductView.builder().referenceId(productReferenceId).build();
        return productQueryService.softDeleteProduct(productView)
                .<Map<Response, Object>>map(view -> Map.of(DATA, view))
                .orElseGet(() -> Map.of(ERROR, PRODUCT_NOT_FOUND, ERROR_CODE, HttpStatus.NOT_FOUND));
    }
}
