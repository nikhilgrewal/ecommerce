package com.project.utils;

import com.project.controllor.request.CreateProductRequest;
import com.project.controllor.request.UpdateProductRequest;
import com.project.dao.ProductView;

public class ProductMapper {

    public static ProductView convertCreateProductRequestToProductView(CreateProductRequest createProductRequest) {
        return ProductView.builder()
                .name(createProductRequest.getName())
                .stock(createProductRequest.getStock())
                .description(createProductRequest.getDescription())
                .price(createProductRequest.getPrice())
                .build();
    }

    public static ProductView convertUpdateProductRequestToProductView(UpdateProductRequest updateProductRequest) {
        return ProductView.builder()
                .name(updateProductRequest.getName())
                .stock(updateProductRequest.getStock())
                .description(updateProductRequest.getDescription())
                .price(updateProductRequest.getPrice())
                .build();
    }
}
