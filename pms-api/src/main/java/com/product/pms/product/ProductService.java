package com.product.pms.product;

import com.product.pms.core.ApplicationError;
import com.product.pms.core.ApplicationException;
import com.product.pms.data.dto.ProductInfo;
import com.product.pms.data.dto.ProductRequest;
import com.product.pms.data.dto.ProductResponse;
import com.product.pms.data.dto.UpdateProductRequest;
import com.product.pms.model.product.Product;
import com.product.pms.model.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String createProduct(ProductRequest request) {

        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setName(request.getName());
        product.setCompany(request.getCompany());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setExpiry(request.getExpiry().toLocalDateTime());
        product.setCreationTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());

        product = productRepository.save(product);

        log.debug("Product successfully created");

        return product.getProductId();

    }

    public void updateProduct(String productId, UpdateProductRequest request) {

        Product product = productRepository.findByProductId(productId);

        if (ObjectUtils.isEmpty(product)) {
            log.error("Product with ID {} does not exist", productId);
            throw new ApplicationException(ApplicationError.PRODUCT_NOT_FOUND);
        }

        if (request.getPrice() == null && request.getQuantity() == null && request.getExpiry() == null) {
            log.error("Atleast one parameter should be non-null to update product with ID {}", productId);
            throw new ApplicationException(ApplicationError.BAD_REQUEST);
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());

        }

        if (request.getQuantity() != null) {
            product.setQuantity(request.getQuantity());

        }

        if (request.getExpiry() != null) {
            product.setExpiry(request.getExpiry().toLocalDateTime());
        }

        product.setUpdateTime(LocalDateTime.now());

        productRepository.save(product);

        log.debug("Product successfully updated");

    }

    public void deleteProduct(String productId) {

        Product product = productRepository.findByProductId(productId);

        if (ObjectUtils.isEmpty(product)) {
            log.error("Product with ID {} does not exist", productId);
            throw new ApplicationException(ApplicationError.PRODUCT_NOT_FOUND);
        }

        productRepository.delete(product);

        log.debug("Product successfully deleted");

    }

    public ProductResponse getProducts() {

        List<Product> products = productRepository.findAll();

        if (CollectionUtils.isEmpty(products)) {
            log.error("No Products exist");
            throw new ApplicationException(ApplicationError.NO_PRODUCTS_FOUND);
        }

        List<Product> availableProducts = products.stream()
                .filter(Product::isProductAvailableInInventory)
                .collect(Collectors.toList());

        return ProductMapper.toProductResponse(availableProducts);

    }

    public ProductInfo getProduct(String productId) {

        Product product = productRepository.findByProductId(productId);

        if (ObjectUtils.isEmpty(product)) {
            log.error("Product with ID {} does not exist", productId);
            throw new ApplicationException(ApplicationError.PRODUCT_NOT_FOUND);
        }

        return ProductMapper.toProductInfo(product);

    }

}
