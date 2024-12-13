import TestimonialLogo from "../../assets/img/testimonial-logo.png";

import OwlCarousel from "react-owl-carousel";
import "owl.carousel/dist/assets/owl.carousel.css";
import "owl.carousel/dist/assets/owl.theme.default.css";

const Testimonials = () => {
  return (
    <section className="testimonial-section spad">
      <div className="container">
        <div className="row">
          <div className="col-lg-12">
            <div className="section-title">
              <span>Testimonials</span>
              <h2>What Customers Say?</h2>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-lg-8 offset-lg-2">
            <OwlCarousel
              //   style={{ position: "absolute", top: "0" }}
              className="testimonial-slider owl-carousel"
              items={1}
              loop
            >
              <div className="ts-item">
                <p>
                  Khách sạn đã mang đến những trải nghiệm tuyệt vời khi đặt
                  phòng , các dịch vụ cung cấp khá đầy đủ và hấp dẫn đối với
                  thực khách. Tôi nghĩ sẽ thuận tiện hơn khi các bạn bổ sung
                  thêm các chức năng mới hơn nâng cao chất lượng cho khách khi
                  đặt phòng tại Thanh Huong Hotel!
                </p>
                <div className="ti-author">
                  <div className="rating">
                    <i className="icon_star" />
                    <i className="icon_star" />
                    <i className="icon_star" />
                    <i className="icon_star" />
                    <i className="icon_star-half_alt" />
                  </div>
                  <h5> - Huong Nguyen</h5>
                </div>
                <img src={TestimonialLogo} alt="" />
              </div>
              <div className="ts-item">
                <p>
                  Khách sạn đã mang đến những trải nghiệm tuyệt vời khi đặt
                  phòng , các dịch vụ cung cấp khá đầy đủ và hấp dẫn đối với
                  thực khách. Tôi nghĩ sẽ thuận tiện hơn khi các bạn bổ sung
                  thêm các chức năng mới hơn nâng cao chất lượng cho khách khi
                  đặt phòng tại Thanh Huong Hotel!
                </p>
                <div className="ti-author">
                  <div className="rating">
                    <i className="icon_star" />
                    <i className="icon_star" />
                    <i className="icon_star" />
                    <i className="icon_star" />
                    <i className="icon_star-half_alt" />
                  </div>
                  <h5> - Huong Nguyen</h5>
                </div>
                <img src={TestimonialLogo} alt="" />
              </div>
            </OwlCarousel>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Testimonials;
