const Footer = () => {
  return (
    <footer className="footer-section">
      <div className="container">
        <div className="footer-text">
          <div className="row">
            <div className="col-lg-4">
              <div className="ft-about">
                <div className="logo">
                  <a href="#">
                    <img src="img/footer-logo.png" alt="" />
                  </a>
                </div>
                <p>Chúng tôi truyền cảm hứng và tiếp cận hàng triệu du khách</p>
                {/* <div className="fa-social">
                  <a href="#">
                    <i className="fa fa-facebook" />
                  </a>
                  <a href="#">
                    <i className="fa fa-twitter" />
                  </a>
                  <a href="#">
                    <i className="fa fa-tripadvisor" />
                  </a>
                  <a href="#">
                    <i className="fa fa-instagram" />
                  </a>
                  <a href="#">
                    <i className="fa fa-youtube-play" />
                  </a>
                </div> */}
              </div>
            </div>
            <div className="col-lg-3 offset-lg-1">
              <div className="ft-contact">
                <h6>Contact Us</h6>
                <ul>
                  <li>+84379327548</li>
                  <li>nguyenhuyhoangpt0402@gmail.com</li>
                  <li>Minh Khai, Bac Tu Liem, Ha Noi</li>
                </ul>
              </div>
            </div>
            <div className="col-lg-3 offset-lg-1">
              <div className="ft-newslatter">
                <h6>New latest</h6>
                {/* <p>Get the latest updates and offers.</p> */}
                <form action="#" className="fn-form">
                  <input type="text" placeholder="Email" />
                  <button type="submit">
                    <i className="fa fa-send" />
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
