import React from "react";
import "./bookingStep.scss";
import step1 from "../../assets/img/booking/step1.png";
import step2 from "../../assets/img/booking/step2.png";
import step3 from "../../assets/img/booking/step3.png";
import Services from "../Services/Services";
import Rooms from "../Rooms/Rooms";

const Booking = ({
  step,
  expectedCheckIn,
  expectedCheckOut,
  num,
  type,
  roomCallBack,
  serviceCallBack,
}) => {
  return (
    <>
      <section className="mb-5 mt-5 ml-2">
        <h3>Rooms & Rates</h3>
        <p>Plan your perfect stay at our hotel</p>
        <img
          src={step == 1 ? step1 : step == 2 ? step2 : step3}
          alt=""
          className="booking-step"
        />
      </section>
      {step == 1 && (
        <Rooms
          expectedCheckIn={expectedCheckIn}
          expectedCheckOut={expectedCheckOut}
          num={num}
          type={type}
          roomCallBack={roomCallBack}
        />
      )}
      {step == 2 && <Services serviceCallBack={serviceCallBack} />}
      {step == 3 && <div>Đặt phòng thành công!!</div>}
    </>
  );
};

export default Booking;
