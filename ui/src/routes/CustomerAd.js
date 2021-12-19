import React, { useState } from "react";
import { Box, Typography } from "@mui/material";
import AwesomeSlider from "react-awesome-slider";
import AwesomeSliderStyles from "react-awesome-slider/src/styles";
import eren from "../img/eren.jpg";
import mikasa from "../img/mikasa.jpg";
import levi from "../img/levi.png";
import TextField from "@material-ui/core/TextField";

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
                <div>
                    <img alt="Ad preview" src={eren} style={{ height: '100%', width: '100%', objectFit: 'contain' }} />
                </div>
                <div>
                    <img alt="Ad preview" src={mikasa}
                        style={{ height: '100%', width: '100%', objectFit: 'contain' }} />
                </div>
                <div>
                    <img alt="Ad preview" src={levi} style={{ height: '100%', width: '100%', objectFit: 'contain' }} />
                </div>
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