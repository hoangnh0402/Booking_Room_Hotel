import slider1 from '../../assets/img/hero/hero-1.jpg'
import slider2 from '../../assets/img/hero/hero-2.jpg'
import slider3 from '../../assets/img/hero/hero-3.jpg'

import OwlCarousel from 'react-owl-carousel'
import 'owl.carousel/dist/assets/owl.carousel.css'
import 'owl.carousel/dist/assets/owl.theme.default.css'
import { Link } from 'react-router-dom'

// import "./owl.css";

const Slider = () => {
  return (
    <section className='hero-section'>
      <div className='container'>
        <div className='row'>
          <div className='col-lg-6'>
            <div className='hero-text'>
              <h1>HIT Hotel</h1>
              <p>
                Dưới đây là trang web đặt phòng khách sạn tốt nhất, bao gồm các đề xuất về tìm phòng
                khách sạn giá rẻ và dịch vụ.
              </p>
              <Link className='primary-btn'>Khám phá ngay</Link>
              {/* <a href="#">
              </a> */}
            </div>
          </div>
          <div className='col-xl-4 col-lg-5 offset-xl-2 offset-lg-1'>
            <div className='booking-form'>
              <h3>Đặt phòng khách sạn của bạn</h3>
              <form action='#'>
                <div className='check-date'>
                  <label htmlFor='date-in'>Check In:</label>
                  <input type='date' className='date-input' id='date-in' />
                  <i className='icon_calendar' />
                </div>
                <div className='check-date'>
                  <label htmlFor='date-out'>Check Out:</label>
                  <input type='date' className='date-input' id='date-out' />
                  <i className='icon_calendar' />
                </div>
                <div style={{ width: '100%' }} className='select-option'>
                  <label htmlFor='guest'>Guests:</label>
                  <select
                    className='custom-select'
                    style={{ width: '100%', height: '50px' }}
                    id='guest'>
                    <option defaultValue={1}>1 person</option>
                    <option defaultValue={2}>2 persons</option>
                    <option defaultValue={3}>3 persons</option>
                    <option defaultValue={4}>4 persons</option>
                    <option defaultValue={5}>5 persons</option>
                    <option defaultValue={6}>6 persons</option>
                    <option defaultValue={7}>7 persons</option>
                  </select>
                </div>
                <button type='button'>Check Availability</button>
              </form>
            </div>
          </div>
        </div>
      </div>
      <OwlCarousel
        style={{ position: 'absolute', top: '0' }}
        className='owl-main hero-slider'
        items={1}
        loop>
        <div className='item hs-item set-bg'>
          <img style={{ height: '100%' }} src={slider1} alt='' />
        </div>
        <div className='item item hs-item set-bg'>
          <img style={{ height: '100%' }} src={slider2} alt='' />
        </div>
        <div className='item item hs-item set-bg'>
          <img style={{ height: '100%' }} src={slider3} alt='' />
        </div>
      </OwlCarousel>

      {/* <div className="hero-slider owl-carousel">
        <div className="hs-item set-bg" data-setbg={slider1} />
        <div
          className="hs-item set-bg"
          data-setbg="../../assets/img/hero/hero-2.jpg"
        />
        <div className="hs-item set-bg" data-setbg="img/hero/hero-3.jpg" />
      </div> */}
    </section>
  )
}

export default Slider
