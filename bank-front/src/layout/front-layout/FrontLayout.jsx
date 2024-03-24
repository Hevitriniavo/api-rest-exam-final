import { Box } from "@mui/material";
import React, { useEffect, useState } from "react";
import HeaderXS from "../header/HeaderXS";
import HeaderXL from "../header/HeaderXL";
import { Outlet } from "react-router-dom";
import Footer from "../footer/Footer";

function FrontLayout() {
  const [largeurEcran, setLargeurEcran] = useState(window.innerWidth);

  useEffect(() => {
    const handleResize = () => {
      setLargeurEcran(window.innerWidth);
    };

    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);
  return (
    <>
      <Box sx={{ minHeight: "10vh" }}>
        {largeurEcran < 900.99 ? (
          <Box sx={{ display: "none" }}>
            <HeaderXS />
          </Box>
        ) : (
          <Box>
            <HeaderXL />
          </Box>
        )}
        {largeurEcran > 900.99 ? (
          <Box sx={{ display: "none" }}>
            <HeaderXL />
          </Box>
        ) : (
          <Box>
            <HeaderXS />
          </Box>
        )}
      </Box>
      <Box sx={{ minHeight: "90vh" }}>
        <Outlet />
      </Box>
      <Footer />
    </>
  );
}

export default FrontLayout;
