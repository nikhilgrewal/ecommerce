package com.project.queryservice;

import com.project.dao.ProductView;
import com.project.entity.Product;
import com.project.repository.ProductRepository;
import com.project.utils.DaoToEntityMapper;
import com.project.utils.EntityToDaoMapper;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class ProductQueryService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public String createProduct(ProductView productView) {
        Product product = DaoToEntityMapper.convertProductViewToProduct(productView);
        product.setReferenceId(UUID.randomUUID());
        productRepository.save(product);
        return product.getReferenceId().toString();
    }

    @Transactional
    public Optional<ProductView> updateProduct(ProductView productView) {
        Optional<Product> existingProduct = productRepository.findByReferenceId(productView.getReferenceId());
        if (existingProduct.isEmpty() || existingProduct.get().isDeleted()) {
            return Optional.empty();
        }
        Product product = DaoToEntityMapper.convertProductViewToProduct(productView);
        productRepository.save(product);
        return Optional.of(productView);
    }

    public Page<ProductView> fetchAllProductsWithFilters(String name, BigDecimal minPrice, BigDecimal maxPrice, Integer stock, Pageable pageable) {
        return productRepository.findAllProductsWithFilters(name, minPrice, maxPrice, stock, pageable)
        .map(EntityToDaoMapper::convertProductToProductView);
    }

    public Optional<ProductView> fetchProductByReferenceId(UUID productReferenceId) {
        Optional<Product> existingProduct = productRepository.findByReferenceId(productReferenceId);
        if (existingProduct.isEmpty() || existingProduct.get().isDeleted()) {
            return Optional.empty();
        }
        return Optional.of(EntityToDaoMapper.convertProductToProductView(existingProduct.get()));
    }

    @Transactional
    public Optional<Boolean> softDeleteProduct(ProductView productView) {
        Optional<Product> existingProduct = productRepository.findByReferenceId(productView.getReferenceId());
        if (existingProduct.isEmpty() || existingProduct.get().isDeleted()) {
            return Optional.empty();
        }
        Product product = DaoToEntityMapper.convertProductViewToProduct(productView);
        product.setDeleted(true);
        productRepository.save(product);
        return Optional.of(true);
    }

    public List<ProductView> fetchAllProductWithStock(Integer stock) {
        return productRepository.findAllProductWithStock(stock)
                .stream()
                .map(EntityToDaoMapper::convertProductToProductView)
                .toList();
    }

}
