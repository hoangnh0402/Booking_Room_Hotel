import HttpService from "./http-service";

class PostService {
  constructor() {
    this.httpService = new HttpService();
  }

  async getPostsAdmin(options) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/post/admin`,
      {
        params: options,
      }
    );
  }

  async getPosts() {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/post`
    );
  }

  async createPost(postCreateDto) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/post/create`,
      { body: postCreateDto },
      false
    );
  }

  async getPostById(postId) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/post/${postId}`
    );
  }

  async updatePostById(postId, postUpdateDto) {
    return await this.httpService.request(
      "PUT",
      `${process.env.REACT_APP_API_URL}/api/v1/post/update/${postId}`,
      { body: postUpdateDto },
      false
    );
  }

  async deletePostById(postId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/post/delete/${postId}`
    );
  }

  async revertPostById(postId) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/post/restore/${postId}`
    );
  }

  async deletePermanPostById(postId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/post/delete/trash/${postId}`
    );
  }
}

export default PostService;
