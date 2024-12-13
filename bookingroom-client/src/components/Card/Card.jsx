import React, { useState } from "react";
import adminFilter from "../../assets/img/admin/adminFilter.png";
import admionSearchWhite from "../../assets/img/admin/admionSearchWhite.png";
import excel from "../../assets/img/admin/excel.png";
import pdf from "../../assets/img/admin/pdf.png";
import printer from "../../assets/img/admin/printer.png";
import eye from "../../assets/img/admin/eye.png";
import edit from "../../assets/img/admin/edit.png";
import adminDelete from "../../assets/img/admin/adminDelete.png";
import lock from "../../assets/img/admin/lock.png";
import Swal from "sweetalert2";
import { Link } from "react-router-dom";
import { MDBIcon } from "mdb-react-ui-kit";
import {
  fetchAddSaleToRoom,
  fetchGetSales,
  fetchRemoveSaleToRoom,
} from "../../store/saleSlice/saleSlice";
import { unwrapResult } from "@reduxjs/toolkit";
import { useDispatch } from "react-redux";
import {
  fetchCheckinBookingById,
  fetchCheckoutBookingById,
} from "../../store/bookingSlice/bookingSlice";
import BillModal from "../Modal/BillModal";
import "./card.scss";

const Card = ({
  option,
  data,
  keys,
  deleteOption,
  revertOption,
  deleteFlag,
  callbackKeyWord,
}) => {
  const dispatch = useDispatch();
  const [displayModal, setDisplayModal] = useState(false);
  const [statusModal, setStatusModal] = useState("");
  const [messageModal, setMessageModal] = useState("");
  const [booking, setBooking] = useState({});
  const [roomsSale, setRoomsSale] = useState([]);
  const [test, setTest] = useState("");
  const [active, setActive] = useState([]);

  const callback = () => {
    setDisplayModal(false);
    setStatusModal("");
    setMessageModal("");
  };

  return (
    <>
      <BillModal
        displayModal={displayModal}
        statusModal={statusModal}
        messageModal={messageModal}
        booking={booking}
        callback={callback}
        // displayStatus="true"
        // message="Cập nhật thành công"
        // status="success"
      />
      <div className="card">
        <div className="card-body">
          <div className="table-top">
            <div className="search-set">
              <div className="search-path">
                <a className="btn btn-filter" id="filter_search">
                  <img src={adminFilter} alt="img" />
                  <span></span>
                </a>
              </div>
              <div className="search-input">
                <a className="btn btn-searchset">
                  <img src={admionSearchWhite} alt="img" />
                </a>
                <div
                  id="DataTables_Table_0_filter"
                  className="dataTables_filter"
                >
                  <label>
                    {" "}
                    <input
                      onChange={(e) => {
                        callbackKeyWord(e.target.value);
                      }}
                      type="search"
                      className="form-control form-control-sm"
                      placeholder="Search..."
                      aria-controls="DataTables_Table_0"
                    />
                  </label>
                </div>
              </div>
            </div>
            <div className="wordset">
              <ul>
                {option == "rooms" && (
                  <>
                    <li>
                      <a
                        style={{ color: "rebeccapurple" }}
                        className="me-3 confirm-text"
                        href="#"
                        onClick={async (e) => {
                          e.preventDefault();
                          console.log(roomsSale);
                          let sales;
                          const func = fetchGetSales({
                            deleteFlag: false,
                          });
                          const result = await dispatch(func)
                            .then(unwrapResult)
                            .then((originalPromiseResult) => {
                              console.log(originalPromiseResult);
                              if (originalPromiseResult.status == "SUCCESS") {
                                sales = originalPromiseResult.data.items;
                              }
                            })
                            .catch((rejectedValueOrSerializedError) => {
                              console.log(rejectedValueOrSerializedError);
                            });
                          let salesObject = {};
                          sales.forEach((sale) => {
                            salesObject[sale.id] = `${sale.salePercent}% - ${
                              sale.dayStart.split("T")[0]
                            } đến ${sale.dayEnd.split("T")[0]}`;
                          });

                          const { value: fruit } = await Swal.fire({
                            title: "Select sales",
                            input: "select",
                            inputOptions: {
                              Sales: salesObject,
                            },
                            inputPlaceholder: "Select sale",
                            showCancelButton: true,
                            inputValidator: async (value) => {
                              if (value) {
                                const result = await dispatch(
                                  fetchAddSaleToRoom({
                                    saleId: value,
                                    roomIds: roomsSale,
                                  })
                                )
                                  .then(unwrapResult)
                                  .then((originalPromiseResult) => {
                                    console.log(originalPromiseResult);
                                    if (
                                      originalPromiseResult.status == "SUCCESS"
                                    ) {
                                      Swal.fire(
                                        "Thêm sale thành công",
                                        "",
                                        "success"
                                      );
                                      setTimeout(() => {
                                        window.location.reload();
                                      }, 1500);
                                    } else {
                                    }
                                  })
                                  .catch((rejectedValueOrSerializedError) => {
                                    console.log(rejectedValueOrSerializedError);
                                  });
                              }
                            },
                          });

                          // if (fruit) {
                          //   Swal.fire(`You selected: ${fruit}`);
                          // }
                        }}
                      >
                        <MDBIcon fas icon="plus-circle" />
                      </a>
                    </li>
                    <li>
                      <a
                        style={{ color: "rebeccapurple" }}
                        className="me-3 confirm-text"
                        href="#"
                        onClick={(e) => {
                          e.preventDefault();
                          Swal.fire({
                            title:
                              "Bạn có chắc chắn xóa khuyến mãi của tất cả các phòng đã chọn?",
                            text: "",
                            icon: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#3085d6",
                            cancelButtonColor: "#d33",
                            confirmButtonText: "Chấp nhận",
                            cancelButtonText: "Hủy bỏ",
                          }).then(async (e) => {
                            if (e.isConfirmed) {
                              const result = await dispatch(
                                fetchRemoveSaleToRoom({
                                  roomIds: roomsSale,
                                })
                              )
                                .then(unwrapResult)
                                .then((originalPromiseResult) => {
                                  if (
                                    originalPromiseResult.status == "SUCCESS"
                                  ) {
                                    Swal.fire("Xóa thành công", "", "success");
                                    setTimeout(() => {
                                      window.location.reload();
                                    }, 1500);
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
                        }}
                      >
                        <MDBIcon fas icon="minus-circle" />
                      </a>
                    </li>
                  </>
                )}
                <li>
                  <a
                    data-bs-toggle="tooltip"
                    data-bs-placement="top"
                    title="pdf"
                  >
                    <img src={pdf} alt="img" />
                  </a>
                </li>
                <li>
                  <a
                    data-bs-toggle="tooltip"
                    data-bs-placement="top"
                    title="excel"
                  >
                    <img src={excel} alt="img" />
                  </a>
                </li>
                <li>
                  <a
                    data-bs-toggle="tooltip"
                    data-bs-placement="top"
                    title="print"
                  >
                    <img src={printer} alt="img" />
                  </a>
                </li>
              </ul>
            </div>
          </div>
          {/* render here */}
          <div className="card" id="filter_inputs">
            <div className="card-body pb-0">
              <div className="row">
                <div className="col-lg-2 col-sm-6 col-12">
                  <div className="form-group">
                    <input type="text" placeholder="Enter Customer Code" />
                  </div>
                </div>
                <div className="col-lg-2 col-sm-6 col-12">
                  <div className="form-group">
                    <input type="text" placeholder="Enter Customer Name" />
                  </div>
                </div>
                <div className="col-lg-2 col-sm-6 col-12">
                  <div className="form-group">
                    <input type="text" placeholder="Enter Phone Number" />
                  </div>
                </div>
                <div className="col-lg-2 col-sm-6 col-12">
                  <div className="form-group">
                    <input type="text" placeholder="Enter Email" />
                  </div>
                </div>
                <div className="col-lg-1 col-sm-6 col-12 ms-auto">
                  <div className="form-group">
                    <a className="btn btn-filters ms-auto">
                      <img src="assets/img/icons/search-whites.svg" alt="img" />
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="table-responsive">
            <table className="table datanew">
              <thead>
                <tr>
                  {option == "rooms" && (
                    <th>
                      <input type="checkbox" />
                    </th>
                  )}
                  {keys && keys.map((key) => <th>{key}</th>)}
                  {option == "bookings" && <th>email</th>}
                  <th style={{ width: "120px" }}>Settings</th>
                </tr>
              </thead>
              <tbody>
                {data &&
                  data.map((item, index) => (
                    <tr
                      // onMouseOver={(e) => console.log("ok")}
                      className={
                        active.includes(index) && option == "rooms"
                          ? "active"
                          : ""
                      }
                    >
                      {option == "rooms" && (
                        <td>
                          <input
                            type="checkbox"
                            onChange={(e) => {
                              if (e.target.checked == true) {
                                active.push(index);
                              } else {
                                active.pop(index);
                              }
                              roomsSale.push(item.id);
                              setRoomsSale(roomsSale);
                              setTest(item.name + e.target.checked);
                            }}
                          />
                        </td>
                      )}
                      {keys && keys.map((key) => <td>{item[key]}</td>)}
                      {option == "bookings" && (
                        <th>{item && item.user?.email}</th>
                      )}

                      <td
                        style={{
                          display: "flex",
                          alignItems: "center",
                        }}
                      >
                        <Link
                          className="me-3"
                          to={"/admin/update/" + option + "/" + item["id"]}
                          style={{ marginRight: "8px" }}
                        ></Link>
                        {option == "rooms" && (
                          <>
                            <a
                              className="me-3 confirm-text"
                              href="#"
                              onClick={async () => {
                                let sales;
                                const func = fetchGetSales({
                                  deleteFlag: false,
                                });
                                const result = await dispatch(func)
                                  .then(unwrapResult)
                                  .then((originalPromiseResult) => {
                                    console.log(originalPromiseResult);
                                    if (
                                      originalPromiseResult.status == "SUCCESS"
                                    ) {
                                      sales = originalPromiseResult.data.items;
                                    }
                                  })
                                  .catch((rejectedValueOrSerializedError) => {
                                    console.log(rejectedValueOrSerializedError);
                                  });
                                let salesObject = {};
                                sales.forEach((sale) => {
                                  salesObject[sale.id] = `${
                                    sale.salePercent
                                  }% - ${sale.dayStart.split("T")[0]} đến ${
                                    sale.dayEnd.split("T")[0]
                                  }`;
                                });

                                const { value: fruit } = await Swal.fire({
                                  title: "Select sales",
                                  input: "select",
                                  inputOptions: {
                                    Sales: salesObject,
                                  },
                                  inputPlaceholder: "Select sale",
                                  showCancelButton: true,
                                  inputValidator: async (value) => {
                                    if (value) {
                                      const result = await dispatch(
                                        fetchAddSaleToRoom({
                                          saleId: value,
                                          roomIds: [item["id"]],
                                        })
                                      )
                                        .then(unwrapResult)
                                        .then((originalPromiseResult) => {
                                          console.log(originalPromiseResult);
                                          if (
                                            originalPromiseResult.status ==
                                            "SUCCESS"
                                          ) {
                                            Swal.fire(
                                              "Thêm sale thành công",
                                              "",
                                              "success"
                                            );
                                          } else {
                                            console.log(
                                              originalPromiseResult.message[
                                                item["id"]
                                              ]
                                            );
                                            Swal.fire(
                                              originalPromiseResult.message[
                                                item["id"]
                                              ],
                                              "",
                                              "error"
                                            );
                                          }
                                        })
                                        .catch(
                                          (rejectedValueOrSerializedError) => {
                                            console.log(
                                              rejectedValueOrSerializedError
                                            );
                                          }
                                        );
                                    }
                                  },
                                });

                                // if (fruit) {
                                //   Swal.fire(`You selected: ${fruit}`);
                                // }
                              }}
                              style={{ marginRight: "12px" }}
                            >
                              <MDBIcon fas icon="plus-circle" />
                            </a>
                            <a
                              className="me-3 confirm-text"
                              href="#"
                              style={{ marginRight: "12px" }}
                              onClick={(e) => {
                                e.preventDefault();
                                Swal.fire({
                                  title: "Bạn có chắc chắn xóa khuyến mãi?",
                                  text: "",
                                  icon: "warning",
                                  showCancelButton: true,
                                  confirmButtonColor: "#3085d6",
                                  cancelButtonColor: "#d33",
                                  confirmButtonText: "Chấp nhận",
                                  cancelButtonText: "Hủy bỏ",
                                }).then(async (e) => {
                                  if (e.isConfirmed) {
                                    const result = await dispatch(
                                      fetchRemoveSaleToRoom({
                                        roomIds: [item["id"]],
                                      })
                                    )
                                      .then(unwrapResult)
                                      .then((originalPromiseResult) => {
                                        if (
                                          originalPromiseResult.status ==
                                          "SUCCESS"
                                        ) {
                                          Swal.fire(
                                            "Xóa thành công",
                                            "",
                                            "success"
                                          );
                                          setTimeout(() => {
                                            window.location.reload();
                                          }, 1500);
                                        } else {
                                          Swal.fire("Lỗi Server", "", "error");
                                        }
                                      })
                                      .catch(
                                        (rejectedValueOrSerializedError) => {
                                          console.log(
                                            rejectedValueOrSerializedError
                                          );
                                          Swal.fire("Lỗi Server", "", "error");
                                        }
                                      );
                                  }
                                });
                              }}
                            >
                              <MDBIcon fas icon="minus-circle" />
                            </a>
                          </>
                        )}
                        {option == "bookings" && (
                          <>
                            {item.status == "PENDING" && (
                              <Link
                                title="Xuât bill"
                                onClick={() => {
                                  Swal.fire({
                                    title: "Bạn muốn check in đặt phòng này",
                                    // showDenyButton: true,
                                    showCancelButton: true,
                                    confirmButtonText: "Save",
                                  }).then(async (result) => {
                                    /* Read more about isConfirmed, isDenied below */
                                    if (result.isConfirmed) {
                                      const result = await dispatch(
                                        fetchCheckinBookingById({
                                          bookingId: item.id,
                                        })
                                      )
                                        .then(unwrapResult)
                                        .then((originalPromiseResult) => {
                                          console.log(originalPromiseResult);
                                          if (
                                            originalPromiseResult.status ==
                                            "SUCCESS"
                                          ) {
                                            Swal.fire(
                                              "Check in thành công",
                                              "",
                                              "success"
                                            );
                                            setTimeout(() => {
                                              window.location.reload();
                                            }, 1500);
                                          } else {
                                            Swal.fire(
                                              originalPromiseResult.message,
                                              "",
                                              "error"
                                            );
                                          }
                                        })
                                        .catch(
                                          (rejectedValueOrSerializedError) => {
                                            console.log(
                                              rejectedValueOrSerializedError
                                            );
                                            Swal.fire(
                                              "Có lỗi xảy ra",
                                              "",
                                              "error"
                                            );
                                          }
                                        );
                                    } else if (result.isDenied) {
                                      Swal.fire(
                                        "Changes are not saved",
                                        "",
                                        "info"
                                      );
                                    }
                                  });
                                }}
                                className="me-3"
                              >
                                <MDBIcon fas icon="sign-in-alt" />
                              </Link>
                            )}

                            {item.status == "CHECKED_IN" && (
                              <Link
                                title="Xuât bill"
                                onClick={() => {
                                  Swal.fire({
                                    title: "Bạn muốn check out đặt phòng này",
                                    // showDenyButton: true,
                                    showCancelButton: true,
                                    confirmButtonText: "Save",
                                  }).then(async (result) => {
                                    /* Read more about isConfirmed, isDenied below */
                                    if (result.isConfirmed) {
                                      const result = await dispatch(
                                        fetchCheckoutBookingById({
                                          bookingId: item.id,
                                        })
                                      )
                                        .then(unwrapResult)
                                        .then((originalPromiseResult) => {
                                          console.log(originalPromiseResult);
                                          if (
                                            originalPromiseResult.status ==
                                            "SUCCESS"
                                          ) {
                                            Swal.fire(
                                              "Check out thành công",
                                              "",
                                              "success"
                                            );
                                            setTimeout(() => {
                                              window.location.reload();
                                            }, 1500);
                                          } else {
                                            Swal.fire(
                                              originalPromiseResult.message,
                                              "",
                                              "error"
                                            );
                                          }
                                        })
                                        .catch(
                                          (rejectedValueOrSerializedError) => {
                                            console.log(
                                              rejectedValueOrSerializedError
                                            );
                                            Swal.fire(
                                              "Có lỗi xảy ra",
                                              "",
                                              "error"
                                            );
                                          }
                                        );
                                    } else if (result.isDenied) {
                                      Swal.fire(
                                        "Changes are not saved",
                                        "",
                                        "info"
                                      );
                                    }
                                  });
                                }}
                                className="me-3"
                              >
                                <MDBIcon fas icon="sign-out-alt" />
                              </Link>
                            )}

                            {item.status == "CHECKED_OUT" && (
                              <Link
                                title="Bill"
                                style={{ marginLeft: "8px" }}
                                onClick={(e) => {
                                  e.preventDefault();
                                  setBooking(item);
                                  setDisplayModal(true);
                                  setStatusModal("success");
                                  setMessageModal("Cập nhật thành công");
                                }}
                              >
                                <MDBIcon fas icon="file-invoice" />
                              </Link>
                            )}
                          </>
                        )}
                        {option != "bookings" && !deleteFlag && (
                          <>
                            <Link
                              className="me-3"
                              to={"/admin/update/" + option + "/" + item["id"]}
                            >
                              <img src={edit} alt="img" />
                            </Link>
                            <a
                              className="me-3 confirm-text"
                              href="#"
                              onClick={() => deleteOption(item["id"])}
                              style={{ marginLeft: "12px" }}
                            >
                              <img src={adminDelete} alt="img" />
                            </a>
                          </>
                        )}
                        {option != "bookings" && deleteFlag && (
                          <>
                            <a
                              className="me-3 confirm-text"
                              href="#"
                              onClick={() => revertOption(item["id"])}
                            >
                              <MDBIcon fas icon="redo" />
                            </a>
                            <a
                              className="me-3 confirm-text"
                              href="#"
                              onClick={() => deleteOption(item["id"])}
                              style={{ marginLeft: "12px" }}
                            >
                              <img src={adminDelete} alt="img" />
                            </a>
                          </>
                        )}
                      </td>
                    </tr>
                  ))}
              </tbody>
            </table>
            {data && data.length == 0 && (
              <div style={{ textAlign: "center", padding: "12px" }}>
                no {option} data
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default Card;
