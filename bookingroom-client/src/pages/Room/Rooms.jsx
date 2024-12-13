import Title from "../../components/Title";
import Header from "../../components/Header";
import OwlCarousel from "react-owl-carousel";

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
import Swal from "sweetalert2";

const Rooms = () => {
  const dispatch = useDispatch();
  const [rooms, setRooms] = useState([]);
  const [pageNum, setPageNum] = useState(1);
  const [pageTotal, setPageTotal] = useState(1);

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
          pageNum: pageNum,
        })
      )
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          if (originalPromiseResult.status == "SUCCESS") {
            setRooms(originalPromiseResult.data.items);
            setPageTotal(originalPromiseResult.data.meta.totalPages);
            console.log(originalPromiseResult);
          } else {
            Swal.fire("Lỗi Server", "", "error");
          }
          // handle result here
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError);
          // handle result here
        });
    })();

    // return () => {}; // no-op
  }, [pageNum]);

  return (
    <div>
      <Header />
      {Title("Our Rooms", "Rooms")}
      {/* Breadcrumb Section End */}
      {/* Rooms Section Begin */}
      <section className="rooms-section spad">
        <div className="container">
          <div className="row">
            {rooms &&
              rooms.map((room) => {
                return (
                  <div key={room.id} className="col-lg-4 col-md-6">
                    <div
                      style={{ boxShadow: "0 0 10px #8888" }}
                      className="room-item"
                    >
                      <OwlCarousel
                        style={{
                          // position: "absolute",
                          top: "0",
                          width: "100%",
                        }}
                        className="owl-main hero-slider"
                        items={1}
                        loop
                      >
                        {room?.medias?.map((media) => {
                          return (
                            <div className="item hs-item set-bg">
                              <img
                                style={{ height: "250px", width: "100%" }}
                                src={media.url}
                                alt=""
                              />
                            </div>
                          );
                        })}
                      </OwlCarousel>

                      <div className="ri-text" style={{ position: "relative" }}>
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
                <Link to="/rooms">{pageNum}</Link>
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
      </section>
      {/* Rooms Section End */}
    </div>
  );
};

export default Rooms;
