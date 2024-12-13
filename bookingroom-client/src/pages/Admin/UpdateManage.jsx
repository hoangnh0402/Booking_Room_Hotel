import AdminHeader from "../../components/AdminHeader";
import AdminSIdeBar from "../../components/AdminSideBar";
import { useDispatch } from "react-redux";
import {
  fetchCreateRoom,
  fetchGetRoom,
  fetchUpdateRoom,
} from "../../store/roomSlice/roomSlice";
import { unwrapResult } from "@reduxjs/toolkit";
import { useEffect, useState } from "react";

import salesInterface from "../../interfaces/sales.interface";
import roomsInterface from "../../interfaces/rooms.interface";
import usersInterface from "../../interfaces/users.interface";
import servicesInterface from "../../interfaces/service.interface.js";
import { useParams, useNavigate, Link } from "react-router-dom";
import {
  fetchGetHotelService,
  fetchUpdateHotelService,
} from "../../store/hotelServiceSlice/hotelServiceSlice";
import {
  fetchCreateProduct,
  fetchGetProduct,
  fetchUpdateProduct,
} from "../../store/productSlice/productSlice";
import productInterface from "../../interfaces/product.interface";
import { fetchGetSale, fetchUpdateSale } from "../../store/saleSlice/saleSlice";
import { fetchGetUser, fetchUpdateUser } from "../../store/userSlice/userSlice";
import Modal from "../../components/Modal/Modal";
import { fetchGetPost, fetchUpdatePost } from "../../store/postSlice/postSlice";
import postInterface from "../../interfaces/post.interface";
import "./addManage.scss";

