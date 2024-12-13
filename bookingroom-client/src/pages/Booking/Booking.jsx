import React, { useState } from "react";
import "./booking.scss";
import BookingStep from "../../components/BookingStep/BookingStep";
import Reservation from "../../components/Reservation/Reservation";
import Header from "../../components/Header";
import FilterBooking from "../../components/FilterBooking/FilterBooking";

const Booking = () => {
  const [step, setStep] = useState(1);
  const [expectedCheckIn, setExpectedCheckIn] = useState("");
  const [expectedCheckOut, setExpectedCheckOut] = useState("");
  const [services, setServices] = useState([]);
  const [service, setService] = useState({});
  const [num, setNum] = useState("");
  const [type, setType] = useState("");
  const [room, setRoom] = useState({});

  const filter = (expectedCheckIn, expectedCheckOut, num, type) => {
    setExpectedCheckIn(expectedCheckIn);
    setExpectedCheckOut(expectedCheckOut);
    setNum(num);
    setType(type);
  };

  const roomCallBack = (room) => {
    setRoom(room);
  };

  const serviceCallBack = (service) => {
    services.push(service);
    setServices(services);
    setService(service);
  };

  const reservation = (step) => {
    setStep(step);
  };

  return (
    <>
      <Header />
      <FilterBooking filter={filter} />
      <div className="container">
        <div className="row">
          <main className="col-md-8">
            <BookingStep
              step={step}
              expectedCheckIn={expectedCheckIn}
              expectedCheckOut={expectedCheckOut}
              num={num}
              type={type}
              reservation={reservation}
              roomCallBack={roomCallBack}
              serviceCallBack={serviceCallBack}
            />
          </main>
          <aside className="col-md-4">
            <section className="mb-4">
              <img src="/images/coco-drink.png" width="300" alt="" />
              <h2 className="text-uppercase font-weight-bold">
                TODAY ONLY: 10% OFF
              </h2>
              <p>
                Book <span className="text-underline">today</span> and get an
                exclusive <strong>10% discount</strong> on your stay.
              </p>
              <button className="btn btn-primary text-uppercase">Enjoy</button>
            </section>
            <Reservation
              expectedCheckIn={expectedCheckIn}
              expectedCheckOut={expectedCheckOut}
              num={num}
              type={type}
              reservation={reservation}
              room={room}
              services={services}
              step={step}
            />
          </aside>
        </div>
      </div>
    </>
  );
};

export default Booking;
