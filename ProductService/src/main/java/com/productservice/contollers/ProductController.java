package com.productservice.contollers;


import com.productservice.models.dto.ProductRequestDTO;
import com.productservice.models.dto.ProductResponseDTO;
import com.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping(path = "/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO productRequestDTO){

            productService.createProduct(productRequestDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/productsList")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){

        return new ResponseEntity<>(productService.getAllProduct() ,HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findProduct(@PathVariable String id){

        ProductResponseDTO productResponse = productService.findProductById(id);
        if (Objects.nonNull(productResponse)){
            return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
