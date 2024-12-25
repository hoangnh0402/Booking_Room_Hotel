import aboutUs1 from '../../assets/img/about/about-p1.jpg'
import aboutUs2 from '../../assets/img/about/about-p2.jpg'
import aboutUs3 from '../../assets/img/about/about-p3.jpg'
import ScrollTop from '../ScrollTop/ScrollTop'
import './Introduction.scss'
const Introduction = () => {
  return (
    <>
      <section className='about-section'>
        <div className='container'>
          <div className='about-content'>
            <div className='about-grid'>
              <div className='about-info'>
                <div className='about-title'>
                  <h2>Welcome To HIT Hotel.</h2>
                  <p>
                    HIT Hotel nơi cung cấp các phòng nghỉ tiện nghi, sang trọng và các dịch vụ tiện
                    ích cho quý khách hàng khi trải nghiệm dừng chân tại đây. Định hướng của HIT
                    Hotel mong muốn trở thành khách sạn đáng tin cậy đối với khách hàng, chủ sở hữu,
                    nhân viên và cộng đồng với các dịch vụ tận tình và chu đáo mang lại sự thư giãn
                    và thoải mái nhất.
                  </p>
                </div>
              </div>
              <div className='about-features'>
                <ul className='features-list'>
                  <li>
                    <span className='feature-icon'>✦</span>
                    Giảm 20% cho chỗ ở.
                  </li>
                  <li>
                    <span className='feature-icon'>✦</span>
                    Bữa sáng hàng ngày miễn phí
                  </li>
                  <li>
                    <span className='feature-icon'>✦</span>
                    Wifi miễn phí.
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div className='services-grid'>
            <div className='service-card'>
              <div className='service-image'>
                <img src={aboutUs1} alt='Restaurant services at Thanh Huong Hotel' />
                <div className='service-overlay'>
                  <h3>Restaurants Services</h3>
                </div>
              </div>
            </div>
            <div className='service-card'>
              <div className='service-image'>
                <img src={aboutUs2} alt='Travel and camping services' />
                <div className='service-overlay'>
                  <h3>Travel &amp; Camping</h3>
                </div>
              </div>
            </div>
            <div className='service-card'>
              <div className='service-image'>
                <img src={aboutUs3} alt='Event and party services' />
                <div className='service-overlay'>
                  <h3>Event &amp; Party</h3>
                </div>
              </div>
            </div>
          </div>
          <div className='testimonial-section'>
            <h2>What Our Guests Say</h2>
            <div className='testimonial-grid'>
              <div className='testimonial-card'>
                <p>Dịch vụ tuyệt vời, phòng ốc sạch sẽ và thoải mái. Chắc chắn sẽ quay lại!</p>
                <div className='testimonial-author'>- Nguyễn Văn A</div>
              </div>
              <div className='testimonial-card'>
                <p>Nhân viên thân thiện, vị trí thuận tiện. Trải nghiệm tuyệt vời!</p>
                <div className='testimonial-author'>- Trần Thị B</div>
              </div>
            </div>
          </div>
          <div className='stats-section'>
            <div className='stat-item'>
              <div className='stat-number'>1000+</div>
              <div className='stat-label'>Khách hàng hài lòng</div>
            </div>
            <div className='stat-item'>
              <div className='stat-number'>50+</div>
              <div className='stat-label'>Phòng sang trọng</div>
            </div>
            <div className='stat-item'>
              <div className='stat-number'>5</div>
              <div className='stat-label'>Năm kinh nghiệm</div>
            </div>
          </div>
          <div className='cta-section'>
            <h2>Trải Nghiệm Sự Sang Trọng</h2>
            <p>Đặt phòng ngay hôm nay để nhận ưu đãi đặc biệt!</p>
            <button className='cta-button'>Đặt Phòng Ngay</button>
          </div>
        </div>
      </section>
      <ScrollTop />
    </>
  )
}

export default Introduction
