import React, {useState} from 'react';
import {Collapse, makeStyles} from '@material-ui/core';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Box from '@mui/material/Box';
import {Alert} from "@mui/material";
import ReactPhoneInput from "react-phone-input-mui";
import AuthService from "../services/auth.service";

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
    const { history } = props;

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
        if (firstName.length < 3 || firstName.length > 50 || lastName.length < 3 || lastName.length > 50) {
            setErrorMessage("Имя и фамилия должны быть длиной от 3 до 50 символов!");
            setAlertOpen(true);
            return;
        }
        if (phone.length !== 18 || phone.charAt(0) !== '+' || phone.charAt(1) !== '7') {
            setErrorMessage("Введен неверный номер телефона!");
            setAlertOpen(true);
            return;
        }
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
        const regexp = new RegExp("^(?=.*[0-9])(?=.*[!#%{}\\[\\]])[a-zA-Z0-9!#%{}\\[\\]]{4,16}$");
        if (!regexp.test(password)) {
            setErrorMessage("Пароль должен содержать хотя бы одну цифру и спец символ!");
            setAlertOpen(true);
            return;
        }
        setAlertOpen(false);
        AuthService.register(firstName, lastName, phone, password)
            .then(() => {
                history.push('/registration-confirmed');
            })
            .catch(() => {
                setAlertOpen(true);
                setErrorMessage('Ошибка при регистрации!');
                setPassword('');
                setPasswordConfirmation('');
            });
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
                id="nameTextField"
                label="Имя"
                variant="filled"
                required
                value={firstName}
                onChange={handleFirstNameTyping}
            />
            <TextField
                id="lastNameTextField"
                label="Фамилия"
                variant="filled"
                required
                value={lastName}
                onChange={handleLastNameTyping}
            />
            <ReactPhoneInput
                id="phoneInput"
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
                id="passwordTextField"
                label="Пароль"
                variant="filled"
                type="password"
                required
                value={password}
                onChange={handlePasswordTyping}
            />
            <TextField
                id="passwordConfirmationTextField"
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
                    id="registrationButton"
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