const Title = (title, page) => {
  return (
    <div className="breadcrumb-section">
      <div className="container">
        <div className="row">
          <div className="col-lg-12">
            <div className="breadcrumb-text">
              <h2>{title}</h2>
              <div className="bt-option">
                <a href="./home.html">Home</a>
                <span>{page}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Title;
