import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import RoomService from "../../services/room.service";
// import { userAPI } from "./userAPI";

const roomService = new RoomService();

// First, create the thunk
export const fetchGetRooms = createAsyncThunk(
  "/rooms",
  async (options, thunkAPI) => {
    try {
      const response = await roomService.getRooms(options);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchGetAvailableRooms = createAsyncThunk(
  "/rooms",
  async (options, thunkAPI) => {
    try {
      const response = await roomService.getAvailableRooms(options);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchGetRoom = createAsyncThunk(
  "room",
  async (roomId, thunkAPI) => {
    try {
      const response = await roomService.getRoomById(roomId);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchRatingByRoomId = createAsyncThunk(
  "room",
  async (roomId, thunkAPI) => {
    try {
      const response = await roomService.getRatingByRoomId(roomId);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchCreateRoom = createAsyncThunk(
  "room",
  async (createRoomDto, thunkAPI) => {
    try {
      const response = await roomService.createRoom(createRoomDto);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchUpdateRoom = createAsyncThunk(
  "room",
  async ({ roomId, updateRoomDto }, thunkAPI) => {
    try {
      const response = await roomService.updateRoomById(roomId, updateRoomDto);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchDeleteRoom = createAsyncThunk(
  "room",
  async (roomId, thunkAPI) => {
    const response = await roomService.deleteRoomById(roomId);
    return response.data;
  }
);

export const fetchDeletePermanentlyRoom = createAsyncThunk(
  "room",
  async (roomId, thunkAPI) => {
    const response = await roomService.deleteRoomPermanentlyById(roomId);
    return response.data;
  }
);

export const fetchRevertRoomById = createAsyncThunk(
  "room",
  async (roomId, thunkAPI) => {
    const response = await roomService.revertRoomById(roomId);
    return response.data;
  }
);

export const fetchPostRoomRating = createAsyncThunk(
  "room",
  async ({ crateRoomRatingDto, roomId }, thunkAPI) => {
    const response = await roomService.createRoomRating(
      crateRoomRatingDto,
      roomId
    );
    return response.data;
  }
);

const initialState = {
  entities: [],
  loading: "idle",
};

// Then, handle actions in your reducers:
const roomSlice = createSlice({
  name: "room",
  initialState,
  reducers: {},
});

export default roomSlice.reducer;
