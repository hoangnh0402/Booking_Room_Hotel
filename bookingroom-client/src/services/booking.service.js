import HttpService from "./http-service";

class BookingService {
  constructor() {
    this.httpService = new HttpService();
  }

  async getBookingsAdmin(options) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/booking/admin`,
      {
        params: options,
      }
    );
  }

  async getBookings() {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/booking`
    );
  }

  async getBookingsUser() {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/booking/user`
    );
  }

  async createBooking(createBookingDto) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/booking/create`,
      { body: createBookingDto }
    );
  }

  async cancelBooking(bookingId, bookingCancelDTO) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/booking/cancel/${bookingId}`,
      { body: bookingCancelDTO }
    );
  }

  async getBookingById(bookingId) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/booking/${bookingId}`
    );
  }

  async updateBookingById(bookingUpdateDto, bookingId) {
    return await this.httpService.request(
      "PUT",
      `${process.env.REACT_APP_API_URL}/api/v1/booking/update/${bookingId}`,
      { body: bookingUpdateDto }
    );
  }

  async deleteBookingById(bookingId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/booking/delete/${bookingId}`
    );
  }
  async checkoutBookingById(bookingId) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/booking/check-out/${bookingId}`
    );
  }

  async checkinBookingById(bookingId) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/booking/check-in/${bookingId}`
    );
  }
}

export default BookingService;
