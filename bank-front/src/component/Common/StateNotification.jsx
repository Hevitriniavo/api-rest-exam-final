/* eslint-disable react/prop-types */
import React from 'react';
import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';

const StateNotification = (props) => {
    return (
        <>
            <Dialog open={props.showSuccessDialog} onClose={props.handleCloseSuccessDialog}>
                <DialogTitle>Succès</DialogTitle>
                <DialogContent>
                    <Alert severity="success">{props.successMessage}</Alert>
                </DialogContent>
                <DialogActions>
                    <Button onClick={props.handleCloseSuccessDialog} color="primary">
                        OK
                    </Button>
                </DialogActions>
            </Dialog>

            <Dialog open={props.showErrorDialog} onClose={props.handleCloseErrorDialog}>
                <DialogTitle>Erreur</DialogTitle>
                <DialogContent>
                    <Alert severity="error">{props.errorMessage}</Alert>
                </DialogContent>
                <DialogActions>
                    <Button onClick={props.handleCloseErrorDialog} color="primary">
                        OK
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
};

export default StateNotification;
