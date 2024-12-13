import Title from "../../components/Title";
import Introduction from "../../components/About-us/Introduction";
import Header from "../../components/Header";
import { useEffect, useState } from "react";
import { fetchGetPosts } from "../../store/postSlice/postSlice";
import { unwrapResult } from "@reduxjs/toolkit";
import { useDispatch } from "react-redux";
import { Link } from "react-router-dom";

const Blog = () => {
  const dispatch = useDispatch();
  const [blogs, setBlogs] = useState([]);

  useEffect(() => {
    (async () => {
      const result = await dispatch(fetchGetPosts())
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          if (originalPromiseResult.data) {
            setBlogs(originalPromiseResult.data.items);
            console.log(originalPromiseResult.data.items);
          }
          // console.log(blogs);
          // handle result here
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError);
          // handle result here
        });
    })();

    // return () => {}; // no-op
  }, []);

  return (
    <div>
      <Header />
      {/* <Title title="Rooms" /> */}
      {Title("Blog", "Blog Grid")}
      <section className="blog-section blog-page spad">
        <div className="container">
          <div className="row">
            {blogs &&
              blogs.map((blog) => (
                <div className="col-lg-4 col-md-6">
                  <div className="blog-item set-bg">
                    <img
                      style={{
                        width: "350px",
                        height: "425px",
                        objectFit: "cover",
                      }}
                      src={blog.medias?.[0]?.url}
                    />
                    <div className="bi-text">
                      <span className="b-tag">{"Hotel Blog"}</span>
                      <h4>
                        <Link to={"/blog-detail/" + blog.id}>{blog.title}</Link>
                      </h4>
                      <div className="b-time">
                        <i className="icon_clock_alt" /> {blog.createdDate}
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            <div className="col-lg-12">
              <div className="load-more">
                <a href="#" className="primary-btn">
                  Load More
                </a>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Blog;
