import AdminHeader from "../../components/AdminHeader";
import AdminSIdeBar from "../../components/AdminSideBar";
import { useDispatch } from "react-redux";
import { fetchCreateRoom, fetchGetRoom } from "../../store/roomSlice/roomSlice";
import { unwrapResult } from "@reduxjs/toolkit";
import { useEffect, useState } from "react";

import saleInterface from "../../interfaces/sales.interface";
import roomsInterface from "../../interfaces/rooms.interface";
import usersInterface from "../../interfaces/users.interface";
import servicesInterface from "../../interfaces/service.interface.js";
import { useParams, useNavigate, Link } from "react-router-dom";
import {
  fetchCreateHotelServices,
  fetchGetHotelService,
  fetchGetHotelServicesAdmin,
} from "../../store/hotelServiceSlice/hotelServiceSlice";
import {
  fetchCreateProduct,
  fetchGetProductsAdmin,
} from "../../store/productSlice/productSlice";
import productInterface from "../../interfaces/product.interface";
import postInterface from "../../interfaces/post.interface";
import { fetchCreateSale, fetchGetSale } from "../../store/saleSlice/saleSlice";
import { fetchGetUser } from "../../store/userSlice/userSlice";
import { fetchCreatePost } from "../../store/postSlice/postSlice";
import Modal from "../../components/Modal/Modal";
import salesInterface from "../../interfaces/sales.interface";
import "./addManage.scss";
import Select from "react-select";

const options = [
  { value: "chocolate", label: "Chocolate" },
  { value: "strawberry", label: "Strawberry" },
  { value: "vanilla", label: "Vanilla" },
];

