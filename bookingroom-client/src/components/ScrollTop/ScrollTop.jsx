import { useEffect, useState } from 'react'
import './ScrollTop.scss'
const ScrollTop = () => {
  const [showScrollTop, setShowScrollTop] = useState(false)

  const handleScroll = () => {
    if (window.scrollY > 200) {
      setShowScrollTop(true)
    } else {
      setShowScrollTop(false)
    }
  }
  useEffect(() => {
    window.addEventListener('scroll', handleScroll)
    return () => {
      window.removeEventListener('scroll', handleScroll)
    }
  }, [])
  const scrollTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }
  return (
    <>
      {showScrollTop && (
        <div className='scrollTop' onClick={scrollTop}>
          <i class='fas fa-square-caret-up'></i>
        </div>
      )}
    </>
  )
}

export default ScrollTop
