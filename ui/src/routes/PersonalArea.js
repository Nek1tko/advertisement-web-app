import React, { useState } from "react";
import { Box, Button, Typography } from "@material-ui/core";
import AuthService from '../services/auth.service';
import { Redirect } from "react-router-dom";
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { Collapse } from "@material-ui/core";
import { Alert } from "@mui/material";

const PersonalArea = props => {
    const [name, setName] = useState('Test name');
    const [surname, setSurname] = useState('Test surname');
    const [open, setOpen] = useState(false);
    const [errorOpen, setErrorOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const [editedName, setEditedName] = useState(name);
    const [editedSurname, setEditedSurname] = useState(surname);


    if (!AuthService.getUser()) {
        return <Redirect to="/login" />
    }

    const { phoneNumber, userId, } = AuthService.getUser();

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleCancelClose = () => {
        setOpen(false);
        setErrorOpen(false);
        setEditedName(name);
        setEditedSurname(surname);
    };

    const handleSaveClose = () => {
        setOpen(false);
        setErrorOpen(false);
        setName(editedName);
        setSurname(editedSurname);
    };

    return (
        <Box
            display='flex'
            flexDirection='column'
            justifyContent='space-between'
            sx={{ mt: 10, width: '25%', m: 'auto' }}
        >
            <Typography variant="h4" align="center">
                {name}
            </Typography>

            <Typography variant="h4" align="center" style={{ marginTop: 30 }}>
                {surname}
            </Typography>

            <Typography variant="h5" align="center" style={{ color: "#666666", marginTop: 30 }}>
                {phoneNumber}
            </Typography>

            <Button
                variant="contained"
                color="primary"
                fullWidth
                mt={20}
                style={{ textTransform: 'none', marginTop: 30 }}
                onClick={handleClickOpen}
            >
                Редактировать
            </Button>

            <Dialog open={open} onClose={handleCancelClose}>
                <DialogTitle>Редактирование имени и фамилии</DialogTitle>

                <DialogContent>
                    <DialogContentText>
                        Введите имя и фамилию
                    </DialogContentText>

                    <Collapse in={errorOpen}>
                        <Alert
                            severity="error"
                            sx={{ mb: 2 }}
                        >
                            {errorMessage}
                        </Alert>
                    </Collapse>

                    <TextField
                        label="Имя"
                        variant="filled"
                        value={editedName}
                        onChange={e => {
                            const value = e.target.value;
                            if (value.length < 3 || value.length > 50) {
                                setErrorOpen(true);
                                setErrorMessage("Длина имени должна быть больше 4 и меньше 50");
                            }
                            else {
                                setEditedName(value);
                                setErrorOpen(false);
                            }
                        }}
                        fullWidth
                    />

                    <TextField
                        label="Фамилия"
                        variant="filled"
                        value={editedSurname}
                        onChange={e => {
                            const value = e.target.value;
                            if (value.length < 3 || value.length > 50) {
                                setErrorOpen(true);
                                setErrorMessage("Длина фамилии должна быть больше 4 и меньше 50");
                            }
                            else {
                                setEditedSurname(value);
                                setErrorOpen(false);
                            }
                        }}
                        fullWidth
                    />
                </DialogContent>

                <DialogActions>
                    <Button onClick={handleCancelClose}>Отмена</Button>
                    <Button onClick={handleSaveClose}>Сохранить</Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
};

export default PersonalArea;
