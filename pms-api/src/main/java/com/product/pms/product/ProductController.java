package com.product.pms.product;

import com.product.pms.data.dto.ProductInfo;
import com.product.pms.data.dto.ProductRequest;
import com.product.pms.data.dto.ProductResponse;
import com.product.pms.data.dto.UpdateProductRequest;
import com.product.pms.util.JsonUtils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/products")
    public ResponseEntity<Void> createProduct(@RequestBody ProductRequest productRequest) {

        log.debug("Request: POST /api/v1/products");
        log.debug("Request Body: {}", JsonUtils.formatJson(productRequest));

        String productId = productService.createProduct(productRequest);

        String transactionLocation = "/api/v1/products/" + productId;

        log.debug("Location: {}", transactionLocation);

        return ResponseEntity.status(HttpStatus.CREATED).header("Location", transactionLocation).body(null);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/products/{product-id}")
    public ResponseEntity<Void> updateProduct(@PathVariable(value = "product-id") UUID productId,
            @RequestBody UpdateProductRequest updateProductRequest) {

        log.debug("Request: PUT /api/v1/products/{}", productId);
        log.debug("Request Body: {}", JsonUtils.formatJson(updateProductRequest));

        productService.updateProduct(productId.toString(), updateProductRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/products/{product-id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "product-id") UUID productId) {

        log.debug("Request: DELETE /api/v1/products/{}", productId);

        productService.deleteProduct(productId.toString());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/api/v1/products")
    public ResponseEntity<ProductResponse> getProducts() {

        log.debug("Request: GET /api/v1/products");

        ProductResponse response = productService.getProducts();

        log.debug("Response Body: {}", JsonUtils.formatJson(response));

        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/api/v1/products/{product-id}")
    public ResponseEntity<ProductInfo> getProduct(@PathVariable(value = "product-id") UUID productId) {

        log.debug("Request: GET /api/v1/products/{}", productId);

        ProductInfo response = productService.getProduct(productId.toString());

        log.debug("Response Body: {}", JsonUtils.formatJson(response));

        return ResponseEntity.ok(response);

    }

}
