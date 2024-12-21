package com.project.controllor;

import com.project.controllor.request.CreateProductRequest;
import com.project.controllor.request.UpdateProductRequest;
import com.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

import static com.project.utils.ResponseProcessorUtil.processResult;

@RestController
@RequestMapping("/product")
public class ProductControllor {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        return processResult(productService.createProduct(createProductRequest));
    }

    @PutMapping("/{productReferenceId}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID productReferenceId, @RequestBody UpdateProductRequest updateProductRequest) {
        return processResult(productService.updateProduct(updateProductRequest, productReferenceId));
    }

    @GetMapping("/fetch-all")
    public ResponseEntity<?> fetchAllProducts(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) BigDecimal minPrice,
                                              @RequestParam(required = false) BigDecimal maxPrice,
                                              @RequestParam(required = false) Integer stock,
                                              Pageable pageable) {
        return processResult(productService.fetchAllProduct(name, minPrice, maxPrice, stock, pageable));
    }

    @DeleteMapping("/{productReferenceId}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID productReferenceId) {
        return processResult(productService.deleteProduct(productReferenceId));
    }


}
