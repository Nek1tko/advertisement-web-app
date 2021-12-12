import React, { useState } from "react";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import ReactPhoneInput from 'react-phone-input-mui';
import Alert from '@mui/material/Alert';
import Collapse from '@mui/material/Collapse';
import { Box } from "@material-ui/core";

const Login = props => {
    // create state variables for each input
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');
    const [errorOpen, setErrorOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = e => {
        e.preventDefault();
        console.log(phone, password);
        if (phone.length !== 18) {
            setErrorOpen(true);
            setErrorMessage('Номер телефона введен не до конца');
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
                disable type="submit"
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
                style={{ textTransform: 'none' }}
            >
                Новый пользователь
            </Button>

        </Box >
    );
}

export default Login;
