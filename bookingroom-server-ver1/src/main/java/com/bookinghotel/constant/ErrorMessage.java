package com.bookinghotel.constant;

public class ErrorMessage {

  public static final String UNAUTHORIZED = "Xin lỗi, bạn cần cung cấp thông tin xác thực để truy cập tài nguyên này!";
  public static final String FORBIDDEN = "Xin lỗi, bạn không có quyền truy cập vào tài nguyên này!";
  public static final String FORBIDDEN_UD = "Bạn không có quyền chỉnh sửa hoặc xóa tài nguyên này!";
  public static final String ERR_EXCEPTION_GENERAL = "Có lỗi xảy ra, vui lòng thử lại sau!";

  //error validation dto
  public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "Trường này là bắt buộc!";
  public static final String INVALID_SOME_THING_FIELD = "Dữ liệu không hợp lệ!";
  public static final String INVALID_FORMAT_PASSWORD = "Mật khẩu không đáp ứng yêu cầu!";
  public static final String NOT_BLANK_FIELD = "Không thể để trống!";
  public static final String ERR_INVALID_FILE = "Định dạng tệp không hợp lệ!";
  public static final String INVALID_DATE = "Thời gian không hợp lệ!";
  public static final String INVALID_DATE_CHECK_IN = "Ngày nhận phòng không hợp lệ!";
  public static final String INVALID_DATE_CHECK_OUT = "Ngày trả phòng không hợp lệ!";
  public static final String INVALID_FORMAT_SOME_THING_FIELD = "Định dạng không hợp lệ!";

  public static class Auth {
    public static final String ERR_INCORRECT_AUTHENTICATION = "Tên đăng nhập hoặc mật khẩu không chính xác!";
    public static final String ERR_DUPLICATE_EMAIL = "Email đã được sử dụng!";
    public static final String ERR_DUPLICATE_PHONE = "Số điện thoại đã được sử dụng!";
    public static final String ERR_DUPLICATE_PASSWORD = "Mật khẩu mới phải khác với mật khẩu cũ!";
    public static final String ERR_ACCOUNT_NOT_ENABLED = "Tài khoản này chưa được kích hoạt!";
    public static final String ERR_ACCOUNT_LOCKED = "Tài khoản này đã bị khóa!";

    //error token verification
    public static final String INVALID_TOKEN = "Token không hợp lệ!";
    public static final String EXPIRED_TOKEN = "Token đã hết hạn!";
    public static final String INCORRECT_TOKEN = "Token không chính xác!";
  }

  public static class User {
    public static final String ERR_NOT_FOUND_EMAIL_OR_PHONE = "Không tìm thấy người dùng với email hoặc số điện thoại: %s";
    public static final String ERR_NOT_FOUND_ID = "Không tìm thấy người dùng với id: %s";
    public static final String ERR_ACCOUNT_NOT_FOUND_BY_EMAIL = "Không tìm thấy người dùng với email: %s";
    public static final String ERR_CAN_NOT_UPDATE = "Bạn không thể cập nhật thông tin người dùng khác!";
    public static final String ERR_CAN_NOT_PERMANENTLY_DELETED = "Người dùng không khóa không thể bị xóa vĩnh viễn!";
    public static final String ERR_USER_NOT_ENABLED = "Tài khoản này chưa được kích hoạt!";
    public static final String ERR_USER_IS_LOCKED = "Tài khoản này đã bị khóa!";
    public static final String ERR_USER_IS_NOT_LOCKED = "Tài khoản này chưa bị khóa!";
    public static final String ERR_OLD_PASSWORD_IS_INCORRECT = "Mật khẩu cũ không chính xác!";
    public static final String ERR_NEW_PASSWORD_EQUAL_OLD_PASSWORD = "Mật khẩu mới không được trùng với mật khẩu cũ!";
  }

  public static class Room {
    public static final String ERR_NOT_FOUND_ID = "Không tìm thấy phòng với id: %s";
    public static final String ERR_NOT_FOUND_ID_IN_TRASH = "Không tìm thấy phòng với id %s trong thùng rác!";
    public static final String ERR_NO_PHOTO = "Phòng cần có một bức ảnh mô tả!";
    public static final String ERR_ROOM_UNAVAILABLE = "Xin lỗi, phòng này hiện không khả dụng. Vui lòng chọn phòng khác!";
    public static final String ROOM_HAS_BEEN_DISCOUNTED = "Phòng %s đã được giảm giá";
    public static final String ROOM_NOT_FOR_SALE = "Phòng %s không có mã giảm giá %s";
    public static final String ROOM_NO_SALE = "Phòng %s không có giảm giá";
  }

  public static class RoomRating {
    public static final String ERR_NOT_FOUND_ID = "Không tìm thấy đánh giá với id: %s";
    public static final String ERR_CAN_NOT_UPDATE_OR_DELETED = "Đánh giá phòng này không thuộc sở hữu của bạn và không thể cập nhật hoặc xóa!";
  }

  public static class Sale {
    public static final String ERR_NOT_FOUND_ID = "Không tìm thấy mã giảm giá với id: %s";
    public static final String ERR_NOT_FOUND_ID_IN_TRASH = "Không tìm thấy mã giảm giá với id %s trong thùng rác";
    public static final String ERR_CAN_NOT_PERMANENTLY_DELETED = "Mã giảm giá không có trong thùng rác để có thể bị xóa vĩnh viễn!";
  }

  public static class Service {
    public static final String ERR_NOT_FOUND_ID = "Không tìm thấy dịch vụ với id: %s";
    public static final String ERR_NOT_FOUND_ID_IN_TRASH = "Không tìm thấy dịch vụ với id: %s trong thùng rác";
    public static final String ERR_SERVICE_MUST_HAVE_THUMBNAIL = "Dịch vụ phải có hình ảnh mô tả";
  }

  public static class Product {
    public static final String ERR_NOT_FOUND_ID = "Không tìm thấy sản phẩm với id: %s";
    public static final String ERR_NOT_FOUND_ID_IN_TRASH = "Không tìm thấy sản phẩm với id: %s trong thùng rác";
    public static final String ERR_PRODUCT_MUST_HAVE_THUMBNAIL = "Sản phẩm phải có hình ảnh mô tả!";
  }

  public static class Booking {
    public static final String ERR_NOT_FOUND_ID = "Không tìm thấy booking với id: %s";
    public static final String ERR_NOT_CHECKIN = "Đặt phòng này chưa checkin, không thể trả phòng";
    public static final String ERR_BOOKING_NOT_ROOM = "Phòng trong booking không được bỏ trống";
    public static final String ERR_CAN_NOT_CANCEL_BOOKING = "Bạn chỉ có thể hủy đặt phòng trước một ngày";
    public static final String ERR_NO_PERMISSION_TO_CANCEL_BOOKING = "Booking này không thuộc sở hữu của bạn và không thể được hủy";
  }

  public static class Post {
    public static final String ERR_NOT_FOUND_ID = "Không tìm thấy bài viết với id: %s";
    public static final String ERR_NOT_FOUND_ID_IN_TRASH = "Không tìm thấy bài viết với id %s trong thùng rác";
  }

  public static class Media {
    public static final String ERR_NOT_FOUND_ID = "Không tìm thấy tệp với id: %s";
    public static final String ERR_NOT_FOUND_ID_IN_TRASH = "Không tìm thấy tệp với id: %s trong thùng rác";
  }

}
