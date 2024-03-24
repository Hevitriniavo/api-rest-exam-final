import * as React from "react";
import { BarChart } from "@mui/x-charts/BarChart";
import { Grid } from "@mui/material";

export default function Dashboard() {
  return (
    <Grid
      container
      justifyContent={"center"}
      height={"100vh"}
      alignItems={"center"}
    >
      {" "}
      <BarChart
        xAxis={[
          {
            id: "barCategories",
            data: ["bar A", "bar B", "bar C"],
            scaleType: "band",
          },
        ]}
        series={[
          {
            data: [2, 5, 3],
            color: "#5a189a",
          },
        ]}
        width={500}
        height={300}
      />
    </Grid>
  );
}
