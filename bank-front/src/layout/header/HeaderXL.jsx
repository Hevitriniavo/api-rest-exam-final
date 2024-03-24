import { Box, Button, Grid, Stack, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import LoginIcon from "@mui/icons-material/Login";

import { ThemeProvider, createTheme } from "@mui/material/styles";
import HomeWorkIcon from "@mui/icons-material/HomeWork";
import PersonIcon from "@mui/icons-material/Person";
// import { useAuthTemp } from "../../util/auth";
// import ResponsiveAppBar from "../Encho";
// import CustomizedInputBase from "./SeacheBar";
import CustomizedInputBase from "./SeacheBar";
import { useAuthTemp } from "../../utils/auth";
import ResponsiveAppBar from "./Encho";

function HeaderXL() {
  const auth = useAuthTemp();
  const userFront = auth.getUserFront();
  const idUser = userFront ? userFront.id : null;
  // console.log('this is the userBack user', userFront.firstName);
  console.log("this is the userBack user", idUser);

  const navigate = useNavigate();
  const location = useLocation();
  const handleSingUp = () => {
    navigate("/login");
  };
  // const handleSingIn = () => {
  //   navigate("/login");
  // };
  const handleAcceuil = () => {
    navigate("/");
  };
  const handleProduct = () => {
    navigate("/compte");
  };
  const handleDetail = () => {
    navigate("/detail");
  };
  const handleApropos = () => {
    navigate("/apropos");
  };
  const theme = createTheme({
    // Define your theme properties here
    shape: {
      borderRadius: 8, // Example value, adjust according to your design
    },
    // Other theme properties...
  });
  // const handleLogout = async () => {
  //   try {
  //     auth.logoutUserFront();
  //     navigate("/login");
  //   } catch (err) {
  //     console.error(err);
  //   }
  // };

  const [bgColor, setBgColor] = useState("transparent");

  useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.pageYOffset;

      if (scrollTop === 0) {
        setBgColor("transparent");
      } else {
        setBgColor("#d3c9e6");
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);
  const transitionStyle = {
    transition: "background-color 0.5s ease", // Ajoutez une transition de 0.5s avec une fonction d'accélération
  };
  return (
    <>
      <Grid
        borderTop={"4px solid #5a189a"}
        container
        justifyContent={"space-between"}
        alignItems={"center"}
        bgcolor="#d3c9e6"
        px={5}
        style={{
          position: "fixed",
          zIndex: 999,
          // ...transitionStyle, // Appliquez le style de transition
          // backgroundColor: bgColor, // Utilisez la couleur d'arrière-plan dynamique
        }}
      >
        <Stack
          direction={"row"}
          alignItems={"flex-end"}
          justifyContent={"center"}
          // border={"green solid 2px"}
        >
          <Stack
            direction={"row"}
            mr={6}
            justifyContent={"center"}
            alignItems={"center"}
            border={"#5a189a 2px solid"}
            p={0.5}
            my={1}
            borderRadius={2}
          >
            {<HomeWorkIcon sx={{ fontSize: "5vh", color: "#5a189a" }} />}
            <Typography
              variant="h4"
              color={"#5a189a"}
              fontFamily={"monospace "}
              fontWeight={"bold"}
              style={{ fontStyle: "italic" }}
            >
              BIP
            </Typography>
          </Stack>
        </Stack>
        <Stack direction={"row"} mb={2} alignItems={"flex-end"}>
          <Box
            sx={{
              borderBottom:
                location.pathname === "/" ? "3px solid #5a189a" : "none",
              // transition: ".8s ease",
            }}
          >
            <Button
              onClick={handleAcceuil}
              variant="text"
              style={{
                color: "#5a189a",
              }}
            >
              Acceuil
            </Button>
          </Box>
          <Box
            sx={{
              borderBottom:
                location.pathname === "/compte" ? "3px solid #5a189a" : "none",
              // transition: ".8s ease",
            }}
          >
            <Button
              onClick={handleProduct}
              variant="text"
              style={{
                color: "#5a189a",
              }}
            >
              Compte
            </Button>
          </Box>
          <Box
            sx={{
              borderBottom:
                location.pathname === "/detail" ? "3px solid #5a189a" : "none",
              // transition: ".8s ease",
            }}
          >
            <Button
              onClick={handleDetail}
              variant="text"
              style={{
                color: "#5a189a",
              }}
            >
              Tableau de bord
            </Button>
          </Box>
          <Box
            sx={{
              borderBottom:
                location.pathname === "/taux-echange"
                  ? "3px solid #5a189a"
                  : "none",
              // transition: ".8s ease",
            }}
          >
            <Button
              // onClick={handleProduct}
              variant="text"
              sx={{
                color: "#5a189a",
                // transition: ".8s ease",
              }}
            >
              Taux d'echange
            </Button>
          </Box>
          <Box
            sx={{
              borderBottom:
                location.pathname === "/apropos" ? "3px solid #5a189a" : "none",
              // transition: ".8s ease",
            }}
          >
            <Button
              onClick={handleApropos}
              variant="text"
              sx={{
                color: "#5a189a",
              }}
            >
              Contacte
            </Button>
          </Box>
        </Stack>
        <Stack
          direction="row"
          spacing={5}
          alignItems={"center"}
          //   border={"solid red 2px"}
        >
          {/* <ThemeProvider theme={theme}>
            <CustomizedInputBase />
          </ThemeProvider> */}
          {userFront !== null ? (
            <>
              <ResponsiveAppBar data={{ userFront, auth }} />{" "}
            </>
          ) : (
            <Grid bgcolor={"white"} borderRadius={2}>
              <Link to={`/login`}>
                <Button
                  // onClick={handleSingUp}

                  sx={{ color: "#5a189a" }}
                  variant="text"
                  startIcon={<PersonIcon />}
                >
                  Se connecter
                </Button>
              </Link>
            </Grid>
          )}
        </Stack>
      </Grid>
    </>
  );
}

export default HeaderXL;
