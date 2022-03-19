import React, {useEffect} from "react";
import {Box, Button, Collapse, Typography} from "@material-ui/core";
import AuthService from '../services/auth.service';
import {Redirect} from "react-router-dom";
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {Alert} from "@mui/material";
import axios from 'axios';
import authHeader from "../services/auth-header";

const API_URL = "http://localhost:8080/user";

const PersonalAreaImpl = props => {
    const [name, setName] = React.useState('');
    const [surname, setSurname] = React.useState('');
    const [open, setOpen] = React.useState(false);
    const [errorOpen, setErrorOpen] = React.useState(false);
    const [errorMessage, setErrorMessage] = React.useState('');

    const [editedName, setEditedName] = React.useState(name);
    const [editedSurname, setEditedSurname] = React.useState(surname);

    const {phoneNumber, userId,} = AuthService.getUser();

    useEffect(() => {
        axios
            .get(API_URL + '/' + userId, {headers: authHeader()})
            .then(res => {
                setName(res.data.name);
                setSurname(res.data.surname);
                setEditedName(res.data.name);
                setEditedSurname(res.data.surname);
            })
    }, [userId, setName, setSurname]);

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

        axios
            .put(API_URL, {
                id: userId,
                phoneNumber: phoneNumber,
                name: editedName,
                surname: editedSurname
            },
                {
                    headers: authHeader()
                })
            .then(res => {
                setName(res.data.name);
                setSurname(res.data.surname);
            });
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
                id="openButton"
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
                        id="nameTextField"
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
                        id="surnameTextField"
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
                    <Button id="closeButton" onClick={handleCancelClose}>Отмена</Button>
                    <Button id="saveButton" onClick={handleSaveClose}>Сохранить</Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
}

const PersonalArea = props => {
    if (!AuthService.getUser()) {
        return <Redirect to="/login" />
    }

    return <PersonalAreaImpl />
};

export default PersonalArea;
