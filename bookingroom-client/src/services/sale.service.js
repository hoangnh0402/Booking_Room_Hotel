import HttpService from "./http-service";

class SaleService {
  constructor() {
    this.httpService = new HttpService();
  }

  async getSales(options) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/sale`,
      {
        params: options,
      }
    );
  }

  async createSale(saleCreateDto) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/sale/create`,
      { body: saleCreateDto }
    );
  }

  async AddSaleToRoom(saleId, roomIds) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/sale/add/room/${saleId}`,
      { body: roomIds }
    );
  }

  async removeSaleToRoom(roomIds) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/sale/remove/room`,
      { body: roomIds }
    );
  }

  async getSaleById(saleId) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/sale/${saleId}`
    );
  }

  async updateSaleById(saleId, saleUpdateDto) {
    return await this.httpService.request(
      "PUT",
      `${process.env.REACT_APP_API_URL}/api/v1/sale/update/${saleId}`,
      { body: saleUpdateDto }
    );
  }

  async deleteSaleById(saleId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/sale/delete/${saleId}`
    );
  }

  async revertSaleById(saleId) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/sale/restore/${saleId}`
    );
  }

  async deleteSalePermanentlyById(saleId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/sale/delete/trash/${saleId}`
    );
  }
}

export default SaleService;