const AddUser = () => {
  const dispatch = useDispatch();
  const [keys, setKeys] = useState([]);
  const [data, setData] = useState({});
  const navigate = useNavigate();
  let { option, method } = useParams();
  const [displayModal, setDisplayModal] = useState(false);
  const [statusModal, setStatusModal] = useState("");
  const [messageModal, setMessageModal] = useState("");
  const [virtualImg, setVirtualImg] = useState(["assets/img/icons/upload.svg"]);
  const [serivceData, setServiceData] = useState([]);

  const callback = () => {
    setDisplayModal(false);
    setStatusModal("");
    setMessageModal("");
  };

  useEffect(() => {
    if (option == "rooms") {
      delete roomsInterface["type"];
      delete roomsInterface["id"];
      setKeys([...Object.keys(roomsInterface)]);
      setData({ ...roomsInterface });
    } else if (option == "sales") {
      delete salesInterface["dayStart"];
      delete salesInterface["dayEnd"];
      setKeys([...Object.keys(saleInterface)]);
      setData({ ...saleInterface });
      delete saleInterface["id"];
    } else if (option == "users") {
      setKeys([...Object.keys(usersInterface)]);
      setData({ ...usersInterface });
      delete usersInterface["id"];
    } else if (option == "services") {
      setKeys([...Object.keys(servicesInterface)]);
      setData({ ...servicesInterface });
      delete servicesInterface["id"];
    } else if (option == "products") {
      delete productInterface["serviceId"];
      setKeys([...Object.keys(productInterface)]);
      setData({ ...productInterface });
      delete productInterface["id"];
    } else if (option == "posts") {
      setKeys([...Object.keys(postInterface)]);
      setData({ ...postInterface });
      delete postInterface["id"];
    }
  }, [option]);

  const addOption = async () => {
    // return;
    let func;
    if (option == "rooms") {
      if (!data.type) {
        data.type = "VIP";
      }
      let formData = new FormData();
      let files = data["files"];
      delete data["files"];
      for (let file of files) {
        formData.append("files", file);
      }
      for (let i in data) {
        formData.append(i, data[i]);
      }
      func = fetchCreateRoom(formData);
    } else if (option == "sales") {
      func = fetchCreateSale(data);
    } else if (option == "users") {
      func = fetchCreateRoom();
    } else if (option == "services") {
      let formData = new FormData();
      for (let i in data) {
        formData.append(i, data[i]);
      }
      func = fetchCreateHotelServices(formData);
    } else if (option == "products") {
      let formData = new FormData();
      for (let i in data) {
        formData.append(i, data[i]);
      }
      func = fetchCreateProduct(formData);
    } else if (option == "posts") {
      let formData = new FormData();
      let files = data["files"];
      delete data["files"];
      for (let file of files) {
        formData.append("files", file);
      }
      for (let i in data) {
        formData.append(i, data[i]);
      }
      for (let i in data) {
        formData.append(i, data[i]);
      }
      func = fetchCreatePost(formData);
    }
    const result = await dispatch(func)
      .then(unwrapResult)
      .then((originalPromiseResult) => {
        console.log("result", originalPromiseResult);
        if (originalPromiseResult.status == "SUCCESS") {
          setDisplayModal(true);
          setStatusModal("success");
          setMessageModal("Tạo thành công");
        } else {
          setDisplayModal(true);
          setStatusModal("failure");
          setMessageModal("Tạo thất bại");
        }
      })
      .catch((rejectedValueOrSerializedError) => {
        setDisplayModal(true);
        setStatusModal("failure");
        setMessageModal("Tạo thất bại");
        console.log(rejectedValueOrSerializedError);
        // handle result here
      });
  };
  useEffect(() => {
    (async () => {
      if (option == "products") {
        const result = await dispatch(
          fetchGetHotelServicesAdmin({ deleteFlag: false })
        )
          .then(unwrapResult)
          .then((originalPromiseResult) => {
            if (originalPromiseResult.status == "SUCCESS") {
              let services = originalPromiseResult.data.items.map((item) => {
                return {
                  value: item.id,
                  label: item.title,
                };
              });
              setServiceData(services);
            }
          })
          .catch((rejectedValueOrSerializedError) => {
            console.log(rejectedValueOrSerializedError);
          });
      }
    })();
  }, [option]);

  const handleDeleteImage = (index) => {
    virtualImg.splice(index, 1);
    console.log(virtualImg);
    setVirtualImg([...virtualImg]);
  };

  return (
    <div className="main-wrapper">
      <Modal
        displayModal={displayModal}
        statusModal={statusModal}
        messageModal={messageModal}
        callback={callback}
        url={"/admin/" + option}
        // displayStatus="true"
        // message="Cập nhật thành công"
        // status="success"
      />
      <AdminHeader />
      <AdminSIdeBar option={option} />
      <div className="page-wrapper">
        <div className="content">
          <div className="page-header">
            <div className="page-title">
              <h4>
                {option.charAt(0).toUpperCase() + option.slice(1)} Management
              </h4>
              <h6>Add {option.charAt(0).toUpperCase() + option.slice(1)}</h6>
            </div>
          </div>
          <div className="card">
            <div className="card-body">
              <div className="row">
                {keys &&
                  keys.map((key) => (
                    <div className="col-lg-3 col-sm-6 col-12">
                      <div className="form-group">
                        <label>{key}</label>
                        <input
                          onChange={(e) => {
                            setData((prevState) => {
                              prevState[key] = e.target.value;
                              return prevState;
                            });
                          }}
                          type="text"
                        />
                      </div>
                    </div>
                  ))}
                {option == "users" && (
                  <div className="col-lg-12">
                    <div className="form-group">
                      <label> User Image</label>
                      <div className="image-upload">
                        <input
                          type="file"
                          onChange={(e) => {
                            setVirtualImg(e.target.files[0]);

                            setData((prevState) => {
                              // console.log();
                              prevState["files"] = e.target.files[0];
                              return prevState;
                            });
                          }}
                        />
                        <div className="image-uploads">
                          <img src={virtualImg[0]} alt="img" />
                          <h4>Drag and drop a file to upload</h4>
                        </div>
                      </div>
                    </div>
                  </div>
                )}
                {option == "rooms" && (
                  <>
                    <div className="col-lg-3 col-sm-6 col-12">
                      <div className="form-group">
                        <label>type</label>
                        <select
                          style={{
                            width: "100%",
                            height: "100%",
                            padding: "9px",
                            borderColor: "#DCE0E4",
                          }}
                          onChange={(e) => {
                            setData((prevState) => {
                              prevState["type"] = e.target.value;
                              return prevState;
                            });
                          }}
                        >
                          <option value="Standard Single">
                            Standard Single
                          </option>
                          <option value="Standard Double">
                            Standard Double
                          </option>
                          <option value="Standard Twin">Standard Twin</option>
                          <option value="Superior Double">
                            Superior Double
                          </option>
                          <option value="Superior Twin">Superior Twin</option>
                          <option value="Superior King">Superior King</option>
                          <option value="Deluxe Double">Deluxe Double</option>
                          <option value="Deluxe Double Sea View">
                            Deluxe Double Sea View
                          </option>
                          <option value="Deluxe Twin">Deluxe Twin</option>
                          <option
                            value="
                            Deluxe Twin Sea View"
                          >
                            Deluxe Twin Sea View
                          </option>
                          <option value="Deluxe King">Deluxe King</option>
                          <option value="Deluxe King Sea View">
                            Deluxe King Sea View
                          </option>
                          <option value="Deluxe Triple">Deluxe Triple</option>
                          <option value="Deluxe Triple Sea View">
                            Deluxe Triple Sea View
                          </option>
                          <option value="Junior Suite">Junior Suite</option>
                          <option value="Family Suite">Family Suite</option>
                          <option value="Senior Suite">Senior Suite</option>
                          <option value="Executive Suite">
                            Executive Suite
                          </option>
                        </select>
                      </div>
                    </div>

                    <div className="col-lg-12">
                      <div className="form-group">
                        <label> Room Image</label>
                        <div className="image-upload">
                          <input
                            type="file"
                            multiple
                            onChange={(e) => {
                              let arr = [];
                              let files = [];
                              for (let file of e.target.files) {
                                arr.push(URL.createObjectURL(file));
                                files.push(file);
                              }
                              setVirtualImg(arr);
                              setData((prevState) => {
                                prevState["files"] = files;
                                return prevState;
                              });
                            }}
                          />

                          <div className="image-uploads">
                            <div className="image-uploads-imgs">
                              {virtualImg &&
                                virtualImg.map((img, index) => (
                                  <div className="image-uploads-img">
                                    <img src={img} alt="img" />
                                    {img != "assets/img/icons/upload.svg" && (
                                      <div
                                        className="image-uploads-icon"
                                        onClick={(e) =>
                                          handleDeleteImage(index)
                                        }
                                      >
                                        <i class="fa-solid fa-xmark"></i>
                                      </div>
                                    )}
                                  </div>
                                ))}
                            </div>
                            <h4>Drag and drop a file to upload</h4>
                          </div>
                        </div>
                      </div>
                    </div>
                  </>
                )}

                {option == "sales" && (
                  <>
                    <div className="col-lg-3 col-sm-6 col-12">
                      <div className="form-group">
                        <label>dayStart</label>
                        <input
                          style={{
                            width: "100%",
                            padding: "6px",
                          }}
                          type="datetime-local"
                          onChange={(e) => {
                            setData((prevState) => {
                              prevState["dayStart"] = e.target.value;
                              return prevState;
                            });
                          }}
                        />
                      </div>
                    </div>
                    <div className="col-lg-3 col-sm-6 col-12">
                      <div className="form-group">
                        <label>dayEnd</label>
                        <input
                          style={{
                            width: "100%",
                            padding: "6px",
                          }}
                          type="datetime-local"
                          onChange={(e) => {
                            setData((prevState) => {
                              prevState["dayEnd"] = e.target.value;
                              return prevState;
                            });
                          }}
                        />
                      </div>
                    </div>
                  </>
                )}

                {option == "products" && (
                  <div className="col-lg-3 col-sm-6 col-12">
                    <div className="form-group">
                      <label>{"Services"}</label>
                      <Select
                        styles={{
                          control: (baseStyles, state) => ({
                            ...baseStyles,
                            borderColor: state.isFocused ? "grey" : "red",
                            height: "40px",
                          }),
                        }}
                        options={serivceData}
                        onChange={(selectedOption) => {
                          console.log(`Option selected:`, selectedOption);
                          setData((prevState) => {
                            prevState["serviceId"] = selectedOption.value;
                            return prevState;
                          });
                        }}
                      />
                    </div>
                  </div>
                )}

                {(option == "products" || option == "services") && (
                  <>
                    <div className="col-lg-12">
                      <div className="form-group">
                        <label>
                          {" "}
                          {option.charAt(0).toUpperCase() +
                            option.slice(1)}{" "}
                          Image
                        </label>
                        <div className="image-upload">
                          <input
                            type="file"
                            onChange={(e) => {
                              setData((prevState) => {
                                prevState["thumbnailFile"] = e.target.files[0];
                                setVirtualImg(
                                  URL.createObjectURL(e.target.files[0])
                                );
                                return prevState;
                              });
                            }}
                          />
                          <div className="image-uploads">
                            <div className="image-uploads-imgs">
                              {virtualImg && (
                                <div className="image-uploads-img">
                                  <img src={virtualImg} alt="img" />
                                  {virtualImg !=
                                    "assets/img/icons/upload.svg" && (
                                    <div className="image-uploads-icon">
                                      <i class="fa-solid fa-xmark"></i>
                                    </div>
                                  )}
                                </div>
                              )}
                            </div>
                            <h4>Drag and drop a file to upload</h4>
                          </div>
                        </div>
                      </div>
                    </div>
                  </>
                )}
                {option == "posts" && (
                  <>
                    <div className="col-lg-12">
                      <div className="form-group">
                        <label>Post Image</label>
                        <div className="image-upload">
                          <input
                            type="file"
                            multiple
                            onChange={(e) => {
                              let arr = [];
                              let files = [];
                              for (let file of e.target.files) {
                                arr.push(URL.createObjectURL(file));
                                files.push(file);
                              }
                              setVirtualImg(arr);
                              setData((prevState) => {
                                prevState["files"] = files;
                                return prevState;
                              });
                            }}
                          />

                          <div className="image-uploads">
                            <div className="image-uploads-imgs">
                              {virtualImg &&
                                virtualImg.map((img, index) => (
                                  <div className="image-uploads-img">
                                    <img src={img} alt="img" />
                                    {img != "assets/img/icons/upload.svg" && (
                                      <div
                                        className="image-uploads-icon"
                                        onClick={(e) =>
                                          handleDeleteImage(index)
                                        }
                                      >
                                        <i class="fa-solid fa-xmark"></i>
                                      </div>
                                    )}
                                  </div>
                                ))}
                            </div>
                            <h4>Drag and drop a file to upload</h4>
                          </div>
                        </div>
                      </div>
                    </div>
                  </>
                )}
                <div className="col-lg-12">
                  <button
                    style={{ marginRight: "12px" }}
                    className="btn btn-submit me-2"
                    onClick={addOption}
                  >
                    Submit
                  </button>
                  <Link className="btn btn-cancel" to={"/admin/" + option}>
                    Cancel
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddUser;
