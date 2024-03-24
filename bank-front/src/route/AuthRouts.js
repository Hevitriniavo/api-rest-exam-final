import AuthLayout from "../layout/auth-layout/AuthLayout";
import SlideNavbar from "../view/front-pages/auth-page/SingUp";

const AuthRoutes = {
  path: "/",
  element: <AuthLayout />,
  children: [
    { path: "login", element: <SlideNavbar /> },
    { path: "inscription", element: <SlideNavbar /> },
  ],
};
export { AuthRoutes };
