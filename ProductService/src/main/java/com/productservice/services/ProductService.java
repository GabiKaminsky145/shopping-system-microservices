package com.productservice.services;

import com.productservice.Helpers.Mappers.ProductMappers;
import com.productservice.Repositories.ProductRepository;
import com.productservice.models.Product;
import com.productservice.models.dto.ProductRequestDTO;
import com.productservice.models.dto.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMappers productMappers;

    public void createProduct(ProductRequestDTO productRequestDTO){

        Product product = productMappers.map(productRequestDTO);

        productRepository.save(product);
        log.info("product {} created", product.getId());
    }

    public List<ProductResponseDTO> getAllProduct(){

        return productRepository.findAll()
                .stream()
                .map(productMappers::map)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO findProductById(String id){

        Optional<Product> product = productRepository.findById(id);

        return product.map(productMappers::map).orElse(null);

    }



}
