import React, {useState} from "react";
import {Box, Typography} from "@mui/material";
import AwesomeSlider from "react-awesome-slider";
import AwesomeSliderStyles from "react-awesome-slider/src/styles";
import eren from "../img/eren.jpg";
import mikasa from "../img/mikasa.jpg";
import levi from "../img/levi.png";
import TextField from "@material-ui/core/TextField";

const CustomerAd = props => {
    const [description, setDescription] = useState('');
    const [name, setName] = useState('');
    const [phone, setPhone] = useState('');
    const [price, setPrice] = useState('');

    return (<div style={{display: "flex"}}>
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
                <Typography
                    variant="h6"
                    align="left"
                    style={{color: "#666666", marginTop: 32, marginBottom: 12, marginLeft: 10}}
                >
                    Имя: {name}
                </Typography>
                <Typography
                    variant="h6"
                    align="left"
                    style={{color: "#666666", marginTop: 12, marginBottom: 12, marginLeft: 10}}
                >
                    Телефон: {phone}
                </Typography>
                <TextField
                    label="Описание"
                    variant="filled"
                    multiline
                    maxRows={11}
                    minRows={11}
                    value={description}
                    fullWidth
                    disabled={true}
                />
            </Box>
        </div>
    );
};

export default CustomerAd;