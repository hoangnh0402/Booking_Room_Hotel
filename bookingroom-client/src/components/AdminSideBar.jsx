import React, { useState } from "react";
import { Link } from "react-router-dom";
import adminDashboard from "../assets/img/admin/adminDashboard.png";

const AdminSIdeBar = ({ option }) => {
  // const [show, setShow] = useState(false);

  const sideBars = [
    {
      path: "/admin",
      name: "Statistic",
    },
    {
      path: "/admin/users",
      name: "Manage Users",
    },
    {
      path: "/admin/rooms",
      name: "Manage Rooms",
    },
    {
      path: "/admin/sales",
      name: "Manage Sales",
    },
    {
      path: "/admin/services",
      name: "Manage Services",
    },
    {
      path: "/admin/products",
      name: "Manage Products",
    },
    {
      path: "/admin/posts",
      name: "Manage Posts",
    },
    {
      path: "/admin/bookings",
      name: "Manage Bookings",
    },
  ];

  // console.log(option.charAt(0).toUpperCase() + option.slice(1));

  return (
    <>
      <div className="sidebar" id="sidebar">
        <div
          className="slimScrollDiv"
          style={{
            position: "relative",
            overflow: "hidden",
            width: "100%",
            height: "672px",
          }}
        >
          <div
            className="sidebar-inner slimscroll"
            style={{ overflow: "hidden", width: "100%", height: "672px" }}
          >
            <div id="sidebar-menu" className="sidebar-menu">
              <ul>
                <li className="submenu">
                  <a className="active">
                    <img src={adminDashboard} alt="img" />
                    <span> Dashboards</span> <span className="menu-arrow" />
                  </a>
                  <ul style={{ display: "block" }}>
                    {sideBars.map((sideBar, index) => (
                      <li key={index} style={{ padding: "12px" }}>
                        <Link
                          className={
                            (sideBar.name.split(" ")[1] ==
                            option.charAt(0).toUpperCase() + option.slice(1)
                              ? "active"
                              : "") ||
                            (sideBar.name == "Statistic" &&
                            sideBar.path == "/admin"
                              ? "active"
                              : "")
                          }
                          to={sideBar.path}
                        >
                          {sideBar.name}
                        </Link>
                      </li>
                    ))}
                  </ul>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default AdminSIdeBar;
