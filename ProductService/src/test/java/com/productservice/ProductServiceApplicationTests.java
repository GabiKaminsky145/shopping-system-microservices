package com.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.Repositories.ProductRepository;
import com.productservice.models.Product;
import com.productservice.models.dto.ProductRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    private static void initProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        mongoDBContainer.start();
    }


    public void init() throws Exception {
        productRepository.deleteAll();
        createProductMockApi("iPhone" , "iPhone", 1200).andExpect(status().isCreated());
    }

    @Test
    public void shouldCreateProduct_Success() throws Exception {

        init();
        List<Product> productList = productRepository.findAll();
        assertEquals(productList.size(), 1);
        assertEquals(productList.get(0).getName(), "iPhone");
    }

    @Test
    public void shouldGetAllProducts_Success() throws Exception {

        init();
        createProductMockApi("Galaxy" , "Galaxy", 2000).andExpect(status().isCreated());

        getAllProductsApi().andExpect(status().isOk());
        List<Product> productList = productRepository.findAll();
        assertEquals(productList.size(), 2);
        assertEquals(productList.get(0).getName(), "iPhone");
        assertEquals(productList.get(1).getName(), "Galaxy");
    }

    @Test
    public void shouldGetProductById_Success() throws Exception {

        init();
        String id = productRepository.findAll().get(0).getId();
        getProductByIdApi(id).andExpect(status().isFound());
    }

    @Test
    public void shouldGetProductById_Fail() throws Exception {

        init();
        getProductByIdApi("2222").andExpect(status().isNotFound());
    }


    private ResultActions createProductMockApi(String name, String description, int price) throws Exception {

        return mockMvc.perform(MockMvcRequestBuilders.post("/api/product/createProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildProductRequest(name, description, BigDecimal.valueOf(price)))));
    }

    private ResultActions getAllProductsApi() throws Exception {

        return mockMvc.perform(MockMvcRequestBuilders.get("/api/product/productsList")
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions getProductByIdApi(String id) throws Exception {

        return mockMvc.perform(MockMvcRequestBuilders.get("/api/product/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ProductRequestDTO buildProductRequest(String name, String description, BigDecimal price) {

        return ProductRequestDTO.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();
    }

}
