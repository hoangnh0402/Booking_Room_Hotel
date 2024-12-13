import Title from "../../components/Title";
import Header from "../../components/Header";

import flag from "../../assets/img/flag.jpg";
import room1 from "../../assets/img/room/room-1.jpg";
import room2 from "../../assets/img/room/room-2.jpg";
import room3 from "../../assets/img/room/room-3.jpg";
import room4 from "../../assets/img/room/room-4.jpg";
import room5 from "../../assets/img/room/room-5.jpg";
import room6 from "../../assets/img/room/room-6.jpg";
import { useDispatch } from "react-redux";
import {
  fetchGetRooms,
  fetchGetAvailableRooms,
} from "../../store/roomSlice/roomSlice";
import { unwrapResult } from "@reduxjs/toolkit";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./room.scss";
import Swal from "sweetalert2";

const Rooms = ({
  expectedCheckIn,
  expectedCheckOut,
  num,
  type,
  roomCallBack,
}) => {
  const dispatch = useDispatch();
  const [rooms, setRooms] = useState([]);
  const [pageNum, setPageNum] = useState(1);
  const [pageTotal, setPageTotal] = useState(1);

  // console.log({ expectedCheckIn, expectedCheckOut, num, type });
  const transferDateTimeToTime = (dateTime) => {
    const time = dateTime.split("T")[0];
    return time;
  };

  let checkValidTime = (dayStart, dayEnd) => {
    return (
      new Date(dayStart).getTime() <= Date.now() &&
      new Date(dayEnd).getTime() >= Date.now()
    );
  };

  useEffect(() => {
    (async () => {
      const result = await dispatch(
        fetchGetAvailableRooms({
          expectedCheckIn: transferDateTimeToTime(expectedCheckIn),
          expectedCheckOut: transferDateTimeToTime(expectedCheckOut),
          num,
          type,
          pageNum,
        })
      )
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          if (originalPromiseResult.status == "SUCCESS") {
            if (originalPromiseResult.data.meta.totalPages != 0) {
              setPageTotal(originalPromiseResult.data.meta.totalPages);
            }
            setRooms(originalPromiseResult.data.items);
          } else {
            Swal.fire("Lỗi server", "", "error");
          }
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError);
        });
    })();
    transferDateTimeToTime(expectedCheckIn);
    // console.log()
    // console.log(expectedCheckIn, expectedCheckOut, num, type);
  }, [expectedCheckIn, expectedCheckOut, num, type, pageNum]);

  return (
    <div>
      <section className="rooms-section spad">
        <div className="">
          {rooms &&
            rooms.map((room) => {
              return (
                <div key={room.id} className="col-lg-12 col-md-6">
                  <div className="room-item" style={{ display: "flex" }}>
                    <div style={{ width: "40%" }}>
                      <img
                        style={{
                          width: "280px",
                          height: "380px",
                          objectFit: "cover",
                        }}
                        src={room?.medias?.[0]?.url}
                        alt=""
                      />
                    </div>
                    <div
                      style={{ width: "60%", position: "relative" }}
                      className="ri-text"
                    >
                      <h4>{room.name}</h4>
                      <div style={{ display: "flex" }}>
                        <h3
                          style={{
                            color: checkValidTime(
                              room.sale?.dayStart,
                              room.sale?.dayEnd
                            )
                              ? "rgba(0,0,0,.54)"
                              : "#5892b5",
                            textDecoration: checkValidTime(
                              room.sale?.dayStart,
                              room.sale?.dayEnd
                            )
                              ? "line-through"
                              : "none",
                          }}
                        >
                          {room &&
                            room?.price?.toLocaleString("it-IT", {
                              style: "currency",
                              currency: "VND",
                            })}
                        </h3>
                        {checkValidTime(
                          room.sale?.dayStart,
                          room.sale?.dayEnd
                        ) && (
                          <h3 style={{ marginLeft: "12px" }}>
                            {room &&
                              (
                                room.price -
                                (room.price * room.sale.salePercent) / 100
                              ).toLocaleString("it-IT", {
                                style: "currency",
                                currency: "VND",
                              })}
                          </h3>
                        )}
                      </div>
                      {room.sale?.salePercent != null &&
                        checkValidTime(
                          room.sale?.dayStart,
                          room.sale?.dayEnd
                        ) && (
                          <h4
                            style={{
                              position: "absolute",
                              top: "12px",
                              right: "12px",
                              fontSize: "16px",
                              color: "#ee4d2d",
                              fontWeight: 400,
                            }}
                          >
                            Giảm giá {room.sale.salePercent} %
                          </h4>
                        )}
                      <table>
                        <tbody>
                          <tr>
                            <td className="r-o">Type:</td>
                            <td>{room.type}</td>
                          </tr>
                          <tr>
                            <td className="r-o">Capacity:</td>
                            <td>Max persion {room.capacity}</td>
                          </tr>
                          <tr>
                            <td className="r-o">Bed:</td>
                            <td>{room.bed}</td>
                          </tr>
                          <tr>
                            <td className="r-o">Size:</td>
                            <td>{room.size}</td>
                          </tr>
                        </tbody>
                      </table>
                      <Link
                        className="primary-btn"
                        to={"/room-detail/" + room.id}
                      >
                        More Details
                      </Link>
                      {!room.isAvailable && (
                        <div
                          style={{ marginLeft: "8px", color: "red" }}
                          className="primary-btn"
                        >
                          Booked
                        </div>
                      )}
                      <div style={{ marginTop: "30px" }}></div>
                      {room.isAvailable && (
                        <button
                          className="btn btn-link p-0 btn-add-room"
                          onClick={(e) => {
                            roomCallBack(room);
                          }}
                          style={{
                            color: "#848492",
                            height: "40px",
                            width: "200px",
                          }}
                        >
                          Add to my booking
                        </button>
                      )}
                    </div>
                  </div>
                </div>
              );
            })}
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
              <Link to="/booking">{pageNum}</Link>
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
      </section>
      {/* Rooms Section End */}
    </div>
  );
};

export default Rooms;
