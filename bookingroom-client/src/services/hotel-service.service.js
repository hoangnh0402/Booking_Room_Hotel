import HttpService from "./http-service";

class HotelServiceService {
  constructor() {
    this.httpService = new HttpService();
  }

  async getServicesAdmin(options) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/service/admin`,
      {
        params: options,
      }
    );
  }

  async getServices(options) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/service`,
      {
        params: options,
      }
    );
  }

  async createService(serviceCreateDto) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/service/create`,
      { body: serviceCreateDto },
      false
    );
  }

  async getServiceById(serviceId) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/service/${serviceId}`
    );
  }

  async updateServiceById(serviceId, updateServiceDto) {
    return await this.httpService.request(
      "PUT",
      `${process.env.REACT_APP_API_URL}/api/v1/service/update/${serviceId}`,
      { body: updateServiceDto },
      false
    );
  }

  async deleteServiceById(serviceId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/service/delete/${serviceId}`
    );
  }

  async revertServiceById(serviceId) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/service/restore/${serviceId}`
    );
  }

  async deletePermanentlyServiceById(serviceId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/service/delete/trash${serviceId}`
    );
  }

  async getProductsByServices(serviceId) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/service/${serviceId}/products`
    );
  }
}

export default HotelServiceService;
