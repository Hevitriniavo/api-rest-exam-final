import { RouterProvider } from "react-router-dom";
import router from "./route";
import { AuthProviderTemp } from "./utils/auth";

const App = () => {
  return (
    <AuthProviderTemp>
      <>
        <RouterProvider router={router} />
      </>
    </AuthProviderTemp>
  );
};

export default App;
