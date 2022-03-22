import React, { useState } from "react";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import ReactPhoneInput from 'react-phone-input-mui';
import Alert from '@mui/material/Alert';
import Collapse from '@mui/material/Collapse';
import { Box } from "@material-ui/core";
import AuthService from "../services/auth.service";

const Login = props => {
    const { history } = props;
    // create state variables for each input
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');
    const [errorOpen, setErrorOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = e => {
        e.preventDefault();
        if (phone.length !== 18) {
            setErrorOpen(true);
            setErrorMessage('Номер телефона введен неверно');
            return;
        }
        if (password.length === 0) {
            setErrorOpen(true);
            setErrorMessage('Введите пароль');
            return;
        }
        if (password.length < 4) {
            setErrorOpen(true);
            setErrorMessage('Длина пароля должна быть не менее 4-х символов');
            return;
        }

        AuthService.signIn(phone, password)
            .then(res => {
                history.push('/');
            })
            .catch(reasone => {
                setErrorOpen(true);
                setErrorMessage('Такого пользователя не существует или пароль не верный');
                setPassword('');
            });
    };

    return (
        <Box
            display='flex'
            flexDirection='column'
            justifyContent='space-between'
            sx={{ mt: 2, width: '25%', m: 'auto' }}
        >
            <Collapse in={errorOpen}>
                <Alert
                    severity="error"
                    sx={{ mb: 2 }}
                >
                    {errorMessage}
                </Alert>
            </Collapse>

            <ReactPhoneInput
                id="phoneInput"
                value={phone}
                defaultCountry={'ru'}
                onChange={value => {
                    setPhone(value);
                    setErrorOpen(false);
                }}
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
                value={password}
                onChange={e => {
                    setPassword(e.target.value);
                    setErrorOpen(false);
                }}
                fullWidth
            />

            <Button
                id="loginButton"
                type="submit"
                variant="contained"
                color="primary"
                onClick={handleSubmit}
                fullWidth
            >
                Войти
            </Button>

            <Button
                variant="text"
                disableRipple
                fullWidth
                onClick={e => { history.push('/sign-up'); }}
                style={{ textTransform: 'none' }}
            >
                Регистрация
            </Button>

        </Box >
    );
}

export default Login;
