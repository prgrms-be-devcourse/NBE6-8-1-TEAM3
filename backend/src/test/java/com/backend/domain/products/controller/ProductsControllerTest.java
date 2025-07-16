package com.backend.domain.products.controller;

import com.backend.api.v1.products.controller.ProductsController;
import com.backend.api.v1.products.entity.Products;
import com.backend.api.v1.products.service.ProductsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductsService productsService;

    @Test
    @DisplayName("상품 다건 조회")
    void t1() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/products")
                )
                .andDo(print());

        List<Products> products = productsService.findAll();

        resultActions
                .andExpect(handler().handlerType(ProductsController.class))
                .andExpect(handler().methodName("getProducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(products.size()));

        for (int i = 0; i < products.size(); i++) {
            Products product = products.get(i);
            resultActions
                    .andExpect(jsonPath("$[%d].id".formatted(i)).value(product.getProductId()))
                    .andExpect(jsonPath("$[%d].productName".formatted(i)).value(Matchers.startsWith(product.getProductName())))
                    .andExpect(jsonPath("$[%d].productDescription".formatted(i)).value(Matchers.startsWith(product.getProductDescription())))
                    .andExpect(jsonPath("$[%d].productPrice".formatted(i)).value(product.getProductPrice()))
                    .andExpect(jsonPath("$[%d].path".formatted(i)).value(product.getPath()));
        }
    }
}
