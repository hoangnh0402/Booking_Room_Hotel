/* eslint-disable array-callback-return */
import { Link } from 'react-router-dom'

const Blog = ({ posts }) => {
  console.log(posts)
  const setBg = (img) => {
    return {
      backgroundImage: `url(${img})`,
      backgroundSize: 'cover',
      backgroundRepeat: 'no-repeat',
      backgroundPosition: 'center',
    }
  }
  return (
    <section className='blog-section spad'>
      <div className='container'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='section-title'>
              <span>Hotel News</span>
              <h2>Our Blog &amp; Event</h2>
            </div>
          </div>
        </div>
        <div className='row'>
          {posts &&
            posts.map((post, index) => {
              if (index <= 5) {
                return (
                  <div className='col-lg-4'>
                    {/* <div className="blog-item set-bg" style={setBg(blogImage1)}> */}
                    <div className='blog-item set-bg'>
                      <div style={{ width: '100%', height: '100%' }}>
                        <img
                          style={{
                            width: '100%',
                            height: '100%',
                            objectFit: 'cover',
                          }}
                          src={post.medias[0].url}
                        />
                      </div>
                      <div className='bi-text'>
                        <span className='b-tag'>Hotel Booking</span>
                        <h4>
                          <Link to={'/blog-detail/' + post.id}>{post.title}</Link>
                        </h4>
                        <div className='b-time'>
                          <i className='icon_clock_alt' /> {post.createdDate}
                        </div>
                      </div>
                    </div>
                  </div>
                )
              }
            })}
        </div>
      </div>
    </section>
  )
}

export default Blog
