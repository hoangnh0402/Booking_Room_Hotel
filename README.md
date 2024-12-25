# PTPM-HDV---20241IT6151004---C6-789---Nhom-3
## Dự án xây dựng Website hỗ trợ đặt phòng khách sạn cho HIT Hotel.
## Mô Tả

Dự án này là bài tập lớn của môn học "Phát Triển Phần Mềm Hướng Dịch vụ". Mục tiêu của dự án là xây dựng một ứng dụng phần mềm với các tính năng cụ thể, áp dụng các nguyên lý và kỹ thuật phát triển website hướng dịch vụ sử dụng Spring Boot và ReactJs.

## Các Tính Năng Chính
### Đối với khách hàng 

- **Xem trang tĩnh**: Khách hàng có quyền xem nội dung trang tĩnh trên website.
- **Xem phòng**: Khách hàng có quyền xem danh sách các phòng trên hệ thống.
- **Xem bài viết**: Khách hàng có quyền xem các bài viết/blogs trên hệ thống.
- **Xem chi tiết đặt phòng**: Khách hàng có quyền xem chi tiết đặt phòng sau khi đặt phòng thành công.
- **Đăng ký**: Khách hàng có thể đăng kí tài khoản người dùng trên hệ thống.
- **Đăng nhập**: Khách hàng có thể đăng nhập vào hệ thống sau khi đã đăng ký thành công.
- **Cập nhật thông tin cá nhân**: Khách hàng có quyền thay đổi thông tin cá nhân.
- **Đặt phòng**: Khách hàng có quyền xem thông tin phòng và tiến hành đặt phòng nếu phòng còn trống.
- **Hủy phòng**: Khách hàng có quyền hủy phòng trước một ngày check-in và cần có lý do hủy phòng.
- **Sử dụng chatbot tự động**: Khách hàng có thể sử dụng chatbot để nhận tư vấn tự động từ hệ thống.
- **Đánh giá**: Khách hàng có thể đánh giá về chất lượng sử dụng sau khi đặt phòng tại hệ thống.

### Đối với quản trị viên

- **Đăng nhập**: Quản trị viên cần đăng nhập vào trang quản lý để thực hiện các chức năng quản trị hệ thống.
- **Thay đổi thông tin cá nhân**: Quản trị viên có thể thay đổi thông tin cá nhân.
- **Bảo trì bài viết**: Quản trị viên có quyền xem, thêm, sửa, và xoá bài viết trên hệ thống.
- **Bảo trì phòng**: Quản trị viên có quyền xem, thêm, sửa, xoá phòng, và quản lý khuyến mãi cho các phòng trên hệ thống.
- **Bảo trì dịch vụ**: Quản trị viên có quyền xem, thêm, sửa, và xoá dịch vụ trên hệ thống.
- **Quản lý người dùng/khách hàng**: Quản trị viên có thể quản lý tài khoản người dùng/khách hàng trên hệ thống, bao gồm khóa và mở khóa tài khoản.
- **Bảo trì sản phẩm**: Quản trị viên có thể thêm, sửa, xoá sản phẩm thích hợp với từng dịch vụ trên hệ thống.
- **Bảo trì khuyến mãi**: Quản trị viên có quyền thêm, sửa, và xoá khuyến mãi trên hệ thống.
- **Quản lý đặt phòng**: Quản trị viên có thể chuyển trạng thái phòng đặt thích hợp và xuất hóa đơn thanh toán khi khách hàng trả phòng.
- **Thống kê**: Thống kê số lượng phòng được đặt trong tháng, Thống kê khách hàng có số lần đặt phòng nhiều nhất, Thống kê doanh thu theo tháng và năm, Thống kê số lượng đặt phòng theo trạng thái.
## Yêu Cầu Hệ Thống

