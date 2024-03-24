import { createBrowserRouter } from "react-router-dom";
import FrontRoute from "./FrontRoute";
import { AuthRoutes } from "./AuthRouts";

const router = createBrowserRouter([FrontRoute, AuthRoutes]);
export default router;
