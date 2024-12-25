/* eslint-disable jsx-a11y/anchor-is-valid */
import Title from '../../components/Title'
import Header from '../../components/Header'
import { useNavigate, useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { unwrapResult } from '@reduxjs/toolkit'
import {
  fetchGetRoom,
  fetchPostRoomRating,
  fetchRatingByRoomId,
} from '../../store/roomSlice/roomSlice'
import './roomDetail.scss'
import ScrollTop from '../../components/ScrollTop/ScrollTop'

const RoomDetail = () => {
  let { roomId } = useParams()
  const dispatch = useDispatch()
  const user = useSelector((state) => state.user.value)
  const navigate = useNavigate()
  const [room, setRoom] = useState({})
  const [roomRating, setRoomRating] = useState([])
  const [star, setStar] = useState(0)
  const [comment, setComment] = useState('')

  let checkValidTime = (dayStart, dayEnd) => {
    return new Date(dayStart).getTime() <= Date.now() && new Date(dayEnd).getTime() >= Date.now()
  }

  const d = new Date()
  const day = d.getDate()
  const month = d.getMonth()
  const year = d.getFullYear()

  useEffect(() => {
    ;(async () => {
      const result = await dispatch(fetchGetRoom(roomId))
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          setRoom(originalPromiseResult.data)
          console.log(originalPromiseResult.data)
          // console.log(originalPromiseResult.data);
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError)
          // handle result here
        })
      const result2 = await dispatch(fetchRatingByRoomId(roomId))
        .then(unwrapResult)
        .then((originalPromiseResult) => {
          setRoomRating(originalPromiseResult.data.items)
          console.log(originalPromiseResult.data.items)
        })
        .catch((rejectedValueOrSerializedError) => {
          console.log(rejectedValueOrSerializedError)
          // handle result here
        })
    })()
  }, [roomId])

  const handleEvaluate = (star) => {
    setStar(5 - star)
  }

  const oldStars = (star) => {
    let ans = []

    for (let i = 0; i < 5; i++) {
      ans.push(
        <i style={{ color: i < star ? '#f5b917' : '' }} class='fa-sharp fa-regular fa-star'></i>,
      )
    }

    return ans
  }

  const stars = (borderColor = 'black') => {
    let ans = []
    for (let i = 0; i < 5; i++) {
      ans.push(
        <input
          style={{ borderColor }}
          type='radio'
          name='star'
          onClick={() => {
            handleEvaluate(i)
          }}
        />,
      )
    }
    return ans
  }

  const handleComment = async () => {
    const result = await dispatch(
      fetchPostRoomRating({
        crateRoomRatingDto: { star, comment },
        roomId: roomId,
      }),
    )
      .then(unwrapResult)
      .then((originalPromiseResult) => {
        // setComment((prev) => [...prev, originalPromiseResult.data]);
        setStar('')
        setComment('')
        // console.log(originalPromiseResult);
        setRoomRating([...roomRating, originalPromiseResult.data])
        // console.log(originalPromiseResult);
      })
      .catch((rejectedValueOrSerializedError) => {
        console.log(rejectedValueOrSerializedError)
        // handle result here
      })
  }
  const [currentIndex, setCurrentIndex] = useState(0)

  useEffect(() => {
    if (!room?.medias || room?.medias.length === 0) return // Nếu dữ liệu chưa sẵn sàng, thoát khỏi useEffect

    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % room?.medias.length)
    }, 3000) // Tự động chuyển slide sau 3 giây

    // Cleanup khi component bị unmount
    return () => clearInterval(interval)
  }, [room?.medias])

  return (
    <div>
      <Header />
      {Title('Our Rooms', 'Rooms')}
      {/* Breadcrumb Section End */}
      {/* Rooms Section Begin */}
      <section className='room-details-section spad'>
        <div className='container'>
          <div className='row'>
            <div className='col-lg-8'>
              <div className='room-details-item'>
                {/* <OwlCarousel
                  style={{
                    // position: "absolute",
                    top: '0',
                    width: '100%',
                  }}
                  className='owl-main hero-slider'
                  items={1}
                  loop>
                  {room?.medias?.map((media) => {
                    return (
                      <div className='item hs-item set-bg'>
                        <img
                          style={{ width: '100%', height: '450px', borderRadius: '8px' }}
                          src={media.url}
                          alt=''
                        />
                      </div>
                    )
                  })}
                </OwlCarousel> */}
                <div
                  style={{
                    overflow: 'hidden',
                    position: 'relative',
                    width: '100%',
                  }}
                  className='hero-slider'>
                  <div
                    style={{
                      display: 'flex',
                      transform: `translateX(-${currentIndex * 100}%)`,
                      transition: 'transform 0.5s ease-in-out',
                    }}>
                    {room?.medias?.map((media, index) => (
                      <div
                        key={index}
                        className='item hs-item set-bg'
                        style={{
                          flex: '0 0 100%', // Slide chiếm 100% chiều rộng
                          maxWidth: '100%',
                        }}>
                        <img
                          style={{ width: '100%', height: '450px', borderRadius: '8px' }}
                          src={media.url}
                          alt=''
                        />
                      </div>
                    ))}
                  </div>
                </div>
                {/* <img
                  style={{ width: "100%", height: "450px" }}
                  src={room && room.medias?.[0]?.url}
                  alt=""
                /> */}
                <div className='rd-text'>
                  <div className='rd-title'>
                    <h3 style={{ fontSize: '24px' }}>{room.name}</h3>
                    <div className='rdt-right'>
                      <div className='rating'>
                        <i class='fas fa-star'></i>
                        <i class='fas fa-star'></i>
                        <i class='fas fa-star'></i>
                        <i class='fas fa-star'></i>
                        <i class='fas fa-star-half'></i>
                      </div>
                      <button onClick={() => navigate('/booking')} className='room-detail__booking'>
                        Đặt ngay
                      </button>
                    </div>
                  </div>
                  <h2>
                    <div style={{ display: 'flex' }}>
                      <h3
                        style={{
                          color: checkValidTime(room.sale?.dayStart, room.sale?.dayEnd)
                            ? 'rgba(0,0,0,.54)'
                            : '#5892b5',
                          textDecoration: checkValidTime(room.sale?.dayStart, room.sale?.dayEnd)
                            ? 'line-through'
                            : 'none',
                        }}>
                        {room &&
                          room?.price?.toLocaleString('it-IT', {
                            style: 'currency',
                            currency: 'VND',
                          })}
                      </h3>
                      {checkValidTime(room.sale?.dayStart, room.sale?.dayEnd) && (
                        <h3 style={{ marginLeft: '12px', color: '#5892b5' }}>
                          {room &&
                            (
                              room.price -
                              (room.price * room.sale.salePercent) / 100
                            ).toLocaleString('it-IT', {
                              style: 'currency',
                              currency: 'VND',
                            })}
                        </h3>
                      )}
                    </div>
                    {room.sale?.salePercent != null &&
                      checkValidTime(room.sale?.dayStart, room.sale?.dayEnd) && (
                        <h4
                          style={{
                            position: 'absolute',
                            top: '12px',
                            right: '15px',
                            fontSize: '24px !important',
                            color: 'rgb(88, 146, 181)',
                            fontWeight: 'bold',
                            zIndex: 1,
                            background: 'white',
                          }}>
                          Giảm giá {room.sale.salePercent} %
                        </h4>
                      )}
                  </h2>
                  <table>
                    <tbody>
                      <tr>
                        <td className='r-o'>Type:</td>
                        <td>{room.type}</td>
                      </tr>
                      <tr>
                        <td className='r-o'>Capacity:</td>
                        <td>Max persion {room.capacity}</td>
                      </tr>
                      <tr>
                        <td className='r-o'>Bed:</td>
                        <td>{room.bed}</td>
                      </tr>
                      <tr>
                        <td className='r-o'>Size:</td>
                        <td>{room.size}</td>
                      </tr>
                    </tbody>
                  </table>
                  <p className='f-para'>{room.description}</p>
                  <div className='room-detail__services'>
                    <p>
                      <strong style={{ fontWeight: 'bold' }}>Các tiện ích đi kèm: </strong>
                      {room.services}
                    </p>
                  </div>
                </div>
              </div>
              <div className='rd-reviews'>
                <h4>Reviews</h4>
                {roomRating &&
                  roomRating.map((rating) => (
                    <div className='review-item'>
                      <div style={{}} className='evaluate'>
                        <div className='star flex flex-row-reverse'>{oldStars(rating.star)}</div>

                        <p
                          style={{
                            fontSize: '18px',
                            fontWeight: '400',
                            textAlign: 'center',
                            paddingBottom: '12px',
                          }}></p>
                      </div>
                      <div className='ri-pic'>
                        <img src={rating.createdBy?.avatar} alt='' />
                      </div>
                      <div className='ri-text'>
                        {/* <span>27 Aug 2019</span> */}
                        <div className='rating'>
                          <i class='fas fa-star'></i>
                          <i class='fas fa-star'></i>
                          <i class='fas fa-star'></i>
                          <i class='fas fa-star'></i>
                          <i class='fas fa-star-half'></i>
                        </div>
                        <h5>
                          {rating.createdBy?.lastName.concat(' ' + rating.createdBy.firstName)}
                        </h5>
                        <p>{rating.comment}</p>
                      </div>
                    </div>
                  ))}
              </div>
              <div className='rd-reviews'>
                <h4>Add Review</h4>
                <div style={{ display: 'flex', alignItems: 'center' }} className='review-item'>
                  <h5>You Rating:</h5>
                  <div style={{ marginLeft: '44px' }} className='evaluate'>
                    <div className='star flex flex-row-reverse '>{stars()}</div>

                    <p
                      style={{
                        fontSize: '18px',
                        fontWeight: '400',
                        textAlign: 'center',
                        paddingBottom: '12px',
                      }}></p>
                  </div>
                </div>
                <div className='review-item'>
                  <div className='ri-pic'>
                    <img src={Object.keys(user).length > 0 && user.avatar} alt='' />
                  </div>
                  <div className='ri-text'>
                    <span>{day + '/' + (month + 1) + '/' + year}</span>
                    <div className='rating'>
                      <i class='fas fa-star'></i>
                      <i class='fas fa-star'></i>
                      <i class='fas fa-star'></i>
                      <i class='fas fa-star'></i>
                      <i class='fas fa-star-half'></i>
                    </div>
                    <h5>
                      {Object.keys(user).length > 0 && user.lastName.concat(' ' + user.firstName)}
                    </h5>
                    <textarea
                      style={{
                        width: '100%',
                        padding: '12px',
                        borderColor: '#E5E5E5',
                      }}
                      value={comment}
                      onChange={(e) => setComment(e.target.value)}
                      placeholder='Your Review'
                    />
                  </div>
                </div>
                <a
                  style={{
                    display: 'inline-block',
                    color: '#ffffff',
                    fontSize: '13px',
                    textTransform: 'uppercase',
                    fontWeight: 700,
                    background: '#5892b5',
                    padding: '14px 28px 13px',
                  }}
                  onClick={handleComment}>
                  Submit Now
                </a>
              </div>
            </div>
            <div className='col-lg-4'>
              <div className='room-booking'>
                <h3>Your Reservation</h3>
                <form action='#'>
                  <div className='check-date'>
                    <label htmlFor='date-in'>Check In:</label>
                    <input type='date' className='date-input' id='date-in' />
                  </div>
                  <div className='check-date'>
                    <label htmlFor='date-out'>Check Out:</label>
                    <input type='date' className='date-input' id='date-out' />
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
      </section>
      <ScrollTop/>
    </div>
  )
}

export default RoomDetail
