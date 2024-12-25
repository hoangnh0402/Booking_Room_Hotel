import React, { useEffect, useState } from "react";
import Header from "../../components/Header";
import roomDetail from "../../assets/img/room/room-details.jpg";
import { useDispatch, useSelector } from "react-redux";
import { fetchGetBooking } from "../../store/bookingSlice/bookingSlice";
import { unwrapResult } from "@reduxjs/toolkit";
import { useParams } from "react-router-dom";
import OwlCarousel from "react-owl-carousel";
import Swal from "sweetalert2";
import storageService from "../../services/storage.service";
import { fetchGetProductsByService } from "../../store/hotelServiceSlice/hotelServiceSlice";

const BookingDetail = () => {
  const dispatch = useDispatch();
  const [booking, setBooking] = useState();
  const [products, setProducts] = useState({});
  let { bookingId } = useParams();

  const user = useSelector((state) => state.user.value);

  useEffect(() => {
    (async () => {
      const result = await dispatch(fetchGetBooking(bookingId))
        .then(unwrapResult)
        .then(async (originalPromiseResult) => {
          console.log(originalPromiseResult);
          // console.log(originalPromiseResult.data);
          for (let service of originalPromiseResult.data.services) {
            const result = await dispatch(
              fetchGetProductsByService(service.service.id)
            )
              .then(unwrapResult)
              .then((originalPromiseResult) => {
                if (originalPromiseResult.status == "SUCCESS") {
                  products[service.service.id] =
                    originalPromiseResult.data.items;
                  setProducts(products);
                }
              });
          }
          setBooking(originalPromiseResult.data);

          // handle result here
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError);
          // handle result here
        });
    })();
  }, []);

  return (
    <>
      <Header />
      <section className="room-details-section spad">
        <div class="container">
          <div class="row">
            <div class="col-lg-10">
              <div class="mb-5">
                <h4>
                  Thanks {user && user?.firstName?.concat(" " + user?.lastName)}{" "}
                  , Đặt phòng của bạn ở HIT hotel đã được nhận , Chúng
                  tôi mong rằng bạn sẽ đến check-in đúng giờ theo quy định của
                  khách sạn và có những trải nghiệm tuyệt vời ở HIT
                  hotel. Cảm ơn bạn rất nhiều và hẹn gặp lại !
                </h4>
                <h2>
                  Mọi thông tin chi tiết xin liên hệ với hotline: 0379327548
                </h2>
                <ul style={{ fontSize: "20px" }}>
                  {/* <li>Chúng tôi đã ..... </li>
                  <li>........</li> */}
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div></div>
        <div className="container">
          <div className="row">
            <div className="col-lg-8">
              <div className="room-details-item">
                <div className="rd-text">
                  <div className="rd-title">
                    <h3> {booking && booking.rooms[0].name}</h3>
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
                    {booking &&
                      booking.rooms[0].price.toLocaleString("it-IT", {
                        style: "currency",
                        currency: "VND",
                      })}
                    <span>/Pernight</span>
                  </h2>
                  <table>
                    <tbody>
                      <tr>
                        <td className="r-o">Type:</td>
                        <td>{booking && booking.rooms[0].type}</td>
                      </tr>
                      <tr>
                        <td className="r-o">Capacity:</td>
                        <td>
                          Max persion {booking && booking.rooms[0].capacity}
                        </td>
                      </tr>
                      <tr>
                        <td className="r-o">Bed:</td>
                        <td>{booking && booking.rooms[0].bed}</td>
                      </tr>
                      <tr>
                        <td className="r-o">Size:</td>
                        <td>{booking && booking.rooms[0].size}</td>
                      </tr>
                    </tbody>
                  </table>
                  <p className="f-para">
                    {booking && booking.rooms[0].description}
                  </p>
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
                        value={booking && booking.checkIn}
                      />
                      <i className="icon_calendar" />
                    </div>
                    <div className="check-date">
                      <label htmlFor="date-out">Check Out:</label>
                      <input
                        type="text"
                        className="date-input"
                        id="date-out"
                        value={booking && booking.checkOut}
                      />
                      <i className="icon_calendar" />
                    </div>
                    <div style={{ width: "100%" }} className="select-option">
                      <label htmlFor="guest">Guests:</label>
                      <select id="guest" className="custom-select">
                        <option value="">3 Adults</option>
                      </select>
                    </div>
                    <div className="select-option">
                      <label htmlFor="room">Room:</label>
                      <select id="room" className="custom-select">
                        <option value="">1 Room</option>
                      </select>
                    </div>
                  </form>
                </div>
                <div className="border-bottom mt-5  ">
                  <h3>Dịch vụ kèm theo</h3>
                  <ul class="list-unstyled mb-5">
                    {booking &&
                      booking?.services?.map((service) => {
                        return (
                          <li class="media">
                            <i class="fa-solid fa-utensils"></i>
                            <div class="media-body">
                              <h5 class="mt-0 mb-1 ml-1">
                                {service?.service?.title} X {service?.amount}
                              </h5>
                              {/* product render here */}
                              <ul style={{ listStyle: "revert" }}>
                                {products[service.service.id] &&
                                  products[service.service.id].map(
                                    (product) => {
                                      return <li>{product.name}</li>;
                                    }
                                  )}
                              </ul>
                            </div>
                          </li>
                        );
                      })}
                  </ul>
                </div>
                <div className="border-bottom mt-3">
                  <div className="d-flex justify-content-end mb-3">
                    <h4>
                      Tổng tiền:
                      <span style={{ color: "#dfa974", fontWeight: 700 }}>
                        {booking &&
                          booking.totalRoomPrice.toLocaleString("it-IT", {
                            style: "currency",
                            currency: "VND",
                          })}
                      </span>
                    </h4>
                  </div>
                </div>
                <button
                  type="button"
                  class="btn btn-lg btn-block"
                  style={{
                    "text-transform": "uppercase",
                    border: "1px solid #dfa974",
                    "border-radius": "2px",
                    color: "#dfa974",
                    "font-weight": 500,
                    background: "transparent",
                  }}
                  onClick={async (e) => {
                    console.log(bookingId);
                    Swal.fire({
                      title: "Note the reason",
                      input: "text",
                      inputAttributes: {
                        autocapitalize: "off",
                      },
                      showCancelButton: true,
                      confirmButtonText: "Enter",
                      showLoaderOnConfirm: true,
                      preConfirm: async (note) => {
                        return await fetch(
                          `${process.env.REACT_APP_API_URL}/api/v1/booking/cancel/${bookingId}?note=${note}`,
                          {
                            headers: {
                              Authorization:
                                "Bearer " + storageService.get("token"),
                            },
                            method: "POST",
                          }
                        )
                          .then((response) => {
                            console.log(response);
                            if (!response.ok) {
                              throw new Error(response.statusText);
                            }
                            return response.json();
                          })
                          .catch((error) => {
                            Swal.showValidationMessage(
                              `Request failed: ${error}`
                            );
                          });
                      },
                      // allowOutsideClick: () => !Swal.isLoading(),
                    }).then((result) => {
                      console.log("result", result);
                      if (result.value.status == "SUCCESS") {
                        Swal.fire("Cancel booking successfully", "", "success");
                      } else {
                        Swal.fire("Some error");
                      }
                    });
                  }}
                >
                  Hủy phòng
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default BookingDetail;
