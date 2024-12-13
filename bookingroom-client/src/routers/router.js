import Home from "../pages/Home";
import Login from "../pages/Auth/Login";
import Register from "../pages/Auth/Register";
import Rooms from "../pages/Room/Rooms";
import User from "../pages/User/User";
import AboutUs from "../pages/About-us";
import Blog from "../pages/Blog/Blog";
import BlogDetail from "../pages/Blog/BlogDetail";
import RoomDetail from "../pages/Room/RoomDetail";
import Manage from "../pages/Admin/Manage";
import AddManage from "../pages/Admin/AddManage";
import Booking from "../pages/Booking/Booking";
import UpdateManage from "../pages/Admin/UpdateManage";
import BookingDetail from "../pages/Booking/BookingDetail";
import BookingCart from "../pages/Booking/BookingCart";
import Test from "../pages/Test";
import Statistic from "../pages/Admin/Statistic";
const publicRoutes = [
  { path: "/", component: Home },
  { path: "/blog-detail/:blogId", component: BlogDetail },
  { path: "/rooms", component: Rooms },
  { path: "/user", component: User },
  { path: "/room-detail/:roomId", component: RoomDetail },
  { path: "/about-us", component: AboutUs },
  { path: "/blogs", component: Blog },
  // { path: "/auth/login", component: Login },
  { path: "/auth/register", component: Register },
  { path: "/test", component: Test },
];

const privateRoutes = [
  { path: "/user", component: User },
  { path: "/booking-detail/:bookingId", component: BookingDetail },
  { path: "/booking-cart", component: BookingCart },
  { path: "/booking", component: Booking },
  { path: "/admin", component: Statistic },
  { path: "/admin/:option", component: Manage },
  { path: "/admin/add/:option", component: AddManage },
  { path: "/admin/update/:option/:optionId", component: UpdateManage },
];

export { publicRoutes, privateRoutes };
