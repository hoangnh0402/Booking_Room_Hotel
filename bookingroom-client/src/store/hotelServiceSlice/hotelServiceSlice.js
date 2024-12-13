import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import HotelServiceService from "../../services/hotel-service.service";

// import { userAPI } from "./userAPI";

const hotelService = new HotelServiceService();

export const fetchGetHotelServicesAdmin = createAsyncThunk(
  "/hotels",
  async (options, thunkAPI) => {
    try {
      const response = await hotelService.getServicesAdmin(options);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchGetHotelServices = createAsyncThunk(
  "/hotels",
  async (thunkAPI) => {
    try {
      const response = await hotelService.getServices();
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchGetHotelService = createAsyncThunk(
  "/hotels",
  async (serviceId, thunkAPI) => {
    try {
      const response = await hotelService.getServiceById(serviceId);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchUpdateHotelService = createAsyncThunk(
  "/hotels",
  async ({ serviceId, updateServiceDto }, thunkAPI) => {
    try {
      const response = await hotelService.updateServiceById(
        serviceId,
        updateServiceDto
      );
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchCreateHotelServices = createAsyncThunk(
  "/hotels",
  async (serviceCreateDto, thunkAPI) => {
    try {
      const response = await hotelService.createService(serviceCreateDto);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchDeleteHotelService = createAsyncThunk(
  "sale",
  async (serviceId, thunkAPI) => {
    const response = await hotelService.deleteServiceById(serviceId);
    return response.data;
  }
);

export const fetchRevertHotelService = createAsyncThunk(
  "sale",
  async (serviceId, thunkAPI) => {
    const response = await hotelService.revertServiceById(serviceId);
    return response.data;
  }
);

export const fetchDeletePermanentlyHotelService = createAsyncThunk(
  "sale",
  async (saleId, thunkAPI) => {
    const response = await hotelService.deletePermanentlyServiceById(saleId);
    return response.data;
  }
);

export const fetchGetProductsByService = createAsyncThunk(
  "/hotels",
  async (serviceId, thunkAPI) => {
    try {
      const response = await hotelService.getProductsByServices(serviceId);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

const initialState = {
  entities: [],
  loading: "idle",
};

// Then, handle actions in your reducers:
const hotelSlice = createSlice({
  name: "hotel",
  initialState,
  reducers: {},
  //   extraReducers: (builder) => {
  //     builder.addCase(fetchLogin.fulfilled, (state, action) => {
  //       state.entities.push(action.payload);
  //     });
  //   },
});

export default hotelSlice.reducer;
