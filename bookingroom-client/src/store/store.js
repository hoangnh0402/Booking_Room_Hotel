import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./authSlice/authSlice";
import userReducer from "./userSlice/userSlice";
import tokenReducer from "./tokenSlice/tokenSlice";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    user: userReducer,
    token: tokenReducer,
  },
});
