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
