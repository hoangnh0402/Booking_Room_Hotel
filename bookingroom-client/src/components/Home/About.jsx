/* eslint-disable jsx-a11y/anchor-is-valid */
import about1 from "../../assets/img/about/about-1.jpg";
import about2 from "../../assets/img/about/about-2.jpg";

const About = () => {
  return (
    <section className="aboutus-section spad">
      <div className="container">
        <div className="row">
          <div className="col-lg-6">
            <div className="about-text">
              <div className="section-title">
                <span>About Us</span>
                <h2>THDD Hotel</h2>
              </div>
              <p className="f-para">
                THDD Hotel nơi cung cấp các phòng nghỉ tiện nghi, sang
                trọng và các dịch vụ tiện ích cho quý khách hàng khi trải nghiệm
                dừng chân tại đây.
              </p>
              <p className="s-para">
                Định hướng của THDD Hotel mong muốn trở thành khách sạn
                đáng tin cậy đối với khách hàng, chủ sở hữu, nhân viên và cộng
                đồng với các dịch vụ tận tình và chu đáo mang lại sự thư giãn và
                thoải mái nhất.
              </p>
              <a href="#" className="primary-btn about-btn">
                Đọc thêm
              </a>
            </div>
          </div>
          <div className="col-lg-6">
            <div className="about-pic">
              <div className="row">
                <div className="col-sm-6">
                  <img src={about1} alt="" />
                </div>
                <div className="col-sm-6">
                  <img src={about2} alt="" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default About;
