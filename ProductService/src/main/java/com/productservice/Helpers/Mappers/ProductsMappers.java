package com.productservice.Helpers.Mappers;

import com.productservice.models.Product;
import com.productservice.models.dto.ProductRequestDTO;
import com.productservice.models.dto.ProductResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductsMappers implements ProductMappers{

    @Override
    public Product map(ProductRequestDTO productRequestDTO){

        return Product.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .build();
    }

    @Override
    public ProductResponseDTO map(Product product){

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
