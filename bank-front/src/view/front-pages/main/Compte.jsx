import { Box, Grid, Typography } from "@mui/material";
import React from "react";
import CardTransfert from "../../../component/cards/TranfertCard";
import TranfertCard from "../../../component/cards/TranfertCard";
import TransactionCard from "../../../component/cards/TransactionCard";
import EmpruntCard from "../../../component/cards/EmpruntCard";
import RetraitCard from "../../../component/cards/RetraitCard";

function Compte() {
  return (
    <>
      <Grid container width={"100%"} px={2}>
        <Box
          my={2}
          boxShadow={8}
          // paddingLeft={1}
          sx={{
            width: "100%",
            backgroundColor: "white",
            borderRadius: "8px",
          }}
          display={"flex"}
          justifyContent={"space-between"}
        >
          <Typography
            variant="h4"
            style={{
              margin: 3,
              fontWeight: "bold",
              color: "#5a189a",
              fontFamily: "monospace",
            }}
          >
            Numero :{" "}
            <span style={{ fontWeight: "50px", color: "grey" }}>
              0011 000 000 001
            </span>
          </Typography>
          <Typography
            variant="h4"
            style={{
              margin: 3,
              fontWeight: "bold",
              fontFamily: "monospace",
              color: "#5a189a",
            }}
          >
            Solode :
            <span style={{ fontWeight: "50px", color: "grey" }}>
              1000000 mg
            </span>
          </Typography>
        </Box>
      </Grid>
      <Grid container justifyContent={"center"}>
        <Grid
          container
          item
          sm={5}
          m={2}
          justifyContent={"center"}
          p={2}
          //  border={"red solid 2px"}
        >
          <TransactionCard />
        </Grid>
        <Grid
          container
          item
          sm={5}
          m={2}
          justifyContent={"center"}
          p={2}
          //  border={"red solid 2px"}
        >
          <RetraitCard />
        </Grid>
        <Grid
          container
          item
          sm={5}
          m={2}
          justifyContent={"center"}
          p={2}
          //  border={"red solid 2px"}
        >
          <TranfertCard />
        </Grid>
        <Grid
          container
          item
          sm={5}
          m={2}
          justifyContent={"center"}
          p={2}
          //  border={"red solid 2px"}
        >
          <EmpruntCard />
        </Grid>
      </Grid>
    </>
  );
}

export default Compte;
