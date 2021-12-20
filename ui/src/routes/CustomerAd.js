import React, { useState, useEffect } from "react";
import { Box, Typography } from "@mui/material";
import AwesomeSlider from "react-awesome-slider";
import AwesomeSliderStyles from "react-awesome-slider/src/styles";
import TextField from "@material-ui/core/TextField";
import axios from 'axios';
import authHeader from "../services/auth-header";

const API_URL = "http://localhost:8080/";

const CustomerAd = props => {
    const ad = (props.location && props.location.ad) || {};

    const [description, setDescription] = useState(ad.description);
    const [name, setName] = useState(ad.saler.name);
    const [phone, setPhone] = useState(ad.saler.phoneNumber);
    const [adName, setAdName] = useState(ad.name);
    const [price, setPrice] = useState(ad.price);
    const [metro, setMetro] = useState(ad.metro);
    const [category, setCategory] = useState(ad.subcategory.category);
    const [subcategory, setSubcategory] = useState(ad.subcategory);
    const [imgs, setImgs] = useState([]);

    useEffect(() => {
        axios
            .get(API_URL + "image/" + ad.id, { headers: authHeader() })
            .then(res => {
                setImgs(res.data.map(img => { return API_URL + "img/" + img.path }));
            })
    }, []);

    return (<div style={{ display: "flex" }}>
        <Box sx={{ width: 1 / 2, height: 1 / 2, flex: 1 }}>
            <Typography variant="h5" align="center" style={{ color: "#666666", marginTop: 30 }}>
                {adName}
            </Typography>

            <Typography align="center" style={{ color: "#666666", marginTop: 10, marginBottom: 10 }}>
                {metro.name}
            </Typography>

            <AwesomeSlider
                animation="foldOutAnimation"
                cssModule={AwesomeSliderStyles}
            >
                {imgs.map(img => {
                    return (
                        <div data-src={img} />
                    );
                })}
            </AwesomeSlider>
        </Box>

        <Box sx={{ width: 1 / 2, height: 1 / 2, flex: 1 }}>
            <Typography variant="h5" align="center" style={{ color: "#666666", marginTop: 30 }}>
                Цена: {price}
            </Typography>

            <Typography align="center" style={{ color: "#666666", marginTop: 10, marginBottom: 10 }}>
                {category.name}: {subcategory.name}
            </Typography>

            <Typography
                variant="h6"
                align="center"
                style={{ color: "#666666", marginTop: 10, marginBottom: 10 }}
            >
                {name}: {phone}
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