import React, { useEffect, useState } from "react";
import Header from "../../components/Header";
import { Link } from "react-router-dom";
import { fetchGetBookingsUser } from "../../store/bookingSlice/bookingSlice";
import { unwrapResult } from "@reduxjs/toolkit";
import { useDispatch, useSelector } from "react-redux";

const BookingCart = () => {
  const dispatch = useDispatch();
  const [bookings, setBookings] = useState([]);
  const user = useSelector((state) => state.user.value);

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
      <section className="mb-5">
        <div className="container">
          <h2>Danh sach phòng đã đặt</h2>
        </div>
      </section>
      <section className="mt-5">
        <div className="container">
          <div class="input-group mb-4">
            <input
              type="text"
              class="form-control"
              id="advanced-search-input"
              placeholder="phrase in:field1,field2"
            />
            <button
              class="btn btn-primary"
              id="advanced-search-button"
              type="button"
            >
              <i class="fa fa-search"></i>
            </button>
          </div>
          <div className="table-responsive">
            <table className="table datanew">
              <thead>
                <tr>
                  <th>Loại phòng đã đặt</th>
                  <th>Số lượng người</th>
                  <th>Người đặt</th>
                  <th>Checkin</th>
                  <th>Checkout</th>
                  <th>Đơn giá</th>
                  <th>Trạng thái</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {bookings &&
                  bookings.map((booking) => {
                    return (
                      <tr>
                        <td>
                          {booking.rooms?.[0]?.name}{" "}
                          {booking.rooms?.[0]?.price.toLocaleString("it-IT", {
                            style: "currency",
                            currency: "VND",
                          })}
                          $/Pernight
                        </td>
                        <td>{booking.rooms?.[0]?.capacity}</td>
                        <td>
                          {booking.createdBy.firstName.concat(
                            " " + booking.createdBy.lastName
                          )}
                        </td>
                        <td>{booking.checkIn}</td>
                        <td>{booking.checkOut}</td>
                        <td>
                          {booking.totalRoomPrice.toLocaleString("it-IT", {
                            style: "currency",
                            currency: "VND",
                          })}
                        </td>
                        <td>
                          <span
                            style={{
                              color:
                                booking.status == "PENDING"
                                  ? "#f50"
                                  : "#87d068",
                            }}
                          >
                            {booking.status == "PENDING"
                              ? "Chưa thanh toán"
                              : booking.status == "CANCEL"
                              ? "Đã hủy"
                              : "Đã thanh toán"}
                          </span>
                        </td>
                        <td>
                          <Link to={"/booking-detail/" + booking.id}>
                            Xem chi tiết
                          </Link>
                        </td>
                      </tr>
                    );
                  })}

                {/* <tr>
                  <td>Premium King Room 159$/Pernight</td>
                  <td>2</td>
                  <td>User</td>
                  <td>2/4/2023</td>
                  <td>3/4/2023</td>
                  <td>300$</td>
                  <td>
                    <span style={{ color: "#f50" }}>Chưa thanh toán</span>
                  </td>
                  <td>
                    <Link to={"/booking-detail"}>Xem chi tiết</Link>
                  </td>
                </tr> */}
              </tbody>
            </table>
          </div>
        </div>
      </section>
    </>
  );
};

export default BookingCart;
