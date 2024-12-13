import { unwrapResult } from "@reduxjs/toolkit";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import Login from "./pages/Auth/Login";
import { privateRoutes, publicRoutes } from "./routers/router";
import storageService from "./services/storage.service";
import { fetchGetCurrentUser, setuser } from "./store/userSlice/userSlice";

function App() {
  const token = storageService.get("token");
  const dispatch = useDispatch();

  useEffect(() => {
    if (token) {
      (async () => {
        const result = await dispatch(fetchGetCurrentUser())
          .then(unwrapResult)
          .then((originalPromiseResult) => {
            dispatch(setuser(originalPromiseResult.data));
          })
          .catch((rejectedValueOrSerializedError) => {
            console.log(rejectedValueOrSerializedError);
          });
      })();
    }
  }, []);
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/auth/login" element={<Login />} />
          {publicRoutes.map((route, index) => {
            const Layout = route.component;
            return (
              <Route
                key={index}
                path={route.path}
                element={<Layout />}
                // element={<Layout />}
              />
            );
          })}
          {privateRoutes.map((route, index) => {
            const Layout = route.component;
            return (
              <Route
                key={index}
                path={route.path}
                element={token ? <Layout /> : <Navigate to="/auth/login" />}
                // element={<Layout />}
              />
            );
          })}
        </Routes>
      </Router>
    </div>
  );
}

export default App;
