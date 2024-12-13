import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./modal.scss";

const Modal = ({ displayModal, statusModal, messageModal, callback, url }) => {
  const [display, setDisplay] = useState(displayModal);
  const [status, setStatus] = useState(statusModal);
  const [message, setMessage] = useState(messageModal);

  useEffect(() => {
    setDisplay(displayModal);
    setStatus(statusModal);
    setMessage(messageModal);
    // console.log(displayModal, statusModal, messageModal);
  }, [displayModal, statusModal, messageModal]);

  return (
    <div>
      {/* Modal HTML */}
      <div
        style={{ display: display ? "block" : "none" }}
        id="myModal"
        className={"modal" + " fade " + (display ? "show" : "")}
      >
        <div className="modal-dialog modal-confirm">
          <div className="modal-content">
            <div
              style={{
                background: status == "success" ? "#47c9a2" : "#E3503A",
              }}
              className="modal-header justify-content-center"
            >
              <div className="icon-box">
                {status == "success" && <i class="fa-solid fa-check"></i>}
                {status == "failure" && <i class="fa-solid fa-xmark"></i>}
              </div>
              <button
                type="button"
                className="close"
                data-dismiss="modal"
                aria-hidden="true"
                onClick={() => {
                  callback();
                }}
              >
                ×
              </button>
            </div>
            <div className="modal-body text-center">
              <h4>{message}</h4>
              <p>{message}</p>
              <Link to={url} className="btn btn-success" data-dismiss="modal">
                <span>Quay lại</span>
              </Link>
            </div>
          </div>
        </div>
      </div>
      <div
        style={{ display: display ? "block" : "none" }}
        class={"modal-backdrop" + " fade " + (display ? "show" : "")}
      ></div>
    </div>
  );
};

export default Modal;
