package com.product.pms.product;

import com.product.pms.data.dto.ProductInfo;
import com.product.pms.data.dto.ProductResponse;
import com.product.pms.model.product.Product;
import com.product.pms.util.DateUtils;

import java.util.List;
import java.util.UUID;

public class ProductMapper {

    public static ProductResponse toProductResponse(List<Product> products) {

        ProductResponse productResponse = new ProductResponse();

        products.forEach(fi -> {
            productResponse.addProductsItem(toProductInfo(fi));
        });

        return productResponse;

    }

    public static ProductInfo toProductInfo(Product product) {

        ProductInfo productInfo = new ProductInfo();

        productInfo.setProductId(UUID.fromString(product.getProductId()));
        productInfo.setName(product.getName());
        productInfo.setCompany(product.getCompany());
        productInfo.setPrice(product.getPrice());
        productInfo.setQuantity(product.getQuantity());
        productInfo.setExpiry(DateUtils.toOffsetDateTime(product.getExpiry()));

        return productInfo;

    }

    private ProductMapper() {

    }

}
