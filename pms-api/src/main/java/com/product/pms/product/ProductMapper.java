package com.product.pms.product;

import com.product.pms.data.dto.ProductInfo;
import com.product.pms.data.dto.ProductRequest;
import com.product.pms.data.dto.ProductResponse;
import com.product.pms.model.product.Product;
import com.product.pms.util.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(imports= {UUID.class, DateUtils.class, LocalDateTime.class})
public interface ProductMapper {

    default ProductResponse toProductResponse(List<Product> products) {

        ProductResponse productResponse = new ProductResponse();

        products.forEach(fi -> {
            productResponse.addProductsItem(toProductInfo(fi));
        });

        return productResponse;

    }
    @Mapping(target = "productId", expression = "java(UUID.fromString(product.getProductId()))")
    @Mapping(target = "expiry", expression = "java(DateUtils.toOffsetDateTime(product.getExpiry()))")
    ProductInfo toProductInfo(Product product);


    @Mapping(target = "productId", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "expiry", expression = "java(request.getExpiry().toLocalDateTime())")
    @Mapping(target = "updateTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "creationTime", expression = "java(LocalDateTime.now())")
    Product mapProductRequestToProductEntity(ProductRequest request);
}
