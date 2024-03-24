import { CircularProgress, Grid } from "@mui/material";
import React from "react";

function Waiter() {
  return (
    <>
      <Grid
        container
        position={"absolute"}
        height={"100vh"}
        justifyContent={"center"}
        alignItems={"center"}
        zIndex={8888}
        sx={{
          backdropFilter: "blur(1px)",
          backgroundColor: "rgba(0, 0, 0, 0.5)",
        }}
      >
        <CircularProgress style={{ color: "#fff" }} />
      </Grid>
    </>
  );
}

export default Waiter;