const UpdateManage = () => {
  const dispatch = useDispatch();
  const [keys, setKeys] = useState([]);
  const [data, setData] = useState({});
  const navigate = useNavigate();
  let { option, optionId } = useParams();
  const [value, setValue] = useState("");
  const [displayModal, setDisplayModal] = useState(false);
  const [statusModal, setStatusModal] = useState("");
  const [messageModal, setMessageModal] = useState("");
  const [virtualImg, setVirtualImg] = useState([]);
  const [virtualOldImg, setVirtualOldImg] = useState([
    "assets/img/icons/upload.svg",
  ]);

  const callback = () => {
    setDisplayModal(false);
    setStatusModal("");
    setMessageModal("");
  };

  useEffect(() => {
    (async () => {
      let func;
      let fields;
      if (option == "rooms") {
        delete roomsInterface["type"];
        delete roomsInterface["id"];
        fields = [...Object.keys(roomsInterface)];
        setKeys([...Object.keys(roomsInterface)]);
        setData({ ...roomsInterface });
        func = fetchGetRoom(optionId);
      } else if (option == "sales") {
        delete salesInterface["id"];
        delete salesInterface["dayStart"];
        delete salesInterface["dayEnd"];
        fields = [...Object.keys(salesInterface)];
        setKeys([...Object.keys(salesInterface)]);
        setData({ ...roomsInterface });
        func = fetchGetSale(optionId);
      } else if (option == "users") {
        delete usersInterface["email"];
        delete usersInterface["password"];
        delete usersInterface["enabled"];
        delete usersInterface["pattern"];
        delete usersInterface["gender"];
        fields = [...Object.keys(usersInterface)];
        setKeys([...Object.keys(usersInterface)]);
        setData({ ...usersInterface });
        func = fetchGetUser(optionId);
      } else if (option == "services") {
        delete servicesInterface["id"];
        fields = [...Object.keys(servicesInterface)];
        setKeys([...Object.keys(servicesInterface)]);
        setData({ ...servicesInterface });
        func = fetchGetHotelService(optionId);
      } else if (option == "products") {
        fields = [...Object.keys(productInterface)];
        setKeys([...Object.keys(productInterface)]);
        setData({ ...productInterface });
        func = fetchGetProduct(optionId);
      } else if (option == "posts") {
        fields = [...Object.keys(postInterface)];
        setKeys([...Object.keys(postInterface)]);
        setData({ ...postInterface });
        func = fetchGetPost(optionId);
      }

      const result = await dispatch(func)
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          const data = originalPromiseResult.data;
          console.log(data);
          if (data?.medias) {
            setVirtualOldImg([...data.medias]);
            data["medias"] = data.medias.map((media) => media.id);
          }
          if (data?.thumbnail) {
            setVirtualOldImg(data?.thumbnail);
            // data["medias"] = data.medias.map((media) => media.id);
          }
          setData(data);
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError);
          // handle result here
        });
    })();
  }, [option]);

  const updateOption = async () => {
    let func;
    delete data["id"];

    if (option == "rooms") {
      if (!data.type) {
        data.type = "Standard Single";
      }
      data["mediaIds[]"] = data["medias"] ? data["medias"] : [];
      delete data["medias"];
      let files = data["files"] ? data["files"] : [];
      delete ["files"];
      let formData = new FormData();
      for (let file of files) {
        formData.append("files", file);
      }
      for (let i in data) {
        formData.append(i, data[i]);
      }
      func = fetchUpdateRoom({ roomId: optionId, updateRoomDto: formData });
    } else if (option == "sales") {
      func = fetchUpdateSale({ saleId: optionId, updateSaleDto: data });
    } else if (option == "users") {
      func = fetchUpdateUser({ userId: optionId, updateUserDto: data });
    } else if (option == "services") {
      let formData = new FormData();
      for (let i in data) {
        formData.append(i, data[i]);
      }
      func = fetchUpdateHotelService({
        serviceId: optionId,
        updateServiceDto: formData,
      });
    } else if (option == "products") {
      let formData = new FormData();
      for (let i in data) {
        formData.append(i, data[i]);
      }
      func = fetchUpdateProduct({
        productId: optionId,
        productUpdateDto: formData,
      });
    } else if (option == "posts") {
      console.log(data["medias"]);
      data["mediaIds[]"] = data["medias"] ? data["medias"] : [];
      console.log(data);
      delete data["medias"];
      let files = data["files"] ? data["files"] : [];
      delete ["files"];
      let formData = new FormData();
      for (let file of files) {
        formData.append("files", file);
      }
      for (let i in data) {
        formData.append(i, data[i]);
      }
      func = fetchUpdatePost({ postId: optionId, updatePostDto: formData });
    }
    const result = await dispatch(func)
      .then(unwrapResult)
      .then((originalPromiseResult) => {
        console.log("result", originalPromiseResult);
        if (originalPromiseResult.status == "SUCCESS") {
          setDisplayModal(true);
          setStatusModal("success");
          setMessageModal("Cập nhật thành công");
        } else {
          setDisplayModal(true);
          setStatusModal("failure");
          setMessageModal("Cập nhật thất bại");
        }
      })
      .catch((rejectedValueOrSerializedError) => {
        console.log(rejectedValueOrSerializedError);
        setDisplayModal(true);
        setStatusModal("failure");
        setMessageModal("Cập nhật thất bại");
        // handle result here
      });
  };

  const handleDeleteImage = (option, index) => {
    if (option == "old") {
      virtualOldImg.splice(index, 1);
      setVirtualOldImg([...virtualOldImg]);
      setData((prevState) => {
        data["medias"] = virtualOldImg.map((medias) => medias.id);
        return prevState;
      });
    } else if (option == "new") {
      virtualImg.splice(index, 1);
      setVirtualImg([...virtualImg]);
    }
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
              <h6>Update {option.charAt(0).toUpperCase() + option.slice(1)}</h6>
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
                              console.log(prevState);
                              return prevState;
                            });
                            setValue(e.target.value);
                          }}
                          type="text"
                          value={data[key]}
                        />
                      </div>
                    </div>
                  ))}
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
                          }}
                          onChange={(e) => {
                            console.log(e.target.value);
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
                              {virtualOldImg &&
                                virtualOldImg.map((img, index) => (
                                  <div className="image-uploads-img">
                                    <img src={img.url} alt="img" />
                                    <div
                                      className="image-uploads-icon"
                                      onClick={(e) =>
                                        handleDeleteImage("old", index)
                                      }
                                    >
                                      <i class="fa-solid fa-xmark"></i>
                                    </div>
                                  </div>
                                ))}
                              {virtualImg &&
                                virtualImg.map((img, index) => (
                                  <div className="image-uploads-img">
                                    <img src={img} alt="img" />
                                    <div
                                      className="image-uploads-icon"
                                      onClick={(e) =>
                                        handleDeleteImage("new", index)
                                      }
                                    >
                                      <i class="fa-solid fa-xmark"></i>
                                    </div>
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
                            setValue(e.target.value);
                          }}
                          value={data["dayStart"]}
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
                            setValue(e.target.value);
                          }}
                          value={data["dayEnd"]}
                        />
                      </div>
                    </div>
                  </>
                )}
                {(option == "services" || option == "products") && (
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
                                prevState["thumbnail"] = "";
                                setVirtualOldImg(
                                  URL.createObjectURL(e.target.files[0])
                                );
                                return prevState;
                              });
                            }}
                          />
                          <div className="image-uploads">
                            <div className="image-uploads-imgs">
                              {virtualOldImg && (
                                <div className="image-uploads-img">
                                  <img src={virtualOldImg} alt="img" />
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
                              {virtualOldImg &&
                                virtualOldImg.map((img, index) => (
                                  <div className="image-uploads-img">
                                    <img src={img.url} alt="img" />
                                    <div
                                      className="image-uploads-icon"
                                      onClick={(e) =>
                                        handleDeleteImage("old", index)
                                      }
                                    >
                                      <i class="fa-solid fa-xmark"></i>
                                    </div>
                                  </div>
                                ))}
                              {virtualImg &&
                                virtualImg.map((img, index) => (
                                  <div className="image-uploads-img">
                                    <img src={img} alt="img" />
                                    <div
                                      className="image-uploads-icon"
                                      onClick={(e) =>
                                        handleDeleteImage("new", index)
                                      }
                                    >
                                      <i class="fa-solid fa-xmark"></i>
                                    </div>
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
                <div style={{ marginTop: "30px" }} className="col-lg-12">
                  <button
                    style={{ marginRight: "12px" }}
                    className="btn btn-submit me-2"
                    onClick={updateOption}
                  >
                    Update
                  </button>
                  <Link className="btn btn-cancel" to={"/admin/" + option}>
                    Cancel
                  </Link>
                  {/* <button className="btn btn-cancel"></button> */}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UpdateManage;
