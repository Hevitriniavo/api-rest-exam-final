import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import Button from "@mui/material/Button";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import HomeWorkIcon from "@mui/icons-material/HomeWork";
import { Grid, Stack, Typography } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

import MenuIcon from "@mui/icons-material/Menu";
import HomeIcon from "@mui/icons-material/Home";
import CategoryIcon from "@mui/icons-material/Category";
import LoginIcon from "@mui/icons-material/Login";
import InfoIcon from "@mui/icons-material/Info";
import { useNavigate } from "react-router-dom";
import PersonIcon from "@mui/icons-material/Person";
import CustomizedInputBase from "./SeacheBar";
import { useAuthTemp } from "../../utils/auth";
import ResponsiveAppBar from "./Encho";
export default function HeaderXS() {
  const [isSearche, setSearche] = React.useState(true);

  const auth = useAuthTemp();
  const userFront = auth.getUserFront();
  const idUser = userFront ? userFront.id : null;
  // console.log('this is the userBack user', userFront.firstName);
  console.log("this is the userBack user", idUser);
  const navigate = useNavigate();
  const [state, setState] = React.useState({
    left: false,
  });
  const handleSingUp = () => {
    navigate("/inscription");
  };
  const handleSingIn = () => {
    navigate("/login");
  };
  const handleOpenSearche = () => {
    setSearche(false);
  };
  const handleCloseSearche = () => {
    setSearche(true);
  };

  const toggleDrawer = (anchor, open, route) => () => {
    if (open) {
      navigate(route);
    }
    setState({ ...state, [anchor]: open });
  };
  const [bgColor, setBgColor] = React.useState("transparent");

  React.useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.pageYOffset;

      if (scrollTop === 0) {
        setBgColor("transparent");
      } else {
        setBgColor("#000000");
        setSearche(true);
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);
  const list = (anchor) => (
    <Box
      sx={{ width: anchor === "top" || anchor === "bottom" ? "auto" : 200 }}
      role="presentation"
      onClick={toggleDrawer(anchor, false)}
      onKeyDown={toggleDrawer(anchor, false)}
    >
      <Stack
        direction={"row"}
        // mr={6}
        alignItems={"center"}
        justifyContent={"center"}
        // border={"white 2px solid"}
        p={0.5}
        my={1}
        borderRadius={2}
      >
        {<HomeWorkIcon sx={{ fontSize: "5vh", color: "#00A1C9" }} />}
        <Typography
          variant="h4"
          color={"#00A1C9"}
          fontFamily={"monospace "}
          style={{ fontStyle: "italic" }}
        >
          Lowe's
        </Typography>
      </Stack>
      <Divider />
      <List>
        {["Acceuil", "Produits", "Apropos"].map((text, index) => (
          <>
            <ListItem key={text} disablePadding>
              <ListItemButton
                onClick={toggleDrawer(
                  anchor,
                  true,
                  text === "Acceuil"
                    ? "/"
                    : text === "Produits"
                    ? "/produit"
                    : text === "Apropos"
                    ? "/apropos"
                    : ""
                )}
              >
                <ListItemIcon>
                  {text === "Acceuil" ? (
                    <HomeIcon />
                  ) : text === "Produits" ? (
                    <CategoryIcon />
                  ) : text === "Apropos" ? (
                    <InfoIcon />
                  ) : null}
                </ListItemIcon>
                <ListItemText primary={text} />
              </ListItemButton>
            </ListItem>
          </>
        ))}
      </List>
    </Box>
  );

  return (
    <>
      <Grid
        borderTop={"4px solid #00A1C9"}
        py={1}
        container
        justifyContent={"space-between"}
        alignItems={"center"}
        bgcolor={bgColor}
        // sx={{ border: "red solid 2px" }}
        // bgcolor={"black"}
        // px={5}
        style={{
          position: "fixed",
          zIndex: 999,
        }}
      >
        {!isSearche && (
          <Grid
            direction={"row"}
            container
            position={"absolute"}
            zIndex={99}
            // border={"red solid 2px"}
            bgcolor={"black"}
            height={"100%"}
            justifyContent={"center"}
            alignItems={"center"}
          >
            <Grid
              item
              // border={"red solid 2px"}
              // xs={8}
              // height={"100%"}
              alignItems={"center"}
              justifyContent={"center"}
            >
              <CustomizedInputBase handleCloseSearche={handleCloseSearche} />
            </Grid>
          </Grid>
        )}
        <Grid
          item
          xs={6}
          mr={2}
          container
          direction={"row"}
          alignItems={"center"}
          justifyContent={"flex-start"}
          // sx={{ border: "green solid 2px" }}
        >
          {" "}
          <Grid>
            {["left"].map((anchor) => (
              <React.Fragment key={anchor}>
                <Button
                  style={{ color: "#00A1C9" }}
                  onClick={toggleDrawer(anchor, true)}
                >
                  {<MenuIcon fontSize="large" />}
                </Button>
                <Drawer
                  anchor={anchor}
                  open={state[anchor]}
                  onClose={toggleDrawer(anchor, false)}
                >
                  {list(anchor)}
                </Drawer>
              </React.Fragment>
            ))}
          </Grid>
          <Grid item xs={4} mr={2}>
            {/* <ThemeProvider theme={theme}> */}
            <SearchIcon
              sx={{
                p: 0.5,
                color: "white",
                border: "white solid 2px",
                borderRadius: 2,
              }}
              onClick={handleOpenSearche}
            />

            {/* </ThemeProvider> */}
          </Grid>
        </Grid>
        <Grid
          container
          // border={"red solid 2px"}
          item
          xs={4}
          justifyContent={"center"}
          p={0}
        >
          <Grid
            container
            justifyContent={"center"}
            // my={2}
            // height={'100%'}
            alignItems={"center"}
            // border={"red solid 2px"}
          >
            {userFront !== null ? (
              <><ResponsiveAppBar data={{ userFront, auth }} /></>
            ) : (
              <Grid>
                <Button
                  onClick={handleSingUp}
                  sx={{ color: "#95c732" }}
                  variant="text"
                  startIcon={<PersonIcon />}
                >
                  Sign up
                </Button>

                <Button
                  onClick={handleSingIn}
                  variant="contained"
                  style={{
                    justifyContent: "center",
                    // width: "30%",
                    margin: "auto",
                    borderRadius: 8,
                    color: "#95c732",
                    backgroundColor: "white",
                  }}
                  startIcon={<LoginIcon />}
                >
                  Sign in
                </Button>

                {/* <Button onClick={handleSingIn}>
                <Chip
                  label="S'inscrit"
                  variant="filled"
                  sx={{
                    backgroundImage:
                      "linear-gradient(120deg, white 50%, white 50%)",
                  }}
                  icon={
                    <PersonIcon
                      style={{
                        border: "2px solid grey",
                        borderRadius: "50%",
                        background: "#00A1C9",
                      }}
                    />
                  }
                  style={{ backgroundColor: "#00A1C9" }}
                />
              </Button> */}
              </Grid>
            )}
          </Grid>
        </Grid>
      </Grid>
    </>
  );
}
