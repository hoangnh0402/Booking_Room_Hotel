import HttpService from "./http-service";

class ProductService {
  constructor() {
    this.httpService = new HttpService();
  }

  async getProductsAdmin(options) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/product/admin`,
      {
        params: options,
      }
    );
  }

  async getProducts() {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/product`
    );
  }

  async createProduct(productCreateDto) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/product/create`,
      { body: productCreateDto },
      false
    );
  }

  async getProductById(productId) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/product/${productId}`
    );
  }

  async updateProductById(productId, productUpdateDto) {
    return await this.httpService.request(
      "PATCH",
      `${process.env.REACT_APP_API_URL}/api/v1/product/update/${productId}`,
      { body: productUpdateDto },
      false
    );
  }

  async deleteProductById(productId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/product/delete/${productId}`
    );
  }

  async revertProductById(productId) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/product/restore/${productId}`
    );
  }

  async deletePermanentlyProductById(productId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/product/delete/trash/${productId}`
    );
  }
}

export default ProductService;
