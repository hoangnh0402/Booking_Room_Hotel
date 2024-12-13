import aboutUs1 from "../../assets/img/about/about-p1.jpg";
import aboutUs2 from "../../assets/img/about/about-p2.jpg";
import aboutUs3 from "../../assets/img/about/about-p3.jpg";

const Introduction = () => {
  return (
    <section className="aboutus-page-section spad">
      <div className="container">
        <div className="about-page-text">
          <div className="row">
            <div className="col-lg-6">
              <div className="ap-title">
                <h2>Welcome To Thanh Huong Hotel.</h2>
                <p>
                  Thanh Huong Hotel nơi cung cấp các phòng nghỉ tiện nghi, sang
                  trọng và các dịch vụ tiện ích cho quý khách hàng khi trải
                  nghiệm dừng chân tại đây .Định hướng của Thanh Huong Hotel
                  mong muốn trở thành khách sạn đáng tin cậy đối với khách hàng,
                  chủ sở hữu, nhân viên và cộng đồng với các dịch vụ tận tình và
                  chu đáo mang lại sự thư giãn và thoải mái nhất.
                </p>
              </div>
            </div>
            <div className="col-lg-5 offset-lg-1">
              <ul className="ap-services">
                <li style={{ listStyleType: "disc" }}>Giảm 20% cho chỗ ở.</li>
                <li style={{ listStyleType: "disc" }}>
                  Bữa sáng hàng ngày miễn phí
                </li>
                {/* <li>
                   3 Pcs Laundry Per Day
                </li> */}
                <li style={{ listStyleType: "disc" }}>Wifi miễn phí.</li>
                {/* <li>
                   Discount 20% On F&amp;B
                </li> */}
              </ul>
            </div>
          </div>
        </div>
        <div className="about-page-services">
          <div className="row">
            <div className="col-md-4">
              <div className="ap-service-item set-bg">
                <img src={aboutUs1} alt="" />
                <div className="api-text">
                  <h3>Restaurants Services</h3>
                </div>
              </div>
            </div>
            <div className="col-md-4">
              <div className="ap-service-item set-bg">
                <img src={aboutUs2} alt="" />
                <div className="api-text">
                  <h3>Travel &amp; Camping</h3>
                </div>
              </div>
            </div>
            <div className="col-md-4">
              <div className="ap-service-item set-bg">
                <img src={aboutUs3} alt="" />
                <div className="api-text">
                  <h3>Event &amp; Party</h3>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Introduction;
