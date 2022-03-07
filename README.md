# pms

Product Management Service

## Authnetication
All endpoints are protected by Basic Authentication.

## Actions

- Create Product: Create a new product ( POST /api/v1/products )
- Update Product: Update existing product ( PUT /api/v1/products/{product-id} )
- Delete Product: Delete existing product ( DELETE /api/v1/products/{product-id} )
- Get product list: Get list of all products ( GET /api/v1/products )
- Get Product: Get existing product ( GET /api/v1/products/{product-id} )

## Privileges

- ADMIN: All actions are allowed ( POST, PUT, DELETE, GET )
- USER: Only Get actions are allowed ( GET )
