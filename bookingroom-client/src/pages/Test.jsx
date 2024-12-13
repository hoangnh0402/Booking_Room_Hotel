import axios from "axios";
import React from "react";
import { useState, useEffect, useRef } from "react";

const Test = () => {
  const [inputValue, setInputValue] = useState("");
  const [page, setPage] = useState(1);
  const [photos, setPhotos] = useState([]);
  const pageRef = useRef([]);

  useEffect(() => {
    (async () => {
      try {
        const res = await axios.get(
          `https://picsum.photos/v2/list?page=${page}&limit=8`
        );
        pageRef.current = res.data;
        console.log(pageRef.current);
        return res.data;
      } catch (err) {
        console.error(err);
      }
    })();
  }, [page]);

  return (
    <>
      <button onClick={() => setPage(page + 1)}>Load more</button>
    </>
  );
};

export default Test;
