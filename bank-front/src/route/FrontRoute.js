import React from "react";
import FrontLayout from "../layout/front-layout/FrontLayout";
import Acceuil from "../view/front-pages/main/Accueil";
import Compte from "../view/front-pages/main/Compte";
import Apropos from "../view/front-pages/main/Apropos";
import Dashboard from "../view/front-pages/main/Dashboard";

const FrontRoute = {
  path: "/",
  element: <FrontLayout />,
  children: [
    {
      path: "/",
      element: <Acceuil />,
    },
    {
      path: "compte",
      element: <Compte />,
    },
    {
      path: "apropos",
      element: <Apropos />,
    },
    {
      path: "detail",
      element: <Dashboard />,
    },
  ],
};

export default FrontRoute;
