import React, { useState, useEffect } from "react";
import { Box, Typography } from "@mui/material";
import AwesomeSlider from "react-awesome-slider";
import AwesomeSliderStyles from "react-awesome-slider/src/styles";
import TextField from "@material-ui/core/TextField";
import axios from 'axios';
import authHeader from "../services/auth-header";
import FavoriteIcon from '@mui/icons-material/Favorite';
import IconButton from '@mui/material/IconButton';

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
    const [isFavorites, setIsFavorites] = useState(ad.isFavourite);
    const [favIconColor, setFavIconColor] = useState(ad.isFavourite ? "#E75480" : "#BDBDBD");
    const [isActive, setIsActive] = useState(ad.is_active);

    useEffect(() => {
        axios
            .get(API_URL + "image/" + ad.id, { headers: authHeader() })
            .then(res => {
                setImgs(res.data.map(img => { return API_URL + "img/" + img.path }));
            })
    }, []);

    const handleFavClick = () => {
        axios
            .put(API_URL + "ad/favourites",
                {
                    adId: ad.id,
                    isFavourite: !isFavorites
                },
                { headers: authHeader() })
            .then(res => {
                setFavIconColor(res.data.isFavourite ? "#E75480" : "#BDBDBD");
                setIsFavorites(res.data.isFavourite);
            });
    }

    return (
        <Box display='flex'
            flexDirection='column'
            justify-content='space-between'
        >
            <Box
                display='flex'
                flexDirection='row'
                justify-content='space-between'
            >
                <Typography variant="h3" align="left" style={{ marginTop: 30 }}>
                    {adName}
                </Typography>

                {!isActive &&
                    <Typography variant="h4" align="left" style={{ marginTop: 40, marginLeft: 20, color: "#777777" }}>
                        снято с продажи
                    </Typography>
                }

                <IconButton
                    align="right"
                    style={{ marginTop: 20, marginLeft: 'auto' }} size="large"
                    onClick={handleFavClick}
                >
                    <FavoriteIcon fontSize="inherit" sx={{ color: favIconColor }} />
                </IconButton>
            </Box>

            <Box
                display='flex'
                flexDirection='row'
                justify-content='space-between'
            >
                <Box sx={{ width: 1 / 2, flex: 1 }}>
                    <Typography align="left" style={{ color: "#666666", marginTop: 10 }}>
                        {metro.name}
                    </Typography>

                    <Typography align="left" style={{ color: "#666666" }}>
                        {category.name} → {subcategory.name}
                    </Typography>

                    <AwesomeSlider
                        animation="foldOutAnimation"
                        cssModule={AwesomeSliderStyles}
                        style={{ marginTop: 20 }}
                    >
                        {imgs.map(img => {
                            return (
                                <div data-src={img} />
                            );
                        })}
                    </AwesomeSlider>
                </Box>

                <Box sx={{ width: 1 / 2, height: 1 / 2, flex: 1 }}>
                    <Typography variant="h5" align="right">
                        {price}₽
                    </Typography>

                    <Typography
                        variant="h5"
                        align="right"
                        style={isActive ? { fontWeight: 550 } : { color: '#777777' }}>
                        {name} {phone}
                    </Typography>

                    <TextField
                        label="Описание"
                        variant="filled"
                        multiline
                        maxRows={18}
                        minRows={18}
                        value={description}
                        fullWidth
                        disabled={true}
                        style={{ marginTop: 15 }}
                    />

                </Box>
            </Box>
        </Box>
    );
};

export default CustomerAd;