import * as React from "react";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Collapse from "@mui/material/Collapse";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import { grey, red } from "@mui/material/colors";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ShareIcon from "@mui/icons-material/Share";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import ImageCard from "../../assets/images/Pret.png";
import ImageCardAvatar from "../../assets/images/4On3Lo-LogoMakr.png";
import { Button, Grid } from "@mui/material";
const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? "rotate(0deg)" : "rotate(180deg)",
  marginLeft: "auto",
  transition: theme.transitions.create("transform", {
    duration: theme.transitions.duration.shortest,
  }),
}));

export default function EmpruntCard() {
  const [expanded, setExpanded] = React.useState(false);

  const handleExpandClick = () => {
    // setExpanded(!expanded);
  };

  return (
    <Grid sx={{ boxShadow: 12 }}>
      {" "}
      <Card sx={{ maxWidth: 500 }}>
        <CardHeader
          avatar={
            <Avatar sx={{ bgcolor: "grey[500]" }} aria-label="recipe">
              <img
                src={ImageCardAvatar}
                alt="Votre image"
                style={{ width: "100%", height: "100%", objectFit: "cover" }}
              />
            </Avatar>
          }
          action={
            <IconButton aria-label="settings">
              <MoreVertIcon />
            </IconButton>
          }
          title="Prêt d'argent"
          subheader="B I P"
        />
        <CardMedia
          component="img"
          height="194"
          image={ImageCard}
          alt="Paella dish"
        />
        <CardContent>
          <Typography variant="body2" color="text.secondary">
            This impressive paella is a perfect party dish and a fun meal to
            cook together with your guests. Add 1 cup of frozen peas along with
            the mussels, if you like.
          </Typography>
        </CardContent>
        <CardActions disableSpacing>
          <ExpandMore
            //   expand={expanded}
            onClick={handleExpandClick}
            //   aria-expanded={expanded}
            aria-label="show more"
          >
            <Button
              variant="contained"
              sx={{ color: "white", bgcolor: "#5a189a" }}
              color="secondary"
            >
              Prêt
            </Button>
          </ExpandMore>
        </CardActions>
      </Card>
    </Grid>
  );
}
