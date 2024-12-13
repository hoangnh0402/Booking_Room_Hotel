import HttpService from "./http-service";

class AuthService {
  constructor() {
    this.httpService = new HttpService();
  }

  async login(authCredential) {
    console.log(authCredential);
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/auth/login`,
      { body: authCredential }
    );
  }

  async register(authCredential) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/auth/signup`,
      { body: authCredential },
      false
    );
  }
}

export default AuthService;
