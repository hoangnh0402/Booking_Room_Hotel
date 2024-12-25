/* eslint-disable jsx-a11y/alt-text */
/* eslint-disable array-callback-return */
import { Link } from 'react-router-dom'

const RoomIntroduction = ({ rooms }) => {
  return (
    <section className='hp-room-section'>
      <div className='container-fluid'>
        <div className='hp-room-items'>
          <div className='row'>
            {rooms &&
              rooms.map((room, index) => {
                if (index <= 3) {
                  return (
                    <div className='col-lg-3 col-md-6'>
                      <div className='hp-room-item set-bg'>
                        <img
                          style={{ height: '100%', objectFit: 'cover' }}
                          src={room.medias?.[0]?.url}
                        />
                        <div className='hr-text'>
                          <h3>{room.title}</h3>
                          <h2>
                            {room.price.toLocaleString('it-IT', {
                              style: 'currency',
                              currency: 'VND',
                            })}
                            <span>/Pernight</span>
                            {/* {room.price}$<span>/Pernight</span> */}
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
                          <Link className='primary-btn' to={'/room-detail/' + room.id}>
                            More Details
                          </Link>
                        </div>
                      </div>
                    </div>
                  )
                }
              })}
          </div>
        </div>
      </div>
    </section>
  )
}

export default RoomIntroduction
