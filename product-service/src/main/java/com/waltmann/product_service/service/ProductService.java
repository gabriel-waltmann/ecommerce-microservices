package com.waltmann.product_service.service;

import com.waltmann.product_service.dto.ProductRequest;
import com.waltmann.product_service.dto.ProductResponse;
import com.waltmann.product_service.model.Product;
import com.waltmann.product_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void create(ProductRequest productRequest) {
        Product product = Product.builder()
            .name(productRequest.getName())
            .price(productRequest.getPrice())
            .build();

        productRepository.save(product);
        log.info(product.getId() + "Product has been created");
    }

    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
