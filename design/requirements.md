# Watch Luxury requirements

## Technologies

    Platform: Android (min-sdk: 21)  
    Client: Retrofit + Mochi  
    Backend: Django + Sqlite  
    UI: Compose  

---------------------------------------

## User stories

- Người dùng có thể đăng ký và đăng nhập vào một tài khoản
- Người dùng có thể tìm kiếm một sản phẩm theo tên
- Người dùng có thể xem thông tin sản phẩm
- Người dùng thêm sản phẩm vào giỏ hàng (từ trang thông tin sản phẩm)
- Người dùng xóa sản phẩm từ giỏ hàng (từ trang giỏ hàng)
- Người dùng thêm/bỏ yêu thích sản phẩm (từ trang thông tin sản phẩm)
- Người dùng xem thông tin cá nhân
- Người dùng chỉnh sửa thông tin cá nhân
- Người dùng đăng xuất khỏi tài khoản

### Version 1.0

- Người dùng đăng nhập bằng Google
- Người dùng reset mật khẩu
- Người dùng lọc kết quả tìm kiếm theo tên, giá, lượt yêu thích

---------------------------------------

### Version 1.1

- Vòng quay may mắn
- Hệ thống coupon

---------------------------------------

## Backend

- Model: User, Product, Order
  
### Public gateways

|URI            |Method |Action              |
|---------------|-------|--------------------|
|/users         |GET    |Lấy tất cả tài khoản|
|/users         |POST   |Tạo tài khoản mới   |
|/users/{id}    |GET    |Lấy 1 tài khoản     |
|/users/{id}    |PUT    |Cập nhật 1 tài khoản|
|/products      |GET    |Lấy tất cả item     |
|/products/{id} |GET    |Lấy 1 item          |
