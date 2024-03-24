import {
  Box,
  Button,
  Grid,
  IconButton,
  InputAdornment,
  TextField,
} from "@mui/material";
import { makeStyles } from "@mui/styles";
import { useFormik } from "formik";
import React, { useState } from "react";
import * as yup from "yup";
import axios from "axios";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { UrlSite } from "../../../utils";
import StateNotification from "../../../component/Common/StateNotification";
const validationSchema = yup.object({
  email: yup
    .string("Veuillez entrer votre adresse e-mail")
    .email("Veuillez entrer une adresse e-mail valide")
    .required(`'L'adresse e-mail est obligatoire'`),
  password: yup
    .string("Veuillez entrer votre mot de passe")
    .min(4, "Le mot de passe doit contenir au moins 8 caractères")
    .required("Le mot de passe est obligatoire"),
});
const useStyles = makeStyles({
  textField: {
    // width: "100%",
    "& .MuiInputBase-root": {
      height: "5vh",
      fontSize: "0.8rem", // Taille du texte
      padding: 0, // Espacement intérieur
      borderRadius: "4px", // Bordure
    },
    "& .MuiInputLabel-root": {
      fontSize: "0.8rem", // Taille du label
      backgroundColor: "white",
      padding: "0 10px",
      marginLeft: "-5px",
      borderRadius: 4,
      zIndex: 2,
    },
  },
});
function SingIn(props) {
  const classes = useStyles();
  const isChecked = props.checked;
  const setLoading = props.load;
  const [showErrorDialog, setShowErrorDialog] = useState(false);
  const submitFormData = async (values) => {
    console.log("Submitting form data model");
    const formData = new FormData();

    formData.append("email", values.email);
    formData.append("password", values.password);

    const object = {};
    formData.forEach((value, key) => {
      object[key] = value;
    });
    console.log(JSON.stringify(object));

    let config = {
      method: "post",
      maxBodyLength: Infinity,
      url: UrlSite("users-front/login"),
      headers: {
        "Content-Type": "application/json",
      },
      data: formData,
    };

    await axios
      .request(config)
      .then((response) => {
        console.log("anaty try");
        console.log(response);
        // auth.loginUserFront(response.data.items);
        // setLoad(false);
        setLoading(false);
        // setShowSuccessDialog(true);
        // navigate("/");
      })
      .catch((error) => {
        console.log("anaty catch");
        console.error(error);
        // setLoad(false);
        setShowErrorDialog(true);
        setLoading(false);
      });
  };

  const formik = useFormik({
    initialValues: {
      email: "",
      password: "",
      showPassword: false,
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      console.log(JSON.stringify(values, null, 2));
      submitFormData(values);
      setLoading(true);
    },
  });
  const handleCloseErrorDialog = () => {
    setShowErrorDialog(false);
  };
  return (
    <Box>
      <Grid
        container
        // border={"red solid 2px"}
        bgcolor={"white"}
        direction={"column"}
        alignItems={"center"}
        className="login"
        style={{
          height: 500,
          background: "#eee",
          borderRadius: "60% / 10%",
          paddingTop: isChecked ? 100 : 0,
          transform: isChecked ? "translateY(-61vh)" : "translateY(-8vh)",
          transition: " .8s ease-in-out",
        }}
      >
        <label
          htmlFor="chk"
          aria-hidden="true"
          style={{
            color: "#573b8a",
            fontSize: "2.3em",
            justifyContent: "center",
            display: "flex",
            fontWeight: "bold",
            transform: !isChecked ? "scale(0.6)" : "scale(1)",
            transition: ".8s ease-in-out",
          }}
        >
          Se Connecter
        </label>
        <form onSubmit={formik.handleSubmit}>
          <Grid
            container
            justifyContent={"center"}
            alignItems={"center"}
            // border={"red solid 2px"}
            my={2.5}
          >
            <Box borderRadius={1} width={"100%"}>
              <TextField
                fullWidth
                label="Email"
                className={classes.textField}
                variant="outlined"
                color="secondary"
                sx={{ bgcolor: "white", borderRadius: 1, zIndex: 0 }}
                size="small"
                id="email"
                name="email"
                value={formik.values.email}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                error={formik.touched.email && Boolean(formik.errors.email)}
                helperText={formik.touched.email && formik.errors.email}
              />
            </Box>
          </Grid>
          <Grid
            container
            justifyContent={"center"}
            alignItems={"center"}
            my={2.5}
          >
            <Box borderRadius={1} width={"100%"}>
              <TextField
                fullWidth
                label="Mot de passe"
                className={classes.textField}
                variant="outlined"
                color="secondary"
                sx={{ bgcolor: "white", borderRadius: 1, zIndex: 0 }}
                size="small"
                id="password"
                name="password"
                type={formik.values.showPassword ? "text" : "password"}
                value={formik.values.password}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                error={
                  formik.touched.password && Boolean(formik.errors.password)
                }
                helperText={formik.touched.password && formik.errors.password}
                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        onClick={() =>
                          formik.setValues({
                            ...formik.values,
                            showPassword: !formik.values.showPassword,
                          })
                        }
                        edge="end"
                      >
                        {formik.values.showPassword ? (
                          <IconButton>
                            <Visibility fontSize="small" />
                          </IconButton>
                        ) : (
                          <IconButton>
                            <VisibilityOff fontSize="small" />
                          </IconButton>
                        )}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
            </Box>
          </Grid>
          <Button
            // onClick={teste}
            type="submit"
            style={{
              width: "100%",
              height: 40,
              margin: "10px auto",
              justifyContent: "center",
              display: "block",
              color: "#fff",
              background: "#573b8a",
              fontSize: "1em",
              fontWeight: "bold",
              marginTop: 20,
              outline: "none",
              border: "none",
              borderRadius: 5,
              transition: ".2s ease-in",
              cursor: "pointer",
            }}
          >
            Submit
          </Button>
        </form>
        <StateNotification
          //   showSuccessDialog={showSuccessDialog}
          showErrorDialog={showErrorDialog}
          //   handleCloseSuccessDialog={handleCloseSuccessDialog}
          //   successMessage="Votre inscription a été complétée avec succès !"
          handleCloseErrorDialog={handleCloseErrorDialog}
          errorMessage="Veuillez réessayer"
        />
      </Grid>
    </Box>
  );
}

export default SingIn;
