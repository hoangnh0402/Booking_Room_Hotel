import { useSelector } from 'react-redux'
import { Link } from 'react-router-dom'
import { useLocation } from 'react-router-dom'
import storageService from '../services/storage.service'
import logo from '../assets/img/hit-hotel.jpg'

const Header = () => {
  const location = useLocation()
  const pathName = location.pathname
  const user = useSelector((state) => state.user.value)
  const token = storageService.get('token')
  let isActive = false

  // useEffect(async () => {}, []);
  // ...

  return (
    <header className='header-section'>
      <div className='top-nav'>
        <div className='container'>
          <div className='row'>
            <div className='col-lg-6'>
              <ul className='tn-left'>
                <li>
                  <i className='fa fa-phone' /> +84379327548
                </li>
                <li>
                  <i className='fa fa-envelope' /> nguyenhuyhoangpt0402@gmail.com
                </li>
              </ul>
            </div>
            <div className='col-lg-6'>
              <div className='tn-right'>
                <Link className='bk-btn' to='/booking'>
                  Booking Now
                </Link>
                <div className='language-option'>
                  {!token && (
                    <Link className='bk-btn' to='/auth/login'>
                      Login
                    </Link>
                  )}
                  {token && (
                    <>
                      <img src={user && user.avatar} alt='' />
                      <span>
                        {user?.firstName?.concat(' ' + user?.lastName)}{' '}
                        <i className='fa fa-angle-down' />
                      </span>
                      <div style={{ width: '200px' }} className='flag-dropdown'>
                        <ul>
                          <li style={{ padding: '8px' }}>
                            <Link to='/user'>My Account</Link>
                          </li>
                          {user.roleName === 'ROLE_ADMIN' && (
                            <li style={{ padding: '8px' }}>
                              <Link to='/admin/users'>Manament</Link>
                            </li>
                          )}
                          <li style={{ padding: '8px' }}>
                            <Link to='/booking-cart'>Booking Cart</Link>
                          </li>
                          <li
                            onClick={() => {
                              storageService.remove('token')
                              // window.location.href = "/auth/login";
                            }}
                            style={{ padding: '8px' }}>
                            <Link to='/auth/login'>Logout</Link>
                          </li>
                        </ul>
                      </div>
                    </>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className='menu-item'>
        <div className='container'>
          <div className='row'>
            <div className='col-lg-2'>
              <div className='logoo' style={{ width: '100px', height: '100px' }}>
                <Link to='/' style={{ display: 'block', width: '200%', height: '100%' }}>
                  <img style={{ width: '100%', height: '100%' }} src={logo} alt='' />
                </Link>
              </div>
            </div>
            <div className='col-lg-10'>
              <div className='nav-menu'>
                <nav className='mainmenu'>
                  <ul>
                    <li className={pathName === '/' ? 'active' : ''}>
                      <Link to={'/'}>HOME</Link>
                    </li>
                    <li className={pathName === '/rooms' ? 'active' : ''}>
                      <Link to={'/rooms'}>ROOMS</Link>
                    </li>
                    <li className={pathName === '/about-us' ? 'active' : ''}>
                      <Link to={'/about-us'}>ABOUT US</Link>
                    </li>
                    <li>
                      <Link to={'/blogs'}>BLOGS</Link>
                    </li>
                    {/* <li>
                      <a href="./contact.html">Contact</a>
                    </li> */}
                  </ul>
                </nav>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
  )
}

export default Header
