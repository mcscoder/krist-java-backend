# Project Endpoints

# 1. Authentication

## 1.1. Login

- Path: `/auth/login`
- Method: `POST`

### Request body

```json
{
  "email": "...",
  "password": "..."
}
```

## 1.2. Register

- Path: `/auth/register`
- Method: `POST`

### Request body

```json
{
  "firstName": "...",
  "lastName": "...",
  "email": "...",
  "password": "..."
}
```

## 1.3. Forgot password

- Path: `/auth/forgot/{email}`
- Method: `POST`

## 1.4. OTP authentication

- Path: `/auth/otp/{email}/{otp}`
- Method: `POST`

## 1.5. Reset password'

- Path: `/reset-password/{token}`
- Method: `POST`

# 2. User profile

## 2.1. Personal information

- Path: `/profile/information`
- Method: `GET`
- Authentication: `JWT`

### Response

- Personal information

## 2.2. My orders

- Path: `/profile/orders`
- Method: `GET`
- Authentication: `JWT`

### Response

- List of orders

## 2.3. My wishlists

- Path: `/profile/wishlists`
- Method: `GET`
- Authentication: `JWT`

### Response

- List of products

## 2.4. Manage addresses

- Path: `/profile/addresses`
- Method: `GET`
- Authentication: `JWT`

### Response

- List of shipping addresses

## 2.5. Saved cards

- Path: `/profile/cards`
- Method: `GET`
- Authentication: `JWT`

### Response

- List of saved cards

## 2.6. Notifications

- Path: `/profile/notifications`
- Method: `GET`
- Authentication: `JWT`

### Response

- List of notifications

## 2.7. Settings

- Path: `/profile/settings`
- Method: `GET`
- Authentication: `JWT`

### Response

- User settings

# 3. Product

## 3.1. Attribute

- Path:

  - `/public/attribute`
  - `/public/attributes`

- Method: `POST` `GET`

- `POST` body:

```json
{
  "name": "..."
}
```

## 3.2. Attribute value

- Path:

  - `/public/attribute-value`
  - `/public/attribute-values`

- Method: `POST` `GET`

- `POST` body:

```json
{
  "name": "...",
  "attributeId": 0
}
```

## 3.3. Category group

- Path:

  - `/public/category-group`
  - `/public/category-groups`

- Method: `POST` `GET`

- `POST` body:

```json
{
  "name": "...",
  "imageId": 0
}
```

## 3.4. Category

- Path:

  - `/public/category`
  - `/public/categories`

- Method: `POST` `GET`

- `POST` body:

```json
{
  "name": "...",
  "categoryGroupId": 0
}
```

## 3.5. Product

- Path:

  - `/public/product`
  - `/public/products`

- Method: `POST` `GET`

- `POST` body:

```json
{
  "title": "...",
  "name": "...",
  "description": "...",
  "imageIds": [0, 1],
  "categoryIds": [0, 1]
}
```

## 3.6. Product variant

- Path:

  - `/public/product-variant`
  - `/public/product-variants`

- Method: `POST` `GET`

- `POST` body:

```json
{
  "price": 0,
  "quantity": 0,
  "productId": 0,
  "attributeValueIds": [0, 1]
}
```

