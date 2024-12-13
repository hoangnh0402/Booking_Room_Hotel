import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "./filterBooking.scss";

function FilterBooking({ filter }) {
  const setDateFormat = (date, status) => {
    let day = date.getDate();
    if (day < 10) {
      day = "0" + day;
    }
    let month = date.getMonth() + 1;
    if (month < 10) {
      month = "0" + month;
    }
    const year = date.getFullYear();

    if (status == "checkin") {
      return year + "-" + month + "-" + day + "T" + "14:00";
    } else if (status == "checkout") {
      return year + "-" + month + "-" + day + "T" + "12:00";
    }
  };
  //   const [data, dispatch] = useSearchValue();
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [expectedCheckIn, setExpectedCheckIn] = useState(
    setDateFormat(new Date(), "checkin")
  );

  const [expectedCheckOut, setExpectedCheckOut] = useState(
    setDateFormat(new Date(), "checkout")
  );
  const [num, setNum] = useState("");
  const [type, setType] = useState("");
  const today = new Date();
  let newData = {};

  const eventhandler = (res) => Object.assign(newData, res);

  const changeDate = (date) => {
    let expectedCheckIn = setDateFormat(date, "checkin");
    setExpectedCheckIn(expectedCheckIn);
    console.log(expectedCheckIn);
    const nextDate = new Date(date);
    nextDate.setDate(date.getDate() + 1);
    setStartDate(date);
    setEndDate(nextDate);
  };

  const CustomInput = ({ value, onClick }) => (
    <div className="calendar" onClick={onClick}>
      <input type="text" className="form-item" value={value} />
      <div>
        <i className="fa-regular fa-calendar"></i>
      </div>
    </div>
  );

  return (
    <div
      className="container"
      style={{ padding: "0 20px", fontFamily: "serif" }}
    >
      <div className="row">
        <div className="col-lg-3 col-md-6">
          <div className="room-item">
            <h2>Check in</h2>
            <DatePicker
              dateFormat="dd/MM/yyyy"
              selected={startDate}
              onChange={changeDate}
              startDate={startDate}
              minDate={today}
              customInput={<CustomInput />}
            />
          </div>
        </div>
        <div className="col-lg-3 col-md-6">
          <div className="room-item">
            <h2>Check out</h2>
            <DatePicker
              dateFormat="dd/MM/yyyy"
              selected={endDate}
              onChange={(date) => {
                setEndDate(date);
                let expectedCheckOut = setDateFormat(date, "checkout");
                setExpectedCheckOut(expectedCheckOut);
              }}
              startDate={endDate}
              minDate={endDate}
              customInput={<CustomInput />}
            />
          </div>
        </div>
        <div className="col-lg-3 col-md-6">
          <div className="room-item">
            <h2>Person Number</h2>
            <select
              id="guest"
              className="custom-select select-option"
              name="num"
              style={{ width: "208px", height: "49px", fontSize: "18px" }}
              onChange={(e) => {
                setNum(e.target.value);
              }}
            >
              <option value={""}>Choose person numbers</option>
              <option value={1}>1 person</option>
              <option value={2}>2 persons</option>
              <option value={3}>3 persons</option>
              <option value={4}>4 persons</option>
              <option value={5}>5 persons</option>
              <option value={6}>6 persons</option>
              <option value={7}>7 persons</option>
            </select>
          </div>
        </div>
        <div className="col-lg-3 col-md-6">
          <div className="room-item">
            <h2>Type Room</h2>
            <select
              style={{ width: "208px", height: "49px", fontSize: "18px" }}
              id="guest"
              className="custom-select select-option"
              name="num"
              onChange={(e) => {
                setType(e.target.value);
              }}
            >
              <option value={""}>Choose type room</option>
              <option value="Standard">Standard</option>
              <option value="Superior">Superior</option>
              <option value="Deluxe">Deluxe</option>
              <option value="Suite">Suite</option>
            </select>
          </div>
        </div>
      </div>
      <button
        className="btn btn-primary"
        onClick={() => {
          console.log(expectedCheckIn, expectedCheckOut, num, type);
          filter(expectedCheckIn, expectedCheckOut, num, type);
        }}
        style={{ backgroundColor: "#5892b5 !important" }}
      >
        Check available
      </button>
    </div>
  );
}

export default FilterBooking;
