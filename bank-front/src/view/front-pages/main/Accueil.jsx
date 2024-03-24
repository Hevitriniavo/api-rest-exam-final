/* eslint-disable jsx-a11y/img-redundant-alt */
// import Footer from "./Footer";
// import ContentCardFront from "./ContentCards";
import bannerImage from "../../../assets/images/bgPar1.png";
import bannerImage2 from "../../../assets/images/baniere2.png";
import bannerImage3 from "../../../assets/images/baniere3.png";
import imgDesc1 from "../../../assets/images/detail.png";
import imgDesc2 from "../../../assets/images/detail1.png";
import imgDesc3 from "../../../assets/images/descri-img.png";
import { Box, Button, Divider, Grid, Typography } from "@mui/material";
import { useEffect, useRef, useState } from "react";
import axios from "axios";
import { useTheme } from "@mui/material/styles";
import useMediaQuery from "@mui/material/useMediaQuery";
import BoltIcon from "@mui/icons-material/Bolt";

function Acceuil() {
  // const [animatedText, setAnimatedText] = useState("");
  const [showFullText, setShowFullText] = useState(false);
  const [datas, setMyDatas] = useState([]);
  const theme = useTheme();
  const isXs = useMediaQuery(theme.breakpoints.only("xs"));
  const isSm = useMediaQuery(theme.breakpoints.only("sm"));
  const isMd = useMediaQuery(theme.breakpoints.only("md"));
  const isLg = useMediaQuery(theme.breakpoints.only("lg"));
  const parallaxRef2 = useRef(null);
  const parallaxRef3 = useRef(null);

  useEffect(() => {
    const parallaxEffect = () => {
      const scrolled = window.scrollY;
      const parallaxValue = scrolled * 0.7; // Ajustez cette valeur pour contrôler l'effet de parallaxe
      parallaxRef2.current.style.backgroundPositionY = `${parallaxValue}px`; // Ajoutez les références manquantes
      parallaxRef3.current.style.backgroundPositionY = `${parallaxValue}px`; // Ajoutez les références manquantes
    };

    window.addEventListener("scroll", parallaxEffect);

    return () => {
      window.removeEventListener("scroll", parallaxEffect);
    };
  }, []);
  useEffect(() => {
    axios
      .get("article/all")
      .then((response) => {
        setMyDatas(response.data.articles);
        console.log("okey azo articles");
        console.log(response);
      })
      .catch((error) => {
        console.error("tsy mandeha url articles");
        console.error(error);
      });
  }, []);

  let maxWords;
  if (isXs) {
    maxWords = 30;
  } else if (isSm) {
    maxWords = 40;
  } else if (isMd) {
    maxWords = 50;
  } else if (isLg) {
    maxWords = 80;
  } else {
    maxWords = 40; // Valeur par défaut pour les autres tailles d'écran
  }

  const text = `Imagine une plateforme de gestion de magasin innovante, conçue pour
    simplifier et optimiser toutes les facettes de la gestion
    commerciale. Notre site offre une interface conviviale permettant de
    gérer les stocks, suivre les ventes, et gérer les employés en toute
    efficacité. Grâce à des fonctionnalités avancées, comme la gestion
    des commandes fournisseurs, la génération de rapports en temps réel
    et la facilité d'intégration avec les systèmes de paiement, notre
    solution améliore la productivité des commerces. Que ce soit pour
    les petites boutiques ou les grandes enseignes, notre site est
    l'outil complet pour maximiser l'efficacité et stimuler la
    croissance du commerce.`;

  const trimmedText = showFullText
    ? text
    : text.split(" ").slice(0, maxWords).join(" ");
  const showMoreText = !showFullText && text.split(" ").length > maxWords;

  const toggleShowMore = () => {
    setShowFullText(!showFullText);
  };

  return (
    <>
      <Grid
        container
        justifyContent={"center"}
        overflow={"hidden"}
        width={"100%"}
        // p={2}
      >
        <Box
          sx={{
            textAlign: "center",
            width: "100%",
            height: {
              xs: "72vh",
              md: "90vh",
            },
            // background: "linear-gradient(to left, white, black)",
            bgcolor: "white",
            backgroundSize: "cover",
            // boxShadow: " inset 0px 65px 30px -7px rgba(0,0,0,0.51);",
            overflow: "hidden",
          }}
        >
          {/* <Hidden smDown>
            <img
              src={bannerImage}
              alt="sary-fandrakofana"
              style={{ width: "100%", height: "100vh" }}
            />
          </Hidden> */}
          <Grid
            height={"100%"}
            container
            justifyContent={"center"}
            // alignItems={"center"}
            sx={
              {
                //   backdropFilter: "blur(1px)", // Appliquer un flou de 5px à l'arrière-plan
                //   backgroundColor: "rgba(0, 0, 0, 0.5)", // Couleur de fond avec transparence pour l'effet de flou
              }
            }
          >
            <Grid
              container
              justifyContent={"space-around"}
              alignItems={"center"}
              // border={"red solid 2px"}
              height={"65vh"}
            >
              <Grid
                container
                // border={"red solid 2px"}
                item
                xs={5}
                direction={"column"}
                justifyContent={"center"}
              >
                <Typography
                  sx={{
                    fontSize: {
                      xs: "7vw", // Hauteur pour xs
                      md: "3vw", // Hauteur pour md
                    },
                    textAlign: {
                      xs: "center", // Hauteur pour xs
                      md: "lef", // Hauteur pour md
                    },
                  }}
                  color={"#7d8597"}
                  variant="h8"
                  fontFamily={"monospace"}
                  fontWeight={"bold"}
                  fontStyle={"italic"}
                >
                  Bienvenue chez <br />
                  <span
                    style={{
                      fontFamily: "sans-serif",
                      fontStyle: "normal",
                      fontSize: "120px",
                      color: "#5a189a",
                    }}
                  >
                    BIP
                  </span>
                </Typography>
                <Grid
                  container
                  justifyContent={"center"}
                  //   border={"red solid 2px"}
                >
                  <Typography
                    variant="h6"
                    style={{
                      // border: "2px solid #EBCC24",
                      justifyContent: "center",
                      //   width: "30%",
                      margin: "auto",
                      borderRadius: 8,
                      color: "#7d8597",
                      backgroundColor: "white",
                    }}
                  >
                    <span
                      style={{
                        fontFamily: "sans-serif",
                        fontStyle: "normal",
                        fontSize: "30px",
                        color: "#7d8597",
                        fontWeight: "bold",
                      }}
                    >
                      B
                    </span>
                    anque{" "}
                    <span
                      style={{
                        fontFamily: "sans-serif",
                        fontStyle: "normal",
                        fontSize: "30px",
                        color: "#7d8597",
                        fontWeight: "bold",
                      }}
                    >
                      I
                    </span>
                    nnovante de{" "}
                    <span
                      style={{
                        fontFamily: "sans-serif",
                        fontStyle: "normal",
                        fontSize: "30px",
                        color: "#7d8597",
                        fontWeight: "bold",
                      }}
                    >
                      P
                    </span>
                    roximité
                  </Typography>
                </Grid>
              </Grid>
              <Grid
                container
                justifyContent={"center"}
                // border={"red solid 2px"}
                position={"relative"}
                item
                xs={5}
                alignItems={"center"}
              >
                <img
                  src={bannerImage}
                  alt=""
                  style={{
                    maxHeight: "50vh",
                    // border: "red solid 2px",
                  }}
                />
              </Grid>
            </Grid>
            <Box
              // height={"100%"}
              display={"flex"}
              justifyContent={"center"}
              // sx={{ border: "red solid 3px" }}
              // position={"absolute"}
              //   top={10}
              //   zIndex={58}
            >
              <Typography
                sx={{
                  fontSize: {
                    xs: "7vw", // Hauteur pour xs
                    md: "3vw", // Hauteur pour md
                  },
                  textAlign: {
                    xs: "center", // Hauteur pour xs
                    md: "lef", // Hauteur pour md
                  },
                }}
                color={"#7d8597"}
                variant="h8"
                fontFamily={"monospace"}
                fontWeight={"bold"}
                fontStyle={"italic"}
              >
                Confiance, proximité, solutions, BIP.
              </Typography>
            </Box>
            <Grid container justifyContent={"center"}>
              <Button
                variant="contained"
                style={{
                  justifyContent: "center",
                  width: "10%",
                  margin: "auto",
                  borderRadius: 3,
                  color: "#5a189a",
                  backgroundColor: "white",
                  fontWeight: "bold",
                  fontFamily: "monospace",
                }}
              >
                Découvre
              </Button>
            </Grid>
          </Grid>
        </Box>

        <Grid
          // border={"red solid 2px"}
          sx={{
            height: {
              xs: "100%",
              md: "80vh",
            },
            my: {
              xs: 5,
              sm: 3,
            },
            bgcolor: "#d3c9e6",
          }}
          container
          direction={{
            xs: "column-reverse",
            sm: "column-reverse",
            md: "column-reverse",
            lg: "row",
          }}
          justifyContent={{
            xs: "center",
            sm: "center",
            md: "center",
            lg: "space-around",
          }}
          alignItems={{ xs: "center", md: "center", lg: "center" }}
        >
          <Grid
            // border={"green solid 2px"}
            // borderRadius={5}
            container
            textAlign={"center"}
            alignItems={"center"}
            justifyItems={"flex-end"}
            sx={{
              width: {
                xs: "70%",
                sm: "50%",
                md: "40%",
              },
              height: {
                xs: "20%",
                md: "30vw",
              },
              borderRadius: {
                xs: 0,
                sm: 3,
              },
              overflow: "hidden",
            }}
          >
            <img
              src={imgDesc3}
              alt="photoDesc"
              style={{ borderRadius: 10, height: "100%" }}
            />
          </Grid>

          <Grid
            container
            item
            xs={4}
            sm={6}
            md={6}
            direction={"column"}
            justifyContent={"center"}
            // border={"green solid 2px"}
            // width={"60%"}
            sx={{
              width: {
                xs: "95%",
                sm: "80%",
                md: "50%",
              },
            }}
          >
            <Typography
              variant="h3"
              color={"#7d8597"}
              fontFamily={"monospace"}
              fontStyle={"italic"}
              textAlign={"center"}
            >
              Description
            </Typography>
            <Grid container justifyContent={"center"}>
              <Divider sx={{ width: "50%" }}>
                <BoltIcon />
              </Divider>
            </Grid>
            <Grid
              container
              justifyContent={"center"}
              alignItems={"center"}
              // border={"red solid 3px"}
              p={3}
            >
              <Typography
                variant="h6"
                // fontFamily={RobotoRegular}
                textAlign={"left"}
                sx={{
                  width: {
                    xs: "100%",

                    md: "100%",
                  },
                }}
              >
                {trimmedText}
                {showMoreText && (
                  <span
                    onClick={toggleShowMore}
                    style={{ cursor: "pointer", color: "blue" }}
                  >
                    ...voir plus
                  </span>
                )}
                {showFullText && (
                  <span
                    onClick={toggleShowMore}
                    style={{ cursor: "pointer", color: "blue" }}
                  >
                    ...voir moins
                  </span>
                )}
              </Typography>
            </Grid>
          </Grid>
        </Grid>
        <Grid
          // border={"red solid 2px"}
          ref={parallaxRef3}
          container
          height={"80vh"}
          sx={{
            backgroundImage: `url(${bannerImage2})`,
            backgroundSize: "cover",
          }}
          // boxShadow={"0px 12px 5px 2px rgba(0,0,0,0.42)"}
        >
          <Grid
            sx={{
              backdropFilter: "blur(1px)", // Appliquer un flou de 5px à l'arrière-plan
              backgroundColor: "rgba(0, 0, 0, 0.5)", // Couleur de fond avec transparence pour l'effet de flou
            }}
            container
            width={"100%"}
            justifyContent={"center"}
            alignItems={"center"}
          >
            <Typography
              variant="h4"
              textAlign={"center"}
              fontFamily={"monospace"}
            >
              <span
                style={{
                  fontSize: "8vh",
                  fontStyle: "italic",
                  fontWeight: "bold",
                  color: "white",
                }}
              >
                BIP
              </span>
              <br />
              <span
                style={{
                  fontWeight: "bold",
                  fontSize: "8vh",
                  color: "#d3c9e6",
                }}
              >
                Excellence sans compromis.
              </span>
            </Typography>
          </Grid>
        </Grid>
        <Box
          display={"flex"}
          flexDirection={"column"}
          // boxShadow={"inset 0px 30px 30px -7px rgba(0,0,0,0.51)"}
          // paddingX={2}
          pt={3}
          // my={4}
          // mx={0.5}
          sx={{
            width: "100%",
          }}
          // border={"red solid 2px"}
        >
          <Box>
            <Grid
              container
              // border={"red solid 2px"}
              direction={{
                xs: "column-reverse",
                sm: "row",
                md: "row",
                lg: "row",
              }}
              justifyContent={"space-around"}
              alignItems={"center"}
            >
              <Grid
                // border={"green solid 2px"}
                borderRadius={5}
                container
                textAlign={"center"}
                alignItems={"center"}
                justifyItems={"center"}
                sx={{
                  width: {
                    xs: "100%",
                    md: "25vw",
                  },
                  height: {
                    xs: "100%",
                    md: "30vw",
                  },
                  overflow: "hidden",
                }}
                sm={5}
                md={4}
                lg={4}
              >
                <img src={imgDesc1} alt="photoDesc" style={{ width: "100%" }} />
              </Grid>
              <Grid
                padding={{ xs: 2, md: 3, lg: 3 }}
                margin={{ xs: 2, sm: "2vh 0", md: 1, lg: 1 }}
                alignItems={"center"}
                width={{ xs: "100", md: "100%", lg: "50%" }}
                sx={{ borderRadius: 5, bgcolor: "#d3c9e6" }}
                item
                // xs={12}
                sm={6.5}
                md={7}
                lg={7}
              >
                <Typography
                  variant="h4"
                  fontFamily={"monospace"}
                  fontStyle={"italic"}
                  textAlign={"center"}
                  color={"#5a189a"}
                  sx={{
                    fontSize: {
                      // xs: "1px",
                      sm: "3.5vh",
                      md: "5vh",
                      // lg: "7vh",
                    },
                  }}
                >
                  BIP est là pour vous, toujours à vos côtés
                </Typography>
                <Typography
                  textAlign={"left"}
                  variant="h6"
                  //   fontFamily={RobotoRegular}

                  sx={{
                    textAlign: {
                      xs: "justify",
                      sm: "left",
                      md: "left",
                    },
                    fontSize: {
                      xs: "2.8vh",
                      sm: "2vh",
                      md: "2.7vh",
                      // lg: "3.5vh",
                    },
                  }}
                >
                  Bienvenue chez BIP - Votre Solution de Gestion de Magasin
                  Disponible à Tout Moment ! Chez BIP, nous comprenons que le
                  monde du commerce ne dort jamais. C'est pourquoi nous sommes
                  fiers de vous offrir une solution de gestion de magasin
                  disponible 24 heures sur 24, 7 jours sur 7. Que vous soyez un
                  propriétaire de petite boutique ou une grande enseigne, nous
                  sommes là pour vous accompagner à chaque étape de votre
                  parcours commercial.
                </Typography>
              </Grid>
            </Grid>
          </Box>
          <Box pb={6}>
            <Grid
              container
              // border={"green solid 2px"}
              direction={{
                xs: "column",
                sm: "row",
                md: "row",
                lg: "row",
              }}
              justifyContent={"space-around"}
              alignItems={"center"}
            >
              <Grid
                padding={{ xs: 2, md: 3, lg: 3 }}
                margin={{ xs: 2, sm: "2vh 0", md: 1, lg: 1 }}
                alignItems={"center"}
                width={{ xs: "100", md: "100%", lg: "50%" }}
                sx={{ borderRadius: 5, bgcolor: "#d3c9e6" }}
                item
                // xs={12}
                sm={6.5}
                md={7}
                lg={7}
              >
                <Typography
                  variant="h4"
                  fontFamily={"monospace"}
                  fontStyle={"italic"}
                  textAlign={"center"}
                  color={"#5a189a"}
                  sx={{
                    fontSize: {
                      // xs: "1px",
                      sm: "3.5vh",
                      md: "5vh",
                      // lg: "6vh",
                    },
                  }}
                >
                  Explorez la Complétude <br />
                  chez BIP
                </Typography>
                <Typography
                  textAlign={"left"}
                  variant="h6"
                  //   fontFamily={RobotoRegular}
                  sx={{
                    textAlign: {
                      xs: "justify",
                      sm: "left",
                      md: "left",
                    },
                    fontSize: {
                      xs: "2.8vh",
                      sm: "2vh",
                      md: "2.7vh",
                      // lg: "2vh",
                    },
                  }}
                >
                  Chez BIP, nous sommes fiers de vous offrir une gamme complète
                  de produits et de services pour répondre à tous vos besoins.
                  Que vous recherchiez des produits de haute qualité, un service
                  client exceptionnel ou une expérience d'achat pratique, nous
                  avons tout ce qu'il vous faut, et plus encore.
                </Typography>
              </Grid>
              <Grid
                // border={"green solid 2px"}
                borderRadius={5}
                container
                textAlign={"center"}
                alignItems={"center"}
                justifyItems={"center"}
                sx={{
                  width: {
                    xs: "100%",
                    md: "30vw",
                  },
                  height: {
                    xs: "100%",
                    md: "30vw",
                  },
                  overflow: "hidden",
                }}
                sm={5}
                md={4}
                lg={4}
              >
                <img src={imgDesc2} alt="photoDesc" style={{ width: "100%" }} />
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Grid
          ref={parallaxRef2}
          container
          height={"70vh"}
          sx={{
            backgroundImage: `url(${bannerImage3})`,
            backgroundSize: "cover",
          }}
          // boxShadow={"0px 12px 5px 2px rgba(0,0,0,0.42)"}
        >
          <Grid
            sx={{
              backdropFilter: "blur(1px)", // Appliquer un flou de 5px à l'arrière-plan
              backgroundColor: "rgba(0, 0, 0, 0.5)", // Couleur de fond avec transparence pour l'effet de flou
            }}
            container
            width={"100%"}
            justifyContent={"center"}
            alignItems={"center"}
          >
            <Typography
              variant="h4"
              textAlign={"center"}
              fontFamily={"monospace"}
            >
              <span
                style={{
                  fontSize: "8vh",
                  fontStyle: "italic",
                  color: "white",
                  fontWeight: "bold",
                }}
              >
                BIP
              </span>
              <br />
              <span
                style={{
                  fontWeight: "bold",
                  fontSize: "8vh",
                  color: "#d3c9e6",
                }}
              >
                Excellence sans compromis.
              </span>
            </Typography>
          </Grid>
        </Grid>
        {/* <Box
          py={3}
          boxShadow={10}
          sx={{
            width: "100%",
            borderRadius: "8px",
            backgroundColor: "white",
          }}
        >
          <Typography
            sx={{
              textAlign: {
                xs: "center",
              },
            }}
            variant="h2"
            color={"#95c732"}
            paddingX={6}
          >
            Arrivage
          </Typography>
          <Stack direction="row" alignItems="center">
            <Box
              display={"flex"}
              margin="auto"
              flexDirection={"row"}
              width={"100%"}
              // border={"red solid 2px"}
              px={2}
              sx={{
                overflowX: "hidden",
                "&::-webkit-scrollbar": {
                  width: 0,
                },
              }}
            >
              <Tabs variant="scrollable">
                {datas &&
                  datas.map((e) => (
                    <Box key={e.id}>
                      <Box
                        sx={{
                          minWidth: { xs: 250, sm: 250 },
                          p: { xs: 1, sm: 2 },
                          borderRadius: 2,
                          overflow: "hidden",
                        }}
                      >
                        <ContentCardFront articles={e} dataId={e.category.id} />
                      </Box>
                    </Box>
                  ))}
              </Tabs>
            </Box>
          </Stack>
        </Box> */}
        {/* <CarouselPub/> */}
      </Grid>
    </>
  );
}

export default Acceuil;
