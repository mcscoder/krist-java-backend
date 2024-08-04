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

### Response

```json
{
  "token": "..."
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

### Response

```json
{
  "message": "..."
}
```

## 1.3. Forgot password

- Path: `/auth/forgot`
- Method: `POST`

### Request body

```json
{
  "email": "..."
}
```

### Response

```json
{
  "message": "..."
}
```

## 1.4. OTP authentication

- Path: `/auth/otp`
- Method: `POST`

### Request body

```json
{
  "otp": "..."
}
```

### Response

```json
{
  "message": "..."
}
```

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


