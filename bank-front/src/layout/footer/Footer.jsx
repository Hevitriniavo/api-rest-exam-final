import { Box, Grid, Link, Stack, Typography } from "@mui/material";
import HomeRepairServiceIcon from "@mui/icons-material/HomeRepairService";
import InfoIcon from "@mui/icons-material/Info";
import ContactsIcon from "@mui/icons-material/Contacts";
import AssignmentIcon from "@mui/icons-material/Assignment";
import React from "react";

function Footer() {
  return (
    <>
      <Box
        sx={{
          // color: "wh",
          // mr: 5,
          backgroundImage:
            "linear-gradient(120deg, #d3c9e6 50%, #ddddddff 50%)",
        }}
        width={"100%"}
        mt={4}
      >
        <Grid
          container
          py={5}
          justifyContent={"space-around"}
          alignItems={"flex-start"}
        >
          <Stack>
            <Typography variant="h5" color={"back"}>
              <InfoIcon /> INFORMEZ-VOUS
            </Typography>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Qui sommes nous
            </Link>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Conditions générales de vente
            </Link>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Pour demande de partenariat
            </Link>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Politique de Confidentialité
            </Link>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Achats groupés à zéro intérêt?
            </Link>
          </Stack>
          <Stack>
            <Typography variant="h5" color={"back"}>
              <AssignmentIcon /> BON A SAVOIR
            </Typography>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Quantifier mon surface à carreler
            </Link>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Conditions générales de vente
            </Link>
          </Stack>
          <Stack>
            <Typography variant="h5" color={"back"}>
              <ContactsIcon />
              RELATION CLIENT
            </Typography>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              lowes@gmail.com
            </Link>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Telma: 034 xx xxx xx
            </Link>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Airtel: 033 xx xxx xx
            </Link>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Orange: 032 xx xxx xx
            </Link>
          </Stack>
          <Stack>
            <Typography variant="h5" color={"back"}>
              <HomeRepairServiceIcon />
              SERVICES
            </Typography>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Equipe technique
            </Link>
            <Link color="inherit" underline="hover">
              <span sx={{ marginRight: "4px", fontSize: "12px" }}>&bull;</span>
              Recouvrementonditions générales
            </Link>
          </Stack>
        </Grid>
      </Box>
      <footer
        style={{
          backgroundColor: "#000",
          color: "#fff",
          padding: "5px 0",
          textAlign: "center",
          width: "100%",
        }}
      >
        <p>© 2024 Equipe Solide H1</p>
      </footer>
    </>
  );
}

export default Footer;
