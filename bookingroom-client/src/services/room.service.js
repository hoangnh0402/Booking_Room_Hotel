import HttpService from "./http-service";

class RoomService {
  constructor() {
    this.httpService = new HttpService();
  }

  async getAvailableRooms(options) {
    // console.log(expectedCheckIn, expectedCheckOut, num, type);
    let params = {};
    if (options.expectedCheckIn) params["checkin"] = options.expectedCheckIn;
    if (options.expectedCheckOut) params["checkout"] = options.expectedCheckOut;
    if (options.num) params["capacity"] = options.num;
    if (options.type) params["roomType"] = options.type;
    if (options.pageNum) params["pageNum"] = options.pageNum;
    params["pageSize"] = 9;
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/room/available`,
      {
        params: params,
      }
      // ?checkin=${expectedCheckIn}&checkout=${expectedCheckOut}&roomType=${type}&maxNum=${num}
    );
  }

  async getRooms(options) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/room`,
      {
        params: options,
      }
    );
  }

  async createRoom(roomCreateDTO) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/room/create`,
      { body: roomCreateDTO },
      false
    );
  }

  async getRoomById(roomId) {
    return await this.httpService.request(
      "GET",
      // `${process.env.REACT_APP_API_URL}/api/v1/room/${roomId}/room-ratings`
      `${process.env.REACT_APP_API_URL}/api/v1/room/${roomId}`
    );
  }

  async getRatingByRoomId(roomId) {
    return await this.httpService.request(
      "GET",
      `${process.env.REACT_APP_API_URL}/api/v1/room/${roomId}/room-ratings`
    );
  }

  async updateRoomById(roomId, updateRoomDto) {
    return await this.httpService.request(
      "PUT",
      `${process.env.REACT_APP_API_URL}/api/v1/room/update/${roomId}`,
      { body: updateRoomDto },
      false
    );
  }

  async deleteRoomById(roomId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/room/delete/${roomId}`
    );
  }

  async deleteRoomPermanentlyById(roomId) {
    return await this.httpService.request(
      "DELETE",
      `${process.env.REACT_APP_API_URL}/api/v1/room/delete/trash/${roomId}`
    );
  }

  async revertRoomById(roomId) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/room/restore/${roomId}`
    );
  }

  async createRoomRating(createRoomRatingDto, roomId) {
    return await this.httpService.request(
      "POST",
      `${process.env.REACT_APP_API_URL}/api/v1/room-rating/create/${roomId}`,
      { body: createRoomRatingDto }
    );
  }
}

export default RoomService;
