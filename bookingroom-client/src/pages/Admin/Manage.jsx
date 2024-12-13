import {
  MDBIcon,
  MDBTabs,
  MDBTabsItem,
  MDBTabsLink,
  MDBTabsContent,
  MDBTabsPane,
} from "mdb-react-ui-kit";
import { Routes, Route, useParams, Link } from "react-router-dom";
import AdminHeader from "../../components/AdminHeader";
import AdminSideBar from "../../components/AdminSideBar";

import adminPlus from "../../assets/img/admin/adminPlus.png";
import { useDispatch } from "react-redux";
import {
  fetchDeletePermanentlyRoom,
  fetchDeleteRoom,
  fetchGetRooms,
  fetchRevertRoomById,
} from "../../store/roomSlice/roomSlice";
import { unwrapResult } from "@reduxjs/toolkit";
import { useEffect, useState } from "react";
import {
  fetchDeleteHotelService,
  fetchDeletePermanentlyHotelService,
  fetchGetHotelServices,
  fetchGetHotelServicesAdmin,
  fetchRevertHotelService,
} from "../../store/hotelServiceSlice/hotelServiceSlice";
import {
  fetchDeletePermanentlySale,
  fetchDeleteSale,
  fetchGetSales,
  fetchRevertSale,
} from "../../store/saleSlice/saleSlice";
import {
  fetchDeleteUser,
  fetchGetUsers,
  fetchLockUnlockUser,
} from "../../store/userSlice/userSlice";
import {
  fetchDeletePermanentlyProduct,
  fetchDeleteProduct,
  fetchGetProducts,
  fetchGetProductsAdmin,
  fetchRevertProduct,
} from "../../store/productSlice/productSlice";
import saleInterface from "../../interfaces/sales.interface";
import roomsInterface from "../../interfaces/rooms.interface";
import usersInterface from "../../interfaces/users.interface";
import servicesInterface from "../../interfaces/service.interface.js";
import productInterface from "../../interfaces/product.interface.js";
import bookingInterface from "../../interfaces/booking.interface.js";
import Swal from "sweetalert2";
import postInterface from "../../interfaces/post.interface.js";
import {
  fetchDeletePermanentlyPost,
  fetchDeletePost,
  fetchGetPosts,
  fetchGetPostsAdmin,
  fetchRevertPost,
} from "../../store/postSlice/postSlice";
import { fetchGetBookingsAdmin } from "../../store/bookingSlice/bookingSlice";
import Card from "../../components/Card/Card";
import BillModal from "../../components/Modal/BillModal";