- Hệ điều hành: Windows, Linux, MacOS.
- Ngôn ngữ lập trình: Java, JavaScript, TypeScript.
- Các công nghệ sử dụng trong dự án: Hệ sinh thái Spring Boot, ReactJs.
- Server sử dụng để triển khai hệ thống: - EC2 của Amazon Web Services(AWS) cho phía Backend.
                                         - RDS của Amazon Web Services(AWS) cho phía Database(MySQL).
                                         - Vercel cho phía Frontend(Tích hợp CI/CD).

## Cài Đặt

Hướng dẫn cài đặt dự án trên máy tính của bạn.
- **Bước 1**: Tạo thư mục "Deploy" trên ổ đĩa máy tính cá nhân.
- **Bước 2**: Tải xuống 2 file theo đường dẫn sau trên Google Drive và lưu vào thư mục vừa tạo tại bước 1.
  [Google Drive](https://drive.google.com/drive/folders/19q4bbOXpUuwwY__TniXeKMuTdY-KrPVN?usp=drive_link)
- **Bước 3**: Nếu chưa có Git Bash trên máy tính cá nhân, vui lòng tải về.Nếu có hãy mở Git bash trên thư mục đã tạo ở bước 1 và thực hiện 3 lệnh sau:
   - chmod 400 "roomserver.pem"
   - ssh -i "roomserver.pem" ubuntu@ec2-18-139-2-8.ap-southeast-1.compute.amazonaws.com
   - java -jar ./BookingHotel-1.0.jar
- **Bước 4**: Sau khi hoàn thành bước 3, bạn đã khởi động thành công phần Backend của hệ thống. Sau đó bạn truy cập Website qua đường dẫn sau:
  [HIT Hotel](https://ptpm-hdv-20241-it-6151004-c6-789-nhom-3-tp9x.vercel.app/booking)
###Cuối cùng như hình ảnh dưới đây là bạn đã cài đặt và triển khai thành công hệ thống của chúng tôi!
![Hình ảnh mô tả](https://res.cloudinary.com/dxwu8aqit/image/upload/v1735145887/Screenshot_2024-12-25_235706_ekedj7.png)

## 👩‍💻 Tổng Quan Hệ Thống

Luồng hoạt động của hệ thống được thiết kế theo kiến trúc Layer Architecture, với luồng hoạt động thông qua một chức năng như sau:

1. **Gửi yêu cầu từ Browser/Dashboard**:
    - **Browser/Dashboard (Frontend)**:
        - Người dùng thực hiện thao tác như đặt phòng, xem danh sách booking, hoặc quản lý trạng thái phòng.
        - Thư viện [Axios](https://axios-http.com/) được sử dụng để gửi yêu cầu HTTP từ frontend (Browser hoặc Dashboard) đến API Gateway.

2. **API Gateway trên EC2**:
    - **Vai trò của API Gateway**:
        - Nhận tất cả các yêu cầu từ client (Browser/Dashboard).
        - Định tuyến các yêu cầu này đến các endpoint tương ứng trong ứng dụng Spring Boot trên EC2.
    - **Lợi ích của API Gateway**:
        - Quản lý tập trung tất cả các API.
        - Cung cấp bảo mật, như xác thực JWT, giới hạn lưu lượng (throttling), và chống DDoS.
        - Tạo môi trường kiểm soát, theo dõi log, và thu thập phân tích hiệu suất.
    - **Cách API Gateway hoạt động**:
        - Khi nhận yêu cầu, API Gateway sẽ định tuyến tới một dịch vụ cụ thể trong ứng dụng Spring Boot dựa trên URL hoặc header.

3. **Spring Boot xử lý yêu cầu**:
    - **Routing**:
        - Spring Boot nhận yêu cầu từ API Gateway, chuyển đến các controller tương ứng dựa trên endpoint.
        - Ví dụ: `/api/v1/booking/check-in/{bookingId}` sẽ được xử lý bởi `BookingController`.
    - **Logic ứng dụng**:
        - Spring Boot thực thi logic kinh doanh cần thiết, như xác nhận trạng thái booking, thực hiện check-in/check-out, hoặc xử lý yêu cầu hủy.
    - **Truy xuất dữ liệu từ MySQL (RDS)**:
        - Spring Boot sử dụng JPA/Hibernate để gửi truy vấn SQL đến MySQL trên RDS.
        - Kết nối giữa Spring Boot và RDS sử dụng kết nối bảo mật trong VPC hoặc thông qua IAM Role trên AWS.

4. **Trả về kết quả**:
    - Sau khi xử lý xong yêu cầu:
        - Dữ liệu được trả về từ RDS (nếu có truy vấn DB).
        - Spring Boot trả kết quả về API Gateway dưới dạng JSON.
        - API Gateway chuyển kết quả về client (Browser/Dashboard).

<img loading="lazy" src="./docs/images/system_architecture.svg" alt="Architecture" width="100%" height=600>

## Đóng Góp

Chúng tôi hoan nghênh mọi đóng góp từ cộng đồng để cải thiện dự án này. Dưới đây là hướng dẫn để bạn có thể đóng góp một cách hiệu quả:

### Báo Lỗi

Nếu bạn gặp phải bất kỳ lỗi nào trong quá trình sử dụng dự án, vui lòng tạo một [issue mới](https://github.com/hoangnh0402/PTPM-HDV---20241IT6151004---C6-789---Nhom-3/issues) và cung cấp thông tin chi tiết về lỗi đó. Bao gồm:

- Mô tả lỗi.
- Các bước để tái tạo lỗi.
- Môi trường (hệ điều hành, phiên bản phần mềm, v.v.).
- Ảnh chụp màn hình (nếu có).

### Đề Xuất Tính Năng

Nếu bạn có ý tưởng mới về tính năng hoặc cải tiến, vui lòng tạo một [issue mới](https://github.com/hoangnh0402/PTPM-HDV---20241IT6151004---C6-789---Nhom-3/issues) và mô tả chi tiết ý tưởng của bạn.

### Đóng Góp Mã Nguồn

Để đóng góp mã nguồn, vui lòng làm theo các bước sau:

1. **Fork Repository**: Fork repository này về tài khoản GitHub của bạn.

2. **Tạo Nhánh Mới**: Tạo một nhánh mới từ nhánh `main` để thực hiện các thay đổi của bạn.

    ```bash
    git checkout -b feature/TenTinhNang
    ```

3. **Thực Hiện Thay Đổi**: Thực hiện các thay đổi và commit với thông điệp rõ ràng.

    ```bash
    git commit -m 'Mô tả ngắn gọn về thay đổi của bạn'
    ```

4. **Push Nhánh**: Push nhánh của bạn lên GitHub.

    ```bash
    git push origin feature/TenTinhNang
    ```

5. **Tạo Pull Request**: Tạo một pull request từ nhánh của bạn vào nhánh `main` của repository gốc. Trong phần mô tả pull request, vui lòng cung cấp thông tin chi tiết về các thay đổi của bạn.

### Hướng Dẫn Viết Commit Message

- Sử dụng ngôn ngữ tiếng Anh.
- Cung cấp mô tả ngắn gọn nhưng đầy đủ.
- Sử dụng thì hiện tại (present tense).

### Quy Tắc Lập Trình

- Tuân thủ theo các quy tắc lập trình và phong cách mã nguồn của dự án.
- Viết code sạch, dễ hiểu và dễ bảo trì.
- Đảm bảo rằng tất cả các thay đổi đều được kiểm tra kỹ lưỡng trước khi tạo pull request.

### Liên Hệ

Nếu bạn có bất kỳ câu hỏi nào về việc đóng góp, vui lòng liên hệ với chúng tôi qua email: [nguyenhuyhoangpt0402@gmail.com].

Cảm ơn bạn đã quan tâm và đóng góp vào dự án của chúng tôi!
