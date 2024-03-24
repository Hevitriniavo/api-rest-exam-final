import {
  Box,
  Button,
  Grid,
  IconButton,
  InputAdornment,
  TextField,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import { makeStyles } from "@mui/styles";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { useFormik } from "formik";
import * as yup from "yup";
import axios from "axios";
import SingIn from "./SingIn";
import { useNavigate } from "react-router-dom";
import StateNotification from "../../../component/Common/StateNotification";
import Waiter from "../../../component/Common/Waiter";
import { UrlSite } from "../../../utils";
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
const validationSchema = yup.object({
  firstName: yup
    .string("Veuillez entrer votre nom")
    .required("Le nom est obligatoire"),
  lastName: yup
    .string("Veuillez entrer votre nprénom")
    .required("Le prénom est obligatoire"),
  userName: yup
    .string("Veuillez entrer votre nom d'utilisateur")
    .required("Le nom d'utilisateur est obligatoire"),
  dateDeN: yup
    .date("Format de date invalide")
    .required("La date de naissance est obligatoire"),
  email: yup
    .string("Veuillez entrer votre adresse e-mail")
    .email("Veuillez entrer une adresse e-mail valide")
    .required(`'L'adresse e-mail est obligatoire'`),
  password: yup
    .string("Veuillez entrer votre mot de passe")
    .min(4, "Le mot de passe doit contenir au moins 8 caractères")
    .required("Le mot de passe est obligatoire"),
  ConfirmPassword: yup
    .string("Veuillez confirmer votre mot de passe")
    .oneOf(
      [yup.ref("password"), null],
      "Les mots de passe doivent correspondre"
    )
    .required("Veuillez confirmer votre mot de passe"),
});
function SlideNavbar() {
  const [isChecked, setIsChecked] = useState(true);
  const [isLoading, setLoading] = useState(false);
  const [showSuccessDialog, setShowSuccessDialog] = useState(false);
  const [showErrorDialog, setShowErrorDialog] = useState(false);
  const navigate = useNavigate();
  const classes = useStyles();

  const submitFormData = async (values) => {
    console.log("Submitting form data model");
    const formData = new FormData();

    formData.append("firstName", values.firstName);
    formData.append("lastName", values.lastName);
    formData.append("username", values.userName);
    formData.append("birthday", values.dateDeN);
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
      url:UrlSite("users"),
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
        setShowSuccessDialog(true);
        // navigate("/");
      })
      .catch((error) => {
        console.log("anaty catch");
        console.error(error);
        // setLoad(false);
        setLoading(false);
        setShowErrorDialog(true);
      });
  };
  const formik = useFormik({
    initialValues: {
      lastName: "",
      firstName: "",
      email: "",
      userName: "",
      dateDeN: "",
      password: "",
      ConfirmPassword: "",
      showPassword: false,
      showConfirmPassword: false,
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      console.log(JSON.stringify(values, null, 2));
      submitFormData(values);
      setLoading(true);
    },
  });
  const handleCloseSuccessDialog = () => {
    setShowSuccessDialog(false);
    // navigate('/');
  };

  const handleCloseErrorDialog = () => {
    setShowErrorDialog(false);
  };

  useEffect(() => {
    if (isChecked) {
      navigate("/login");
    } else {
      navigate("/inscription");
    }
  }, [isChecked, navigate]);
  return (
    <>
      {isLoading && <Waiter />}
      <div
        style={{
          margin: 0,
          padding: 0,
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          minHeight: "100vh",
          fontFamily: "Jost, sans-serif",
          background: "linear-gradient(to bottom, #0f0c29, #5a189a, #24243e)",
        }}
      >
        <div
          style={{
            width: 350,
            height: isChecked ? 500 : 600, // La hauteur varie en fonction de isChecked
            background: "linear-gradient(to bottom, #0f0c29, #5a189a, #24243e)",
            overflow: "hidden",
            borderRadius: 10,
            boxShadow: "5px 20px 50px #000",
            transition: "height .8s ease-in-out", // Transition pour l'animation
          }}
        >
          <input
            type="checkbox"
            id="chk"
            aria-hidden="true"
            style={{ display: "none" }}
            checked={isChecked}
            onChange={() => setIsChecked(!isChecked)}
          />
          <div
            className="signup"
            style={{ position: "relative", width: "100%", height: "100%" }}
          >
            <label
              htmlFor="chk"
              aria-hidden="true"
              style={{
                color: "#fff",
                fontSize: "2.3em",
                justifyContent: "center",
                display: "flex",
                margin: "10px",
                fontWeight: "bold",
                cursor: "pointer",
                transition: ".5s ease-in-out",
              }}
            >
              Inscription
            </label>
            <form onSubmit={formik.handleSubmit}>
              <Grid
                container
                justifyContent={"center"}
                alignItems={"center"}
                my={2.5}
                style={{
                  opacity: !isChecked ? 1 : 0,
                  transition: ".8s ease-in-out",
                }}
              >
                <Box borderRadius={1} width={"70%"}>
                  <TextField
                    label="Nom"
                    className={classes.textField}
                    variant="outlined"
                    color="secondary"
                    sx={{ bgcolor: "white", borderRadius: 1, zIndex: 0 }}
                    size="small"
                    fullWidth
                    id="lastName"
                    name="lastName"
                    value={formik.values.lastName}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={
                      formik.touched.lastName && Boolean(formik.errors.lastName)
                    }
                    helperText={
                      formik.touched.lastName && formik.errors.lastName
                    }
                  />
                </Box>
              </Grid>
              <Grid
                container
                justifyContent={"center"}
                alignItems={"center"}
                my={2.5}
                style={{
                  opacity: !isChecked ? 1 : 0,
                  transition: ".8s ease-in-out",
                }}
              >
                <Box borderRadius={1} width={"70%"}>
                  <TextField
                    label="Prenom"
                    className={classes.textField}
                    fullWidth
                    id="firstName"
                    name="firstName"
                    variant="outlined"
                    color="secondary"
                    sx={{ bgcolor: "white", borderRadius: 1, zIndex: 0 }}
                    size="small"
                    value={formik.values.firstName}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={
                      formik.touched.firstName &&
                      Boolean(formik.errors.firstName)
                    }
                    helperText={
                      formik.touched.firstName && formik.errors.firstName
                    }
                  />
                </Box>
              </Grid>
              <Grid
                container
                justifyContent={"center"}
                alignItems={"center"}
                my={2.5}
                style={{
                  opacity: !isChecked ? 1 : 0,
                  transition: ".8s ease-in-out",
                }}
              >
                <Box borderRadius={1} width={"70%"}>
                  <TextField
                    label="Nom d'utilisateur"
                    className={classes.textField}
                    fullWidth
                    variant="outlined"
                    color="secondary"
                    sx={{ bgcolor: "white", borderRadius: 1, zIndex: 0 }}
                    size="small"
                    id="userName"
                    name="userName"
                    value={formik.values.userName}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={
                      formik.touched.userName && Boolean(formik.errors.userName)
                    }
                    helperText={
                      formik.touched.userName && formik.errors.userName
                    }
                  />
                </Box>
              </Grid>
              <Grid
                container
                justifyContent={"center"}
                alignItems={"center"}
                my={2.5}
                style={{
                  opacity: !isChecked ? 1 : 0,
                  transition: ".8s ease-in-out",
                }}
              >
                <Box borderRadius={1} width={"70%"}>
                  <TextField
                    label="Date de naissance"
                    type="date"
                    className={classes.textField}
                    fullWidth
                    id="dateDeN"
                    name="dateDeN"
                    value={formik.values.dateDeN}
                    onBlur={formik.handleBlur}
                    onChange={formik.handleChange}
                    error={
                      formik.touched.dateDeN && Boolean(formik.errors.dateDeN)
                    }
                    helperText={formik.touched.dateDeN && formik.errors.dateDeN}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    variant="outlined"
                    color="secondary"
                    sx={{ bgcolor: "white", borderRadius: 1, zIndex: 0 }}
                    size="small"
                  />
                </Box>
              </Grid>
              <Grid
                container
                justifyContent={"center"}
                alignItems={"center"}
                my={2.5}
                style={{
                  opacity: !isChecked ? 1 : 0,
                  transition: ".8s ease-in-out",
                }}
              >
                <Box borderRadius={1} width={"70%"}>
                  <TextField
                    label="Email"
                    className={classes.textField}
                    variant="outlined"
                    color="secondary"
                    sx={{ bgcolor: "white", borderRadius: 1, zIndex: 0 }}
                    size="small"
                    fullWidth
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
                style={{
                  opacity: !isChecked ? 1 : 0,
                  transition: ".8s ease-in-out",
                }}
              >
                <Box borderRadius={1} width={"70%"} position={"relative"}>
                  <TextField
                    label="Mot de passe"
                    className={classes.textField}
                    fullWidth
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
                    helperText={
                      formik.touched.password && formik.errors.password
                    }
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
              <Grid
                container
                justifyContent={"center"}
                alignItems={"center"}
                my={2.5}
                style={{
                  opacity: !isChecked ? 1 : 0,
                  transition: ".8s ease-in-out",
                }}
              >
                <Box borderRadius={1} width={"70%"}>
                  <TextField
                    label="Confirmation de mot de passe"
                    className={classes.textField}
                    fullWidth
                    variant="outlined"
                    color="secondary"
                    sx={{ bgcolor: "white", borderRadius: 1, zIndex: 0 }}
                    size="small"
                    id="ConfirmPassword"
                    name="ConfirmPassword"
                    type={
                      formik.values.showConfirmPassword ? "text" : "password"
                    }
                    value={formik.values.ConfirmPassword}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={
                      formik.touched.ConfirmPassword &&
                      Boolean(formik.errors.ConfirmPassword)
                    }
                    helperText={
                      formik.touched.ConfirmPassword &&
                      formik.errors.ConfirmPassword
                    }
                    InputProps={{
                      endAdornment: (
                        <InputAdornment position="end">
                          <IconButton
                            onClick={() =>
                              formik.setValues({
                                ...formik.values,
                                showConfirmPassword:
                                  !formik.values.showConfirmPassword,
                              })
                            }
                            edge="end"
                          >
                            {formik.values.showConfirmPassword ? (
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
                type="submit"
                style={{
                  width: "60%",
                  height: 40,
                  margin: "10px auto",
                  justifyContent: "center",
                  display: "block",
                  color: "#fff",
                  background: "#573b8a",
                  // fontSize: "1em",
                  fontWeight: "bold",
                  marginTop: 20,
                  // outline: "none",
                  border: "none",
                  borderRadius: 5,
                  transition: ".2s ease-in",
                  // cursor: "pointer",
                }}
              >
                Submit
              </Button>
            </form>
          </div>
          <SingIn
            checked={isChecked}
            load={setLoading}
            errorMessage={showErrorDialog}
          />
        </div>
        <StateNotification
          showSuccessDialog={showSuccessDialog}
          showErrorDialog={showErrorDialog}
          handleCloseSuccessDialog={handleCloseSuccessDialog}
          successMessage="Votre inscription a été complétée avec succès !"
          handleCloseErrorDialog={handleCloseErrorDialog}
          errorMessage="Veuillez réessayer"
        />
      </div>
    </>
  );
}

export default SlideNavbar;
