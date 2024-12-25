/* eslint-disable no-useless-concat */
import React, { useEffect, useState } from 'react'
import './billModal.scss'
import html2canvas from 'html2canvas'
import { jsPDF } from 'jspdf'

const BillModal = ({ displayModal, statusModal, messageModal, callback, booking, url }) => {
  const [display, setDisplay] = useState(displayModal)
  const [status, setStatus] = useState(statusModal)
  const [message, setMessage] = useState(messageModal)
  const printRef = React.useRef()

  const handleDownloadPdf = async () => {
    const element = printRef.current
    const canvas = await html2canvas(element)
    const data = canvas.toDataURL('image/png')

    const pdf = new jsPDF()
    const imgProperties = pdf.getImageProperties(data)
    const pdfWidth = pdf.internal.pageSize.getWidth()
    const pdfHeight = (imgProperties.height * pdfWidth) / imgProperties.width
    pdf.addImage(data, 'PNG', 0, 0, pdfWidth, 300)
    pdf.save('bill.pdf')
  }

  useEffect(() => {
    setDisplay(displayModal)
    setStatus(statusModal)
    setMessage(messageModal)
    // console.log(displayModal, statusModal, messageModal);
  }, [displayModal, statusModal, messageModal])

  console.log(booking)

  return (
    <div>
      <div
        style={{
          display: display ? 'block' : 'none',
          overflowX: 'scroll',
          // position: "relative",
        }}
        id='myModal'
        className={'modal' + ' fade ' + (display ? 'show' : '')}>
        <div className='modal-dialog modal-confirm'>
          <div className='modal-content' style={{ height: '600px', overflowX: 'scroll' }}>
            <button
              style={{ color: 'black', fontSize: '30px', zIndex: '10' }}
              type='button'
              className='close'
              data-dismiss='modal'
              aria-hidden='true'
              onClick={() => {
                callback()
              }}>
              ×
            </button>
            <div className='container mt-6 mb-7'>
              <div className='row justify-content-center'>
                <div className=''>
                  <div className='card'>
                    <div className='card-body p-5' ref={printRef}>
                      <h2>
                        Xin chào{' '}
                        {booking && Object.keys(booking).length > 0 && booking.createdBy.firstName},
                      </h2>
                      <p className='fs-sm'>Đây là hóa đơn thanh toán của bạn</p>
                      <div className='border-top border-gray-200 pt-4 mt-4'>
                        <div className='row'>
                          <div className='col-md-6'>
                            <div className='text-muted mb-2'>Payment No.</div>
                            <strong>
                              {booking && Object.keys(booking).length > 0 && booking.id}
                            </strong>
                          </div>
                          <div className='col-md-6 text-md-end'>
                            <div className='text-muted mb-2'>Payment Date</div>
                            <strong>
                              {booking &&
                                Object.keys(booking).length > 0 &&
                                booking.checkOut
                                  .concat(' - ')
                                  .concat(
                                    booking.lastModifiedBy.lastName +
                                      ' ' +
                                      booking.lastModifiedBy.firstName,
                                  )}
                            </strong>
                          </div>
                        </div>
                      </div>
                      <div className='border-top border-gray-200 mt-4 py-4'>
                        <div className='row'>
                          <div className='col-md-6'>
                            <div className='text-muted mb-2'>Client</div>
                            <strong>
                              {booking &&
                                Object.keys(booking).length > 0 &&
                                booking.createdBy.lastName.concat(
                                  ' ' + booking.createdBy.firstName,
                                )}{' '}
                            </strong>
                            {booking && Object.keys(booking).length > 0 && booking.rooms[0].name} -{' '}
                            {booking &&
                              Object.keys(booking).length > 0 &&
                              booking.rooms[0].price.toLocaleString('it-IT', {
                                style: 'currency',
                                currency: 'VND',
                              })}
                            {/* <p className="fs-sm">
                              989 5th Avenue, New York, 55832
                              <br />
                              <a href="#!" className="text-purple">
                                john@email.com
                              </a>
                            </p> */}
                          </div>
                          <div className='col-md-6 text-md-end'>
                            <div className='text-muted mb-2'>Payment To</div>
                            <strong>Hotel Booking</strong>
                            <p className="fs-sm">
                              Bac Tu Liem, Ha Noi
                              <br />
                              <a href="#!" className="text-purple">
                                nguyenhuyhoangpt0402@gmail.com
                              </a>
                            </p>
                          </div>
                        </div>
                      </div>
                      <table className='table border-bottom border-gray-200 mt-3'>
                        <thead>
                          <tr>
                            <th scope='col' className='fs-sm text-dark text-uppercase-bold-sm px-0'>
                              Description
                            </th>
                            <th
                              scope='col'
                              className='fs-sm text-dark text-uppercase-bold-sm text-end px-0'>
                              Amount
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                            <td className='px-0'>Services</td>
                            <td className='text-end px-0'>
                              {booking &&
                                Object.keys(booking).length > 0 &&
                                booking.totalServicePrice.toLocaleString('it-IT', {
                                  style: 'currency',
                                  currency: 'VND',
                                })}
                            </td>
                          </tr>
                          <tr>
                            <td className='px-0'>Rooms</td>
                            <td className='text-end px-0'>
                              {booking &&
                                Object.keys(booking).length > 0 &&
                                booking.totalRoomPrice.toLocaleString('it-IT', {
                                  style: 'currency',
                                  currency: 'VND',
                                })}
                            </td>
                          </tr>
                        </tbody>
                      </table>
                      <div className='mt-5'>
                        <div className='d-flex justify-content-end'>
                          <p className='text-muted me-3'>Subtotal:</p>
                          <span>
                            {booking &&
                              Object.keys(booking).length > 0 &&
                              (booking.totalRoomPrice + booking.totalServicePrice).toLocaleString(
                                'it-IT',
                                {
                                  style: 'currency',
                                  currency: 'VND',
                                },
                              )}
                          </span>
                        </div>
                        <div className='d-flex justify-content-end'>
                          <p className='text-muted me-3'>Surcharges:</p>
                          <span>
                            {(booking &&
                              Object.keys(booking).length > 0 &&
                              booking.surcharges
                                .reduce((total, item) => {
                                  let sc = 0
                                  if (item) {
                                    sc = item.roomSurcharge
                                  }
                                  return total + sc
                                }, 0)
                                .toLocaleString('it-IT', {
                                  style: 'currency',
                                  currency: 'VND',
                                })) ||
                              (0).toLocaleString('it-IT', {
                                style: 'currency',
                                currency: 'VND',
                              })}
                          </span>
                        </div>
                        <div className='d-flex justify-content-end mt-3'>
                          <h5 className='me-3'>Total:</h5>
                          <h5 className='text-success'>
                            {booking &&
                              Object.keys(booking).length > 0 &&
                              (
                                booking.totalRoomPrice +
                                booking.totalServicePrice +
                                booking.surcharges.reduce((total, item) => {
                                  let sc = 0
                                  if (item) {
                                    sc = item.roomSurcharge
                                  }
                                  return total + sc
                                }, 0)
                              ).toLocaleString('it-IT', {
                                style: 'currency',
                                currency: 'VND',
                              })}
                          </h5>
                        </div>
                      </div>
                    </div>
                    <a
                      href='#!'
                      className='btn btn-dark btn-lg card-footer-btn justify-content-center text-uppercase-bold-sm hover-lift-light'
                      onClick={handleDownloadPdf}>
                      <span className='svg-icon text-white me-2'>
                        <svg
                          xmlns='http://www.w3.org/2000/svg'
                          width={512}
                          height={512}
                          viewBox='0 0 512 512'>
                          <title>ionicons-v5-g</title>
                          <path
                            d='M336,208V113a80,80,0,0,0-160,0v95'
                            style={{
                              fill: 'none',
                              stroke: '#000',
                              strokeLinecap: 'round',
                              strokeLinejoin: 'round',
                              strokeWidth: '32px',
                            }}
                          />
                          <rect
                            x={96}
                            y={208}
                            width={320}
                            height={272}
                            rx={48}
                            ry={48}
                            style={{
                              fill: 'none',
                              stroke: '#000',
                              strokeLinecap: 'round',
                              strokeLinejoin: 'round',
                              strokeWidth: '32px',
                            }}
                          />
                        </svg>
                      </span>
                      Download Now
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div
        style={{ display: display ? 'block' : 'none' }}
        class={'modal-backdrop' + ' fade ' + (display ? 'show' : '')}></div>
    </div>
  )
}

export default BillModal
