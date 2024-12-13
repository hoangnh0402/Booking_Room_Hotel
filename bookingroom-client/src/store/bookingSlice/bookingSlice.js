import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import BookingService from "../../services/booking.service";
// import { userAPI } from "./userAPI";

const bookingService = new BookingService();

// First, create the thunk
export const fetchGetBookings = createAsyncThunk(
  "/bookings",
  async (thunkAPI) => {
    try {
      const response = await bookingService.getBookings();
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchGetBookingsAdmin = createAsyncThunk(
  "/bookings",
  async (options, thunkAPI) => {
    try {
      const response = await bookingService.getBookingsAdmin(options);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchGetBookingsUser = createAsyncThunk(
  "/bookings",
  async (thunkAPI) => {
    try {
      const response = await bookingService.getBookingsUser();
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchGetAvailableBookings = createAsyncThunk(
  "/bookings",
  async ({ expectedCheckIn, expectedCheckOut, num, type }, thunkAPI) => {
    // console.log(expectedCheckIn, expectedCheckOut, num, type);

    try {
      const response = await bookingService.getAvailableBookings(
        expectedCheckIn,
        expectedCheckOut,
        num,
        type
      );
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchGetBooking = createAsyncThunk(
  "booking",
  async (bookingId, thunkAPI) => {
    try {
      const response = await bookingService.getBookingById(bookingId);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchCreateBooking = createAsyncThunk(
  "booking",
  async (createBookingDto, thunkAPI) => {
    try {
      const response = await bookingService.createBooking(createBookingDto);
      return response.data;
    } catch (err) {
      return err.response.data;
    }
  }
);

export const fetchCancelBooking = createAsyncThunk(
  "booking",
  async ({ bookingId, cancelBookingDto }, thunkAPI) => {
    try {
      const response = await bookingService.cancelBooking(
        bookingId,
        cancelBookingDto
      );
      return response.data;
    } catch (err) {
      return err.response.data;
    }
  }
);

export const fetchUpdateBooking = createAsyncThunk(
  "booking",
  async ({ bookingId, updateBookingDto }, thunkAPI) => {
    try {
      const response = await bookingService.updateBookingById(
        bookingId,
        updateBookingDto
      );
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchCheckinBookingById = createAsyncThunk(
  "booking",
  async ({ bookingId }, thunkAPI) => {
    try {
      const response = await bookingService.checkinBookingById(bookingId);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchCheckoutBookingById = createAsyncThunk(
  "booking",
  async ({ bookingId }, thunkAPI) => {
    try {
      const response = await bookingService.checkoutBookingById(bookingId);
      return response.data;
    } catch (err) {
      console.log(err);
      return err.response.data;
    }
  }
);

export const fetchDeleteBooking = createAsyncThunk(
  "booking",
  async (bookingId, thunkAPI) => {
    const response = await bookingService.deleteBookingById(bookingId);
    return response.data;
  }
);

const initialState = {
  entities: [],
  loading: "idle",
};

// Then, handle actions in your reducers:
const bookingSlice = createSlice({
  name: "booking",
  initialState,
  reducers: {},
});

export default bookingSlice.reducer;
