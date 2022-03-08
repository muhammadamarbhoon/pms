package com.product.pms.product;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateProductSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/products").content(getProductRequest()).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")).andDo(print()).andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateProductSuccess() throws Exception {
        mockMvc.perform(put("/api/v1/products/5e8bac93-fbfd-46de-85be-58b7ddb30efa").content(getUpdateProductRequest())
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic YWRtaW46YWRtaW4="))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProductSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/products/6fff41ca-a394-4505-8301-91fe9166d144").header("Authorization",
                "Basic YWRtaW46YWRtaW4=")).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProductForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/products/6fff41ca-a394-4505-8301-91fe9166d144").header("Authorization",
                "Basic dXNlcjp1c2Vy")).andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    void testGetProductsSuccess() throws Exception {
        mockMvc.perform(get("/api/v1/products").header("Authorization", "Basic YWRtaW46YWRtaW4=")).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.products").isArray());
    }

    @Test
    void testGetProductSuccess() throws Exception {
        mockMvc.perform(get("/api/v1/products/349a2640-1496-4488-a46f-b8f459b9035a").header("Authorization",
                "Basic dXNlcjp1c2Vy")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product 3"))
                .andExpect(jsonPath("$.company").value("XYZ Pvt Ltd"));
    }

    private String getProductRequest() {
        return "{" + "\"name\":\"Product1\"," + "\"price\":9.99," + "\"quantity\":10,"
                + "\"company\": \"Morgan Pvt Ltd\"," + "\"expiry\": \"2023-03-06T09:00:18.522Z\"" + "}";
    }

    private String getUpdateProductRequest() {
        return "{" + "\"price\":10.99" + "}";
    }

}
