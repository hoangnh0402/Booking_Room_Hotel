import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import UserService from "../../services/user.service";

const userService = new UserService();

const initialState = {
  value: {},
};

export const fetchGetUsers = createAsyncThunk(
  "/user",
  async (options, thunkAPI) => {
    try {
      const response = await userService.getUsers(options);
      return response.data;
    } catch (err) {
      return err.response.data;
    }
  }
);

export const fetchGetUser = createAsyncThunk(
  "/user",
  async (userId, thunkAPI) => {
    try {
      const response = await userService.getUserById(userId);
      return response.data;
    } catch (err) {
      return err.response.data;
    }
  }
);

export const fetchUpdateUser = createAsyncThunk(
  "/user",
  async ({ userId, updateUserDto }, thunkAPI) => {
    try {
      const response = await userService.updateUserById(userId, updateUserDto);
      return response.data;
    } catch (err) {
      return err.response.data;
    }
  }
);

export const fetchGetCurrentUser = createAsyncThunk(
  "/user",
  async (thunkAPI) => {
    try {
      const response = await userService.getCurrentUser();
      return response.data;
    } catch (err) {
      return err.response.data;
    }
  }
);

export const fetchLockUnlockUser = createAsyncThunk(
  "user",
  async ({ userId, isLocked }, thunkAPI) => {
    console.log(userId, isLocked);
    const response = await userService.lockUnlockUserById(userId, isLocked);
    return response.data;
  }
);

export const fetchDeleteUser = createAsyncThunk(
  "user",
  async (userId, thunkAPI) => {
    const response = await userService.deleteUserById(userId);
    return response.data;
  }
);

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setuser: (state, action) => {
      state.value = { ...action.payload };
    },
  },
});

export const { setuser } = userSlice.actions;

export default userSlice.reducer;
