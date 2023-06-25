package com.productservice.Helpers.Mappers;


import com.productservice.models.Product;
import com.productservice.models.dto.ProductRequestDTO;
import com.productservice.models.dto.ProductResponseDTO;
public interface ProductMappers {

    Product map(ProductRequestDTO productRequestDTO);

    ProductResponseDTO map(Product product);

    }
