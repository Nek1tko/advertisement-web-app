import React, {useState} from 'react';
import {Collapse, makeStyles} from '@material-ui/core';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Box from '@mui/material/Box';
import {Alert} from "@mui/material";
import ReactPhoneInput from "react-phone-input-mui";

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        padding: theme.spacing(2),

        '& .MuiTextField-root': {
            margin: theme.spacing(1),
            width: '300px',
        },
        '& .MuiButtonBase-root': {
            margin: theme.spacing(2),
        },
    },
}));

const Registration = props => {
    const classes = useStyles();
    // create state variables for each input
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');
    const [passwordConfirmation, setPasswordConfirmation] = useState('');
    const [alertOpen, setAlertOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = e => {
        e.preventDefault();
        console.log(firstName, lastName, phone, password);
        if (password !== passwordConfirmation) {
            setErrorMessage("Пароли не совпадают!");
            setAlertOpen(true);
            return;
        }
        if (password.length < 4 || password.length > 16) {
            setErrorMessage("Пароль должен содержать от 4 до 16 символов!");
            setAlertOpen(true);
            return;
        }
    };

    const handlePasswordTyping = (e) => {
        setPassword(e.target.value)
    }

    const handlePasswordConfirmationTyping = (e) => {
        setPasswordConfirmation(e.target.value)
    }

    const handleFirstNameTyping = (e) => {
        setFirstName(e.target.value);
    }

    const handleLastNameTyping = (e) => {
        setLastName(e.target.value)
    }

    const handlePhoneTyping = (e) => {
        setPhone(e);
    }

    return (
        <Box className={classes.root}>
            <Collapse in={alertOpen}>
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
                required
                value={firstName}
                onChange={handleFirstNameTyping}
            />
            <TextField
                label="Фамилия"
                variant="filled"
                required
                value={lastName}
                onChange={handleLastNameTyping}
            />
            <ReactPhoneInput
                value={phone}
                defaultCountry={'ru'}
                onChange={handlePhoneTyping}
                component={TextField}
                inputExtraProps={{
                    name: 'phone',
                    variant: "filled",
                    label: "Номер телефона"
                }}
            />
            <TextField
                label="Пароль"
                variant="filled"
                type="password"
                required
                value={password}
                onChange={handlePasswordTyping}
            />
            <TextField
                label="Подтверждение пароля"
                variant="filled"
                type="password"
                required
                value={passwordConfirmation}
                onChange={handlePasswordConfirmationTyping}
            />
            <div>
                <Button variant="contained">
                    Отмена
                </Button>
                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    onClick={handleSubmit}
                    disabled={!firstName || !lastName || !phone || !password || !passwordConfirmation}
                >
                    Регистрация
                </Button>
            </div>
        </Box>
    );
};

export default Registration;