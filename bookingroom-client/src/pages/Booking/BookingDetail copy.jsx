import React, { useEffect, useState } from "react";
import Header from "../../components/Header";
import roomDetail from "../../assets/img/room/room-details.jpg";
import { useDispatch, useSelector } from "react-redux";
import { unwrapResult } from "@reduxjs/toolkit";
import { fetchGetBookingsUser } from "../../store/bookingSlice/bookingSlice";

const BookingDetail = () => {
  const dispatch = useDispatch();
  const [bookings, setBookings] = useState([]);
  const user = useSelector((state) => state.user.value);
  console.log(user);

  useEffect(() => {
    (async () => {
      const result = await dispatch(fetchGetBookingsUser())
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          setBookings(originalPromiseResult.data.items);
          console.log(originalPromiseResult.data.items);
          // handle result here
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError);
          // handle result here
        });
    })();

    // return () => {}; // no-op
  }, []);

  return (
    <>
      <Header />
      <section className="room-details-section spad">
        <div class="container">
          <div class="row">
            <div class="col-lg-10">
              <div class="mb-5">
                <h4>Thank {user.firstName.concat(" " + user.lastName)}</h4>
                <h2>Your list Reservation</h2>
                <ul style={{ fontSize: "20px" }}>
                  {/* <li>Chúng tôi đã ..... </li> */}
                  {/* <li>........</li> */}
                </ul>
              </div>
            </div>
          </div>
        </div>
        {bookings &&
          bookings.map((booking) => {
            return (
              <div className="container">
                <div className="row">
                  <div className="col-lg-8">
                    <div className="room-details-item">
                      <img src={roomDetail} alt="" />
                      <div className="rd-text">
                        <div className="rd-title">
                          <h3>{booking.rooms[0].title}</h3>
                          <div className="rdt-right">
                            <div className="rating">
                              <i className="icon_star" />
                              <i className="icon_star" />
                              <i className="icon_star" />
                              <i className="icon_star" />
                              <i className="icon_star-half_alt" />
                            </div>
                          </div>
                        </div>
                        <h2>
                          159$<span>/Pernight</span>
                        </h2>
                        <table>
                          <tbody>
                            <tr>
                              <td className="r-o">Type:</td>
                              <td>{booking.rooms[0].type}</td>
                            </tr>
                            <tr>
                              <td className="r-o">Capacity:</td>
                              <td>Max persion {booking.rooms[0].maxNum}</td>
                            </tr>
                            <tr>
                              <td className="r-o">Floor:</td>
                              <td>{booking.rooms[0].floor}</td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                  <div className="col-lg-4">
                    <div className="room-booking">
                      <h3>Your Reservation</h3>
                      <div className="border-bottom">
                        <form action="#" className="mb-5">
                          <div className="check-date">
                            <label htmlFor="date-in">Check In:</label>
                            <input
                              type="text"
                              className="date-input"
                              id="date-in"
                              value={booking.checkIn}
                            />
                            <i className="icon_calendar" />
                          </div>
                          <div className="check-date">
                            <label htmlFor="date-out">Check Out:</label>
                            <input
                              type="text"
                              className="date-input"
                              id="date-out"
                              value={booking.checkOut}
                            />
                            <i className="icon_calendar" />
                          </div>
                          {/* <div
                            style={{ width: "100%" }}
                            className="select-option"
                          >
                            <label htmlFor="guest">Guests:</label>
                            <select id="guest" className="custom-select">
                              <option value="">{booking.rooms[0].}</option>
                            </select>
                          </div> */}
                          {/* <div className="select-option">
                            <label htmlFor="room">Room:</label>
                            <select id="room" className="custom-select">
                              <option value="">1 Room</option>
                            </select>
                          </div> */}
                        </form>
                      </div>
                      <div className="border-bottom mt-5  ">
                        <h3>List Services</h3>
                        <ul class="list-unstyled mb-2">
                          {booking.services &&
                            booking.services.map((service) => {
                              return (
                                <li class="media">
                                  <i className="flaticon-033-dinner" />-
                                  {service.service.title}
                                  {/* Cras sit amet nibh libero, in gravida nulla. */}
                                </li>
                              );
                            })}
                        </ul>
                      </div>
                      <div className="border-bottom mt-3">
                        <div className="d-flex justify-content-end mb-3">
                          <h4>
                            Tổng tiền phòng:{" "}
                            <span style={{ color: "#5892b5", fontWeight: 700 }}>
                              {booking.totalRoomPrice}$
                            </span>
                          </h4>
                        </div>
                        <div className="d-flex justify-content-end mb-3">
                          <h4>
                            Tổng tiền dịch vụ:{" "}
                            <span style={{ color: "#5892b5", fontWeight: 700 }}>
                              {booking.totalServicePrice}$
                            </span>
                          </h4>
                        </div>
                      </div>
                      {/* <button
                        type="button"
                        class="btn btn-lg btn-block"
                        style={{
                          "text-transform": "uppercase",
                          border: "1px solid #5892b5",
                          "border-radius": "2px",
                          color: "#5892b5",
                          "font-weight": 500,
                          background: "transparent",
                        }}
                      >
                        Hủy phòng
                      </button> */}
                    </div>
                  </div>
                </div>
              </div>
            );
          })}
      </section>
    </>
  );
};

export default BookingDetail;
