import React, { useState } from "react";
import { useSelector } from "react-redux";
import logo from "../assets/img/logo.png";
import avatar from "../assets/img/avatar.jpg";
import logout from "../assets/img/admin/log-out.png";
import { Link } from "react-router-dom";
import storageService from "../services/storage.service";

const AdminHeader = () => {
  const [isBlock, setIsBlock] = useState(false);
  const user = useSelector((state) => state.user.value);
  return (
    <div className="header" style={{ backgroundColor: "#F8F9FE" }}>
      <div className="header-left active" style={{ top: "30px" }}>
        <Link className="logo" to="/">
          <img src={logo} alt="" />
        </Link>
        <Link className="logo-small" to="/">
          <img src={logo} alt="" />
        </Link>
      </div>
      <a id="mobile_btn" className="mobile_btn" href="#sidebar">
        <span className="bar-icon">
          <span />
          <span />
          <span />
        </span>
      </a>
      <ul className="nav user-menu" onClick={() => setIsBlock(!isBlock)}>
        <li className="nav-item dropdown has-arrow main-drop">
          <a
            // href="javascript:void(0);"
            className="dropdown-toggle nav-link userset"
            data-bs-toggle="dropdown"
          >
            <span className="user-img">
              <img src={user && user.avatar} alt="" />
              <span className="status online" />
            </span>
          </a>
          <div
            style={{
              display: isBlock == true ? "block" : "none",
              top: "-70%",
              left: "-100%",
            }}
            className="dropdown-menu menu-drop-user"
          >
            <div className="profilename">
              <div className="profileset">
                <span className="user-img">
                  <img src={user && user.avatar} alt="" />
                  <span className="status online" />
                </span>
                <div className="profilesets">
                  <h6>{user?.firstName?.concat(" " + user?.lastName)}</h6>
                  <h5>Admin</h5>
                </div>
              </div>
              <hr className="m-0" />
              <Link to="/user" className="dropdown-item">
                <i className="me-2" data-feather="user" />
                My Profile
              </Link>
              {/* <a className="dropdown-item" href="generalsettings.html">
                <i className="me-2" data-feather="settings" />
                Settings
              </a> */}
              <hr className="m-0" />
              <div
                className="dropdown-item logout pb-0"
                style={{ cursor: "pointer" }}
                onClick={() => {
                  storageService.remove("token");
                  window.location.href = "/auth/login";
                }}
              >
                <img src={logout} className="me-2" alt="img" />
                Logout
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  );
};

export default AdminHeader;
