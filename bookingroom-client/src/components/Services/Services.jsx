import { unwrapResult } from "@reduxjs/toolkit";
import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import {
  fetchGetHotelServices,
  fetchGetProductsByService,
} from "../../store/hotelServiceSlice/hotelServiceSlice";
import room1 from "../../assets/img/room/room-1.jpg";
import "./services.scss";
import Swal from "sweetalert2";

const Services = ({ serviceCallBack }) => {
  const dispatch = useDispatch();
  const [services, setServices] = useState([]);
  const [html, setHtml] = useState("");
  useEffect(() => {
    (async () => {
      const result = await dispatch(fetchGetHotelServices())
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          console.log(originalPromiseResult.data.items);
          setServices(originalPromiseResult.data.items);
          // console.log(services);
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
      {services &&
        services.map((service) => (
          <div className="booking-servies">
            <div className="booking-servies__left">
              <img src={service.thumbnail} />
            </div>
            <div className="booking-servies__right">
              <h4>{service.title}</h4>
              <p>
                {service.description.length <= 130
                  ? service.description
                  : service.description.slice(0, 126).concat("...")}
              </p>
              {service &&
                service?.price?.toLocaleString("it-IT", {
                  style: "currency",
                  currency: "VND",
                })}
              <div>
                <button
                  class="btn btn-link p-0"
                  onClick={(e) => {
                    serviceCallBack(service);
                  }}
                >
                  Add service
                </button>
                <a
                  onClick={async () => {
                    const result = await dispatch(
                      fetchGetProductsByService(service.id)
                    )
                      .then(unwrapResult)
                      .then((originalPromiseResult) => {
                        if (originalPromiseResult.status == "SUCCESS") {
                          console.log(originalPromiseResult);
                          let products = originalPromiseResult.data.items;
                          let html = `
                            <ul>
                          `;
                          html += products.map((item) => {
                            return `
                            <li style="display: flex; margin-bottom: 12px;">
                              <img style="width: 180px; height: 180px; object-fit: cover;" src=${item.thumbnail}>
                              <h3 style="margin-left: 12px">${item.name}</h3>
                            </li>
                            `;
                          });

                          html += `
                            </ul>
                          `;
                          setHtml(html);
                          Swal.fire({
                            title: "Danh sách sản phẩm",
                            html,
                            showClass: {
                              popup: "animate__animated animate__fadeInDown",
                            },
                            hideClass: {
                              popup: "animate__animated animate__fadeOutUp",
                            },
                          });
                          console.log(html);
                        }

                        // console.log(services);
                        // handle result here
                      })
                      .catch((rejectedValueOrSerializedError) => {
                        console.log(rejectedValueOrSerializedError);
                        // handle result here
                      });
                  }}
                  style={{ marginLeft: "12px" }}
                >
                  Chi tiết dịch vụ
                </a>
              </div>
            </div>
          </div>
        ))}
    </>
  );
};

export default Services;
