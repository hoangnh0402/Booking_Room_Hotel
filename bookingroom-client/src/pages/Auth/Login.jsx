import React, { useState } from "react";
import { useForm } from "react-hook-form";
import "./Login.css";
import slider1 from "../../assets/img/hero/hero-1.jpg";
import slider2 from "../../assets/img/hero/hero-2.jpg";
import slider3 from "../../assets/img/hero/hero-3.jpg";
import OwlCarousel from "react-owl-carousel";
import "owl.carousel/dist/assets/owl.carousel.css";
import "owl.carousel/dist/assets/owl.theme.default.css";
import { useDispatch, useSelector } from "react-redux";
import { unwrapResult } from "@reduxjs/toolkit";
import { fetchLogin, fetchRegister } from "../../store/authSlice/authSlice";
import { setToken } from "../../store/tokenSlice/tokenSlice";
import { redirect } from "react-router-dom";
import storageService from "../../services/storage.service";
import Modal from "../../components/Modal/Modal";
import Swal from "sweetalert2";
import { MDBIcon } from "mdb-react-ui-kit";

const Login = () => {
  const { register, handleSubmit } = useForm();
  const [isBounceActive, setIsBounceActive] = useState(false);
  const [emailOrPhone, setEmailOrPhone] = useState("admin@gmail.com");
  const [password, setPassword] = useState("admin");
  const [newEmail, setNewEmail] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newPhoneNumber, setNewPhoneNumber] = useState("");
  const [newFirstName, setNewFirstName] = useState("");
  const [newLastName, setNewLastName] = useState("");
  const [newGender, setNewGender] = useState("");
  const [newBirthday, setNewBirthday] = useState("");
  const [newAddress, setNewAddress] = useState("");
  const dispatch = useDispatch();
  const [isClose, setIsClose] = useState(true);

  const signUpButton = () => {
    setIsBounceActive(true);
  };
  const signInButton = () => {
    setIsBounceActive(false);
  };
  const [displayModal, setDisplayModal] = useState(false);
  const [statusModal, setStatusModal] = useState("");
  const [messageModal, setMessageModal] = useState("");
  const callback = () => {
    setDisplayModal(false);
    setStatusModal("");
    setMessageModal("");
  };
  const loginFunc = async () => {
    const result = await dispatch(
      fetchLogin({
        emailOrPhone,
        password,
      })
    )
      .then(unwrapResult)
      .then((originalPromiseResult) => {
        if (originalPromiseResult.status == "SUCCESS") {
          const token = originalPromiseResult.data.accessToken;
          const index = originalPromiseResult.data.authorities.findIndex(
            (role) => {
              return role.authority == "ROLE_ADMIN";
            }
          );
          storageService.set("token", token);
          if (index == "-1") {
            window.location.href = "/";
          } else {
            window.location.href = "/admin/users";
          }
        } else {
          Swal.fire(originalPromiseResult.message, "", "error");
        }
        console.log(originalPromiseResult);
      })
      .catch((rejectedValueOrSerializedError) => {
        console.log(rejectedValueOrSerializedError);
        // handle result here
      });
  };
  const registerFunc = async () => {
    console.log(
      newEmail,
      newPassword,
      newPhoneNumber,
      newFirstName,
      newLastName,
      newGender,
      newBirthday,
      newAddress
    );
    const formData = new FormData();
    formData.append("email", newEmail);
    formData.append("password", newPassword);
    formData.append("phoneNumber", newPhoneNumber);
    formData.append("firstName", newFirstName);
    formData.append("lastName", newLastName);
    formData.append("gender", newGender);
    formData.append("birthday", newBirthday);
    formData.append("address", newAddress);
    const result = await dispatch(fetchRegister(formData))
      .then(unwrapResult)
      .then((originalPromiseResult) => {
        Swal.fire({
          title: "Input code from your email",
          input: "text",
          inputAttributes: {
            autocapitalize: "off",
          },
          showCancelButton: true,
          confirmButtonText: "Enter",
          showLoaderOnConfirm: true,
          preConfirm: async (token) => {
            return await fetch(
              `${process.env.REACT_APP_API_URL}/api/v1/auth/signup/verify?email=${newEmail}&token=${token}`,
              {
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
                Swal.showValidationMessage(`Request failed: ${error}`);
              });
          },
          // allowOutsideClick: () => !Swal.isLoading(),
        }).then((result) => {
          console.log("result", result);
          if (result.value.status == "SUCCESS") {
            Swal.fire("Đăng ký thành công", "", "success");
          } else {
            Swal.fire("Token không chính xác");
          }
        });
      })
      .catch((rejectedValueOrSerializedError) => {
        console.log(rejectedValueOrSerializedError);
        // handle result here
      });
  };

  return (
    <section className="login">
      <OwlCarousel
        style={{ position: "absolute", top: "0" }}
        className="owl-main hero-slider"
        items={1}
        loop
        autoplay
        dots={false}
      >
        <div className="item hs-item set-bg">
          <img style={{ height: "100%" }} src={slider1} alt="" />
        </div>
        <div className="item item hs-item set-bg">
          <img style={{ height: "100%" }} src={slider2} alt="" />
        </div>
        <div className="item item hs-item set-bg">
          <img style={{ height: "100%" }} src={slider3} alt="" />
        </div>
      </OwlCarousel>
      <div className="user">
        <div className="user_options-container">
          <div className="user_options-text">
            <div className="user_options-unregistered">
              <h2 className="user_unregistered-title">
                Don't have an account?
              </h2>
              <p className="user_unregistered-text">
                Banjo tote bag bicycle rights, High Life sartorial cray craft
                beer whatever street art fap.
              </p>
              <button
                className="user_unregistered-signup"
                id="signup-button"
                onClick={signUpButton}
              >
                Sign up
              </button>
            </div>
            <div className="user_options-registered">
              <h2 className="user_registered-title">Have an account?</h2>
              <p className="user_registered-text">
                Banjo tote bag bicycle rights, High Life sartorial cray craft
                beer whatever street art fap.
              </p>
              <button
                className="user_registered-login"
                id="login-button"
                onClick={signInButton}
              >
                Login
              </button>
            </div>
          </div>
          <div
            className={`user_options-forms ${
              isBounceActive ? "bounceLeft" : "bounceRight"
            }`}
            id="user_options-forms"
          >
            <div className="user_forms-login">
              <h2 className="forms_title">Login</h2>
              <form className="forms_form" onSubmit={handleSubmit(loginFunc)}>
                <fieldset className="forms_fieldset">
                  <div className="forms_field">
                    <input
                      type="text"
                      placeholder="Email"
                      className="forms_field-input"
                      required=""
                      autoFocus=""
                      value={emailOrPhone}
                      onChange={(e) => setEmailOrPhone(e.target.value)}
                    />
                  </div>
                  <div className="forms_field" style={{ position: "relative" }}>
                    <input
                      type={isClose ? "password" : "text"}
                      placeholder="Password"
                      className="forms_field-input"
                      required=""
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                    <div
                      style={{
                        position: "absolute",
                        top: "0",
                        right: "12px",
                        cursor: "pointer",
                      }}
                    >
                      {isClose && (
                        <MDBIcon
                          far
                          icon="eye-slash"
                          onClick={() => {
                            setIsClose(!isClose);
                          }}
                        />
                      )}
                      {!isClose && (
                        <MDBIcon
                          far
                          icon="eye"
                          onClick={() => {
                            setIsClose(!isClose);
                          }}
                        />
                      )}
                    </div>
                  </div>
                </fieldset>
                <div className="forms_buttons">
                  <button
                    type="button"
                    className="forms_buttons-forgot"
                    onClick={() => {
                      Swal.fire({
                        title: "Enter your email to resert password",
                        input: "text",
                        inputAttributes: {
                          autocapitalize: "off",
                        },
                        showCancelButton: true,
                        confirmButtonText: "Enter",
                        showLoaderOnConfirm: true,
                        preConfirm: async (email) => {
                          return fetch(
                            `${process.env.REACT_APP_API_URL}/api/v1/auth/forgot-password?email=${email}`,
                            {
                              method: "POST",
                            }
                          )
                            .then((response) => {
                              console.log(response);
                              if (!response.ok) {
                                throw new Error(response.statusText);
                              }
                              return email;
                            })
                            .catch((error) => {
                              Swal.showValidationMessage(
                                `Request failed: ${error}`
                              );
                            });
                        },
                      }).then(async (result) => {
                        const email = result.value;
                        if (!email) {
                          return;
                        }
                        Swal.fire({
                          title: "Your token is sent to " + email,
                          html: `<input type="text" id="token" class="swal2-input" placeholder="Your token">
                          <input type="password" id="password" class="swal2-input" placeholder="New password">`,
                          confirmButtonText: "Confirm",
                          focusConfirm: false,
                          preConfirm: async () => {
                            const token =
                              Swal.getPopup().querySelector("#token").value;
                            const password =
                              Swal.getPopup().querySelector("#password").value;
                            if (!token || !password) {
                              Swal.showValidationMessage(
                                `Please enter token and new password`
                              );
                            }
                            const response = await fetch(
                              `${process.env.REACT_APP_API_URL}/api/v1/auth/forgot-password/verify?email=${email}&token=${token}&password=${password}`,
                              {
                                method: "POST",
                              }
                            );
                            if (response.status != 200) {
                              if (!token || !password) {
                                Swal.showValidationMessage(
                                  `Please enter correct token`
                                );
                              }
                            }
                            return await response.json();
                            // return { token: token, password: password };
                          },
                        }).then((info) => {
                          Swal.fire(
                            "Cập nhật mật khẩu thành công",
                            "",
                            "success"
                          );
                        });
                      });
                    }}
                  >
                    Forgot password?
                  </button>
                  <input
                    type="submit"
                    defaultValue="Log In"
                    className="forms_buttons-action"
                  />
                </div>
              </form>
            </div>
            <div className="user_forms-signup">
              <h2 className="forms_title">Sign Up</h2>
              <form
                className="forms_form"
                onSubmit={handleSubmit(registerFunc)}
              >
                <fieldset className="forms_fieldset">
                  <div className="forms_field">
                    <input
                      type="email"
                      placeholder="Email"
                      className="forms_field-input"
                      required=""
                      value={newEmail}
                      onChange={(e) => setNewEmail(e.target.value)}
                    />
                  </div>
                  <div className="forms_field">
                    <input
                      type="text"
                      placeholder="Phone Nụmber"
                      className="forms_field-input"
                      required=""
                      value={newPhoneNumber}
                      onChange={(e) => setNewPhoneNumber(e.target.value)}
                    />
                  </div>
                  <div className="forms_field">
                    <input
                      type="password"
                      placeholder="Password"
                      className="forms_field-input"
                      required=""
                      value={newPassword}
                      onChange={(e) => setNewPassword(e.target.value)}
                    />
                  </div>
                  <div className="forms_field">
                    <input
                      type="text"
                      placeholder="First Name"
                      className="forms_field-input"
                      required=""
                      value={newFirstName}
                      onChange={(e) => setNewFirstName(e.target.value)}
                    />
                  </div>
                  <div className="forms_field">
                    <input
                      type="text"
                      placeholder="Last Name"
                      className="forms_field-input"
                      required=""
                      value={newLastName}
                      onChange={(e) => setNewLastName(e.target.value)}
                    />
                  </div>
                  <div className="forms_field">
                    <label htmlFor="male">Male</label>{" "}
                    <input
                      style={{ marginRight: "12px" }}
                      id="male"
                      type="radio"
                      value="Male"
                      name="gender"
                      onChange={(e) => setNewGender(e.target.value)}
                    />
                    <label htmlFor="female">Female</label>{" "}
                    <input
                      name="gender"
                      id="female"
                      type="radio"
                      value="Female"
                      onChange={(e) => setNewGender(e.target.value)}
                    />
                  </div>
                  <div className="forms_field">
                    <input
                      type="date"
                      placeholder="Birthday"
                      className="forms_field-input"
                      required=""
                      onChange={(e) => setNewBirthday(e.target.value)}
                    />
                  </div>
                  <div className="forms_field">
                    <input
                      type="text"
                      placeholder="Address"
                      className="forms_field-input"
                      required=""
                      value={newAddress}
                      onChange={(e) => setNewAddress(e.target.value)}
                    />
                  </div>
                </fieldset>
                <div className="forms_buttons">
                  <input
                    type="submit"
                    defaultValue="Sign up"
                    className="forms_buttons-action"
                  />
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Login;