const UserManage = () => {
  const [iconsActive, setIconsActive] = useState("tab1");
  const [deleteFlag, setDeleteFlag] = useState(false);
  const [pageNum, setPageNum] = useState(1);
  const [pageTotal, setPageTotal] = useState(1);
  const [keyword, setKeyword] = useState("");

  const callbackKeyWord = (keyword) => {
    setKeyword(keyword);
  };

  const handleIconsClick = (value) => {
    if (value === iconsActive) {
      return;
    }
    if (value == "tab1") {
      setDeleteFlag(false);
    } else {
      setDeleteFlag(true);
    }

    setIconsActive(value);
  };

  const dispatch = useDispatch();
  const [keys, setKeys] = useState([]);
  const [data, setData] = useState([]);
  let { option, optionId } = useParams();
  const [checkOption, setCheckOption] = useState(option);

  useEffect(() => {
    console.log(keyword);
    if (checkOption != option) {
      setPageNum(1);
      setCheckOption(option);
    }
    (async () => {
      let func;
      if (option == "rooms") {
        func = fetchGetRooms({ deleteFlag, pageNum, keyword });
        setKeys([...Object.keys(roomsInterface)]);
      } else if (option == "sales") {
        func = fetchGetSales({ deleteFlag, pageNum, keyword });
        setKeys([...Object.keys(saleInterface)]);
      } else if (option == "users") {
        func = fetchGetUsers({ isLocked: deleteFlag, pageNum, keyword });
        setKeys([...Object.keys(usersInterface)]);
      } else if (option == "services") {
        func = fetchGetHotelServicesAdmin({ deleteFlag, pageNum, keyword });
        setKeys([...Object.keys(servicesInterface)]);
      } else if (option == "products") {
        func = fetchGetProductsAdmin({ deleteFlag, pageNum, keyword });
        setKeys([...Object.keys(productInterface)]);
      } else if (option == "posts") {
        func = fetchGetPostsAdmin({ deleteFlag, pageNum, keyword });
        setKeys([...Object.keys(postInterface)]);
      } else if (option == "bookings") {
        func = fetchGetBookingsAdmin({ deleteFlag, pageNum, keyword });
        setKeys([...Object.keys(bookingInterface)]);
      }
      const result = await dispatch(func)
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          console.log(originalPromiseResult);
          if (originalPromiseResult.status == "SUCCESS") {
            let data = originalPromiseResult.data.items;
            if (originalPromiseResult.data.meta.totalPages != 0) {
              setPageTotal(originalPromiseResult.data.meta.totalPages);
            }

            if (option == "rooms") {
              data = data.map((item) => {
                if (item.sale.salePercent) {
                  item["discount"] = item.sale.salePercent + "%";
                }
                return item;
              });
            }
            setData(data);
          } else {
            Swal.fire("Lỗi server", "", "error");
          }
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError);
        });
    })();
  }, [option, deleteFlag, pageNum, keyword]);

  const deleteOption = async (id) => {
    console.log(id);
    let func;
    if (option == "rooms") {
      if (deleteFlag == false) {
        func = fetchDeleteRoom(id);
      } else {
        func = fetchDeletePermanentlyRoom(id);
      }
    } else if (option == "users") {
      if (deleteFlag == false) {
        func = fetchLockUnlockUser({ userId: id, isLocked: true });
      } else {
        func = fetchDeleteUser(id);
      }
    } else if (option == "sales") {
      if (deleteFlag == false) {
        func = fetchDeleteSale(id);
      } else {
        func = fetchDeletePermanentlySale(id);
      }
    } else if (option == "services") {
      if (deleteFlag == false) {
        func = fetchDeleteHotelService(id);
      } else {
        func = fetchDeletePermanentlyHotelService(id);
      }
    } else if (option == "products") {
      if (deleteFlag == false) {
        func = fetchDeleteProduct(id);
      } else {
        func = fetchDeletePermanentlyProduct(id);
      }
    } else if (option == "posts") {
      if (deleteFlag == false) {
        func = fetchDeletePost(id);
      } else {
        func = fetchDeletePermanentlyPost(id);
      }
    }
    Swal.fire({
      title: "Bạn có chắc chắn xóa ?",
      text: "",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Chấp nhận",
      cancelButtonText: "Hủy bỏ",
    }).then(async (e) => {
      if (e.isConfirmed) {
        const result = await dispatch(func)
          .then(unwrapResult)
          .then((originalPromiseResult) => {
            if (originalPromiseResult.status == "SUCCESS") {
              // setData(originalPromiseResult.data.items);
              const newData = data.filter((item) => item.id != id);
              setData(newData);
              // console.log(originalPromiseResult.data.items);
              Swal.fire("Xóa thành công", "", "success");
            } else {
              Swal.fire("Lỗi Server", "", "error");
            }
          })
          .catch((rejectedValueOrSerializedError) => {
            console.log(rejectedValueOrSerializedError);
            Swal.fire("Lỗi Server", "", "error");
          });
      }
    });
  };

  const revertOption = async (id) => {
    let func;
    if (option == "rooms") {
      func = fetchRevertRoomById(id);
    } else if (option == "users") {
      func = fetchLockUnlockUser({ userId: id, isLocked: false });
    } else if (option == "sales") {
      func = fetchRevertSale(id);
    } else if (option == "services") {
      func = fetchRevertHotelService(id);
    } else if (option == "products") {
      func = fetchRevertProduct(id);
    } else if (option == "posts") {
      func = fetchRevertPost(id);
    }
    Swal.fire({
      title: "Bạn có chắc chắn phục hồi ?",
      text: "",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Chấp nhận",
    }).then(async (e) => {
      if (e.isConfirmed) {
        const result = await dispatch(func)
          .then(unwrapResult)
          .then((originalPromiseResult) => {
            console.log("delete", originalPromiseResult);
            Swal.fire("Phục hồi thành công", "", "success");
            const newData = data.filter((item) => item.id != id);
            setData(newData);
          })
          .catch((rejectedValueOrSerializedError) => {
            console.log(rejectedValueOrSerializedError);
            Swal.fire("Có lỗi xảy ra", "", "error");
          });
      }
    });
  };

  return (
    <>
      <div className="main-wrapper">
        <AdminHeader />
        <AdminSideBar option={option} />
        <div className="page-wrapper">
          <div className="content">
            <div className="page-header">
              <div className="page-title">
                <h4>{option.charAt(0).toUpperCase() + option.slice(1)} List</h4>
                <h6>
                  Manage your {option.charAt(0).toUpperCase() + option.slice(1)}
                </h6>
              </div>
              {option != "users" && option != "bookings" && (
                <div className="page-btn">
                  <Link className="btn btn-added" to={"/admin/add/" + option}>
                    <img src={adminPlus} alt="img" />
                    Add {option}
                  </Link>
                </div>
              )}
            </div>
            <>
              <MDBTabs className="mb-3">
                <MDBTabsItem>
                  <MDBTabsLink
                    onClick={() => handleIconsClick("tab1")}
                    active={iconsActive === "tab1"}
                  >
                    <MDBIcon fas icon="database" />{" "}
                    {option.charAt(0).toUpperCase() + option.slice(1)}
                  </MDBTabsLink>
                </MDBTabsItem>
                <MDBTabsItem>
                  <MDBTabsLink
                    onClick={() => handleIconsClick("tab2")}
                    active={iconsActive === "tab2"}
                  >
                    {option == "users" && (
                      <>
                        <MDBIcon fas icon="lock" /> Lock
                      </>
                    )}
                    {option != "users" && option != "bookings" && (
                      <>
                        <MDBIcon fas icon="trash-alt" /> Trash
                      </>
                    )}
                  </MDBTabsLink>
                </MDBTabsItem>
              </MDBTabs>

              <MDBTabsContent>
                <MDBTabsPane show={iconsActive === "tab1"}>
                  <Card
                    option={option}
                    data={data}
                    keys={keys}
                    deleteOption={deleteOption}
                    deleteFlag={deleteFlag}
                    callbackKeyWord={callbackKeyWord}
                  />
                </MDBTabsPane>
                <MDBTabsPane show={iconsActive === "tab2"}>
                  <Card
                    option={option}
                    data={data}
                    keys={keys}
                    deleteOption={deleteOption}
                    revertOption={revertOption}
                    deleteFlag={deleteFlag}
                  />{" "}
                </MDBTabsPane>
                <MDBTabsPane show={iconsActive === "tab3"}>
                  Tab 3 content
                </MDBTabsPane>
              </MDBTabsContent>
            </>
            <div className="col-lg-12">
              <div className="room-pagination">
                {pageNum != 1 && (
                  <a
                    href="#"
                    onClick={(e) => {
                      e.preventDefault();
                      setPageNum(pageNum - 1);
                    }}
                  >
                    <i className="fa fa-long-arrow-left" /> Prev
                  </a>
                )}
                <Link to={"/admin/" + option}>{pageNum}</Link>
                {pageTotal && pageNum != pageTotal && (
                  <a
                    onClick={(e) => {
                      e.preventDefault();
                      setPageNum(pageNum + 1);
                    }}
                    href="#"
                  >
                    Next <i className="fa fa-long-arrow-right" />
                  </a>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default UserManage;
