import React, { useState } from "react";
import {
  Alert,
  Box,
  Button,
  Grid,
  IconButton,
  InputAdornment,
  Modal,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import {
  Clear,
  Info,
  InfoOutlined,
  Visibility,
  VisibilityOff,
} from "@mui/icons-material";
import { useFormik } from "formik";
import * as yup from "yup";
import axios from "axios";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  boxShadow: 24,
  borderRadius: 2,
  p: 4,
};

const validationSchema = yup.object({
  amount: yup
    .number()
    .typeError("Veuillez entrer le montant en chiffre")
    .required("Le montant est obligatoire"),
  accont_num: yup
    .number()
    .typeError("Veuillez entrer le numero de destinataire")
    .required("Le numero de destinataire est obligatoire"),

  password: yup
    .string()
    .min(8, "Le mot de passe doit contenir au moins 8 caractères")
    .required("Le mot de passe est obligatoire"),
});

function TransferModal(props) {
  const solde = 10000;
  const open = props.open;
  const close = props.close;
  const [isLoading, setLoading] = useState(false);
  const [amountValue, setAmountValue] = useState("");
  const [numBank, setNumBank] = useState(null);
  const [showPassword, setShowPassword] = useState(false);
  const [currentStep, setCurrentStep] = useState(1);

  const handleOpen = () => {
    close();
  };

  const handleClickShowPassword = () => {
    setShowPassword((prev) => !prev);
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const submitFormData = async (values) => {
    console.log("Submitting form data model");
    try {
      // Votre logique de soumission de formulaire ici
    } catch (error) {
      console.error(error);
      setLoading(false);
    }
  };

  const formik = useFormik({
    initialValues: {
      amount: "",
      accont_num: "",
      password: "",
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      if (currentStep === 1) {
        setCurrentStep(2);
      } else {
        submitFormData(values);
        console.log(JSON.stringify(values, null, 2));
      }
    },
  });

  return (
    <>
      <form onSubmit={formik.handleSubmit}>
        <Modal
          open={open}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <Grid container justifyContent="center" alignItems="center">
              <Box position="absolute" top={5} right={5}>
                <IconButton onClick={handleOpen} sx={{ boxShadow: 5 }}>
                  <Clear />
                </IconButton>
              </Box>
              <Typography
                variant="h3"
                fontWeight="bold"
                fontFamily="monospace"
                fontStyle="italic"
                color="#5a189a"
                width={"100%"}
                textAlign={"center"}
              >
                Transfer
              </Typography>

              {currentStep === 1 && (
                <>
                  <Typography my={1} variant="h7" fontWeight="bold">
                    Veuillez saisir le montant que vous souhaitez retirer.
                  </Typography>
                  {solde < amountValue && (
                    <Grid
                      container
                      justifyContent={"center"}
                      alignItems={"center"}
                      color={"red"}
                    >
                      <InfoOutlined />
                      <Typography
                        // color={"red"}
                        ml={1}
                        variant="h7"
                        // fontWeight="bold"
                      >
                        Votre solde est : {solde}
                      </Typography>
                    </Grid>
                  )}
                  <TextField
                    sx={{ mt: 2 }}
                    fullWidth
                    InputProps={{
                      endAdornment: (
                        <InputAdornment position="end">MGA</InputAdornment>
                      ),
                    }}
                    color="secondary"
                    label="Montant retiré"
                    variant="outlined"
                    id="amount"
                    name="amount"
                    value={formik.values.amount}
                    onChange={(event) => {
                      const newValue = event.target.value;
                      setAmountValue(newValue);
                      formik.setFieldValue("amount", newValue);
                      console.log(newValue);
                    }}
                    onBlur={formik.handleBlur}
                    error={
                      formik.touched.amount && Boolean(formik.errors.amount)
                    }
                    helperText={formik.touched.amount && formik.errors.amount}
                  />
                  <TextField
                    sx={{ mt: 2 }}
                    fullWidth
                    color="secondary"
                    label="Numero de destinataire"
                    variant="outlined"
                    id="num_bank"
                    name="num_bank"
                    value={formik.values.accont_num}
                    onChange={(event) => {
                      const newValue = event.target.value;
                      setNumBank(newValue);
                      formik.setFieldValue("accont_num", newValue);
                      console.log(newValue);
                    }}
                    onBlur={formik.handleBlur}
                    error={
                      formik.touched.num_bank && Boolean(formik.errors.num_bank)
                    }
                    helperText={
                      formik.touched.num_bank && formik.errors.num_bank
                    }
                  />
                  <Grid
                    container
                    display={"flex"}
                    justifyContent={"space-between"}
                    mt={2}
                  >
                    <Button
                      sx={{ bgcolor: "grey" }}
                      variant="contained"
                      onClick={handleOpen}
                    >
                      Annuler
                    </Button>
                    <Button
                      sx={{ bgcolor: "#5a189a" }}
                      variant="contained"
                      color="success"
                      onClick={() => setCurrentStep(2)}
                      disabled={
                        amountValue === "" ||
                        formik.errors.amount ||
                        formik.errors.accont_num ||
                        solde < amountValue ||
                        numBank === null
                      }
                    >
                      Suivante
                    </Button>
                  </Grid>
                </>
              )}
              {currentStep === 2 && (
                <>
                  <Typography
                    textAlign={"center"}
                    my={1}
                    variant="h7"
                    fontWeight="bold"
                  >
                    Entrez votre mot de passe si vous etez sur de retire <br />
                    {amountValue} ariary
                  </Typography>
                  <TextField
                    sx={{ mt: 2 }}
                    fullWidth
                    type={showPassword ? "text" : "password"}
                    InputProps={{
                      endAdornment: (
                        <InputAdornment position="end">
                          <IconButton
                            aria-label="toggle password visibility"
                            onClick={handleClickShowPassword}
                            onMouseDown={handleMouseDownPassword}
                            edge="end"
                          >
                            {showPassword ? <VisibilityOff /> : <Visibility />}
                          </IconButton>
                        </InputAdornment>
                      ),
                    }}
                    label="Mot de passe"
                    variant="outlined"
                    id="password"
                    name="password"
                    value={formik.values.password}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={
                      formik.touched.password && Boolean(formik.errors.password)
                    }
                    helperText={
                      formik.touched.password && formik.errors.password
                    }
                  />
                  <Grid
                    container
                    display={"flex"}
                    justifyContent={"space-between"}
                    mt={2}
                  >
                    <Button
                      sx={{ bgcolor: "grey" }}
                      variant="contained"
                      color="primary"
                      onClick={() => setCurrentStep(1)}
                    >
                      Retour
                    </Button>
                    <Button
                      sx={{ bgcolor: "#5a189a" }}
                      variant="contained"
                      color="success"
                      onClick={formik.handleSubmit}
                    >
                      Valider
                    </Button>
                  </Grid>
                </>
              )}
            </Grid>
          </Box>
        </Modal>
      </form>
    </>
  );
}

export default TransferModal;
