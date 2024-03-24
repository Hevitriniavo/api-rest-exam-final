import { Grid, Typography } from "@mui/material";
import React from "react";
import { MapsHomeWork } from "@mui/icons-material";

function Header() {
  return (
    <>
      <Grid container border={"red solid 2px"} minHeight={"2vh"}>
        <Typography variant="h3" color={"white"}>
          <MapsHomeWork fontSize="large" />
          BIP
        </Typography>
      </Grid>
    </>
  );
}

export default Header;
