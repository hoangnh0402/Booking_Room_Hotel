import React, { useState, useEffect } from "react";
import { Helmet } from "react-helmet";
import Service from "../components/Home/Service";
import About from "../components/Home/About";
import Slider from "../components/Home/Slider";
import RoomIntroduction from "../components/Home/Room-Introduction";
import Testimonials from "../components/Home/Testimonials";
import Header from "../components/Header";
import Footer from "../components/Footer";
import Blog from "../components/Home/Blog";
import { useDispatch } from "react-redux";
import { fetchGetAvailableRooms } from "../store/roomSlice/roomSlice";
import { unwrapResult } from "@reduxjs/toolkit";
import { fetchGetHotelService } from "../store/hotelServiceSlice/hotelServiceSlice";
import { fetchGetPosts } from "../store/postSlice/postSlice";

const Home = () => {
  const dispatch = useDispatch();
  const [rooms, setRooms] = useState([]);
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    (async () => {
      const result = await dispatch(fetchGetAvailableRooms({}))
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          console.log(originalPromiseResult);
          setRooms(originalPromiseResult.data.items);
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError);
        });

      const result2 = await dispatch(fetchGetPosts({}))
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          console.log(originalPromiseResult);
          setPosts(originalPromiseResult.data.items);
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError);
        });
    })();

    // return () => {}; // no-op
  }, []);
  return (
    <div>
      <Header />
      <Slider />
      <About />
      {/* <Service /> */}
      <RoomIntroduction rooms={rooms} />
      <Testimonials />
      <Blog posts={posts} />
      <Footer />
    </div>
  );
};

export default Home;
