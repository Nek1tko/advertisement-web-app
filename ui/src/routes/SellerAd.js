import React, {useState} from 'react';
import AwesomeSlider from 'react-awesome-slider';
import AwesomeSliderStyles from 'react-awesome-slider/src/styles';
import eren from '../img/eren.jpg'
import mikasa from '../img/mikasa.jpg'
import levi from '../img/levi.png'
import {Alert, Box, Typography} from "@mui/material";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import {Collapse} from "@material-ui/core";
import DialogActions from "@mui/material/DialogActions";
import Dialog from "@mui/material/Dialog";


const SellerAd = props => {
    const [description, setDescription] = useState('');
    const [open, setOpen] = useState(false);
    const [errorOpen, setErrorOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [editedDescription, setEditedDescription] = useState(description);
    const [price, setPrice] = useState('');

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleCancelClose = () => {
        setOpen(false);
        setErrorOpen(false);
        setEditedDescription(description);
    };

    const handleSaveClose = () => {
        setOpen(false);
        setErrorOpen(false);
        setDescription(editedDescription);
    };

    return (
        <div style={{display: "flex"}}>
            <Box sx={{width: 1 / 2, height: 1 / 2, flex: 1}}>
                <Typography variant="h5" align="center" style={{color: "#666666", marginTop: 30, marginBottom: 30}}>
                    Название объявления
                </Typography>
                <AwesomeSlider
                    animation="foldOutAnimation"
                    cssModule={AwesomeSliderStyles}
                >
                    <div>
                        <img alt="Ad preview" src={eren} style={{height: '100%', width: '100%', objectFit: 'contain'}}/>
                    </div>
                    <div>
                        <img alt="Ad preview" src={mikasa}
                             style={{height: '100%', width: '100%', objectFit: 'contain'}}/>
                    </div>
                    <div>
                        <img alt="Ad preview" src={levi} style={{height: '100%', width: '100%', objectFit: 'contain'}}/>
                    </div>
                </AwesomeSlider>
            </Box>
            <Box sx={{width: 1 / 2, height: 1 / 2, flex: 1}}>
                <Typography variant="h5" align="center" style={{color: "#666666", marginTop: 30, marginBottom: 30}}>
                    Цена: {price}
                </Typography>
                <TextField
                    label="Описание"
                    variant="filled"
                    multiline
                    maxRows={14}
                    minRows={14}
                    onChange={e => {
                        setDescription(e.target.value);
                    }}
                    value={description}
                    fullWidth
                    disabled={true}
                />
                <Button
                    fullWidth
                    variant="outlined"
                    onClick={handleClickOpen}
                >
                    Редактировать
                </Button>
                <Dialog
                    open={open}
                    onClose={handleCancelClose}
                    fullWidth
                    maxWidth="md"
                >
                    <DialogTitle>Редактирование описания</DialogTitle>

                    <DialogContent>
                        <DialogContentText>
                            Введите новое описание
                        </DialogContentText>

                        <Collapse in={errorOpen}>
                            <Alert
                                severity="error"
                                sx={{mb: 2}}
                            >
                                {errorMessage}
                            </Alert>
                        </Collapse>

                        <TextField
                            label="Описание"
                            variant="filled"
                            value={editedDescription}
                            multiline
                            maxRows={9}
                            minRows={9}
                            onChange={e => {
                                const value = e.target.value;
                                if (value.length < 1 || value.length > 250) {
                                    setErrorOpen(true);
                                    setErrorMessage("Длина описания не должна быть меньше 4 и больше 250");
                                } else {
                                    setEditedDescription(value);
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
        </div>
    )
};

export default SellerAd;