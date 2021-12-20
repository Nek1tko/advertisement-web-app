import React, { useState, useEffect } from 'react';
import AwesomeSlider from 'react-awesome-slider';
import AwesomeSliderStyles from 'react-awesome-slider/src/styles';
import { Alert, Box, Typography, FormControlLabel, Checkbox } from "@mui/material";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import { Collapse, InputAdornment, MenuItem } from "@material-ui/core";
import DialogActions from "@mui/material/DialogActions";
import Dialog from "@mui/material/Dialog";
import axios from 'axios';
import authHeader from "../services/auth-header";
import FavoriteIcon from '@mui/icons-material/Favorite';
import IconButton from '@mui/material/IconButton';

const API_URL = "http://localhost:8080/";

const SellerAd = props => {
    const ad = (props.location && props.location.ad) || {};

    const [description, setDescription] = useState(ad.description);
    const [open, setOpen] = useState(false);
    const [errorOpen, setErrorOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [editedDescription, setEditedDescription] = useState(description);
    const [price, setPrice] = useState(ad.price);
    const [name, setName] = useState(ad.name);
    const [editedName, setEditedName] = useState(name);
    const [editedPrice, setEditedPrice] = useState(price);
    const [metroList, setMetroList] = useState([]);
    const [metro, setMetro] = useState(ad.metro);
    const [editedMetro, setEditedMetro] = useState(ad.metro);
    const [categoryList, setCategoryList] = useState([]);
    const [category, setCategory] = useState(ad.subcategory.category);
    const [subcategoryList, setSubcategoryList] = useState([]);
    const [subcategory, setSubcategory] = useState(ad.subcategory);
    const [editedCategory, setEditedCategory] = useState(category);
    const [editedSubcategory, setEditedSubcategory] = useState(subcategory);
    const [imgs, setImgs] = useState([]);
    const [isFavorites, setIsFavorites] = useState(ad.isFavourite);
    const [favIconColor, setFavIconColor] = useState(ad.isFavourite ? "#E75480" : "#BDBDBD");
    const [isActive, setIsActive] = useState(ad.is_active);
    const [editedIsActive, setEditedIsActive] = useState(ad.is_active);

    useEffect(() => {
        axios
            .get(API_URL + "image/" + ad.id, { headers: authHeader() })
            .then(res => {
                setImgs(res.data.map(img => { return API_URL + "img/" + img.path }));
            })
    }, []);

    useEffect(() => {
        axios
            .get(API_URL + "metro", { headers: authHeader() })
            .then(res => {
                setMetroList(res.data);
                setEditedMetro(res.data[editedMetro.id - 1]);
            })
    }, []);

    useEffect(() => {
        axios
            .get(API_URL + "category", { headers: authHeader() })
            .then(res => {
                setCategoryList(res.data);
                setEditedCategory(res.data[editedCategory.id - 1])
            })
    }, []);

    useEffect(() => {
        axios
            .get(API_URL + "category/" + editedCategory.id, { headers: authHeader() })
            .then(res => {
                setSubcategoryList(res.data);
                setEditedSubcategory('');
            })
    }, [editedCategory]);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleCancelClose = () => {
        setOpen(false);
        setErrorOpen(false);
        setEditedDescription(description);
    };

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

    const handleSaveClose = () => {
        if (!editedSubcategory) {
            setErrorOpen(true);
            setErrorMessage('Выберите подкатегорию');
            return;
        }

        setOpen(false);
        setErrorOpen(false);

        axios
            .put(API_URL + "ad", {
                id: ad.id,
                name: editedName,
                price: editedPrice,
                description: editedDescription,
                metro: editedMetro,
                subCategory: editedSubcategory,
                isActive: editedIsActive
            },
                {
                    headers: authHeader()
                })
            .then(res => {
                console.log(res);
                setDescription(res.data.description);
                setName(res.data.name);
                setPrice(res.data.price);
                setMetro(res.data.metro);
                setSubcategory(res.data.subCategory);
                setCategory(res.data.subCategory.category);
                setIsActive(res.data.isActive);
            });

    };

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
                    {name}
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

                    <TextField
                        label="Описание"
                        variant="filled"
                        multiline
                        maxRows={18}
                        minRows={18}
                        onChange={e => {
                            setDescription(e.target.value);
                        }}
                        value={description}
                        fullWidth
                        disabled={true}
                        style={{ marginTop: 45 }}
                    />

                    <Button
                        fullWidth
                        variant="contained"
                        color='primary'
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
                        <DialogTitle>Редактирование</DialogTitle>

                        <DialogContent>
                            <DialogContentText>
                                Введите новые данные
                            </DialogContentText>

                            <Collapse in={errorOpen}>
                                <Alert
                                    severity="error"
                                    sx={{ mb: 2 }}
                                >
                                    {errorMessage}
                                </Alert>
                            </Collapse>

                            <FormControlLabel
                                label="Снято с продажи"
                                control={
                                    <Checkbox
                                        checked={!editedIsActive}
                                        onChange={e => {
                                            setEditedIsActive(!e.target.checked);
                                        }}
                                    />
                                }
                            />

                            <TextField
                                label="Название"
                                variant="filled"
                                value={editedName}
                                onChange={e => {
                                    const value = e.target.value;
                                    if (value.length < 3 || value.length > 50) {
                                        setErrorOpen(true);
                                        setErrorMessage("Название объявления должно быть длиной от 3 до 50 символов");
                                    }
                                    else {
                                        setEditedName(value);
                                        setErrorOpen(false);
                                    }
                                }}
                                fullWidth
                            />

                            <TextField
                                select
                                variant="filled"
                                label="Метро"
                                value={editedMetro}
                                onChange={e => {
                                    setEditedMetro(e.target.value);
                                }}
                                fullWidth
                            >
                                {metroList.map(metro => (
                                    <MenuItem key={metro.id} value={metro}>
                                        {metro.name}
                                    </MenuItem>
                                ))}
                            </TextField>

                            <TextField
                                label="Цена"
                                variant="filled"
                                type="number"
                                fullWidth
                                InputProps={{
                                    endAdornment: <InputAdornment position="end">₽</InputAdornment>,
                                }}
                                onChange={e => {
                                    if (e.target.value >= 0) {
                                        setEditedPrice(e.target.value);
                                    }
                                }}
                                value={editedPrice}
                            />

                            <TextField
                                select
                                variant="filled"
                                label="Категория"
                                value={editedCategory}
                                onChange={e => {
                                    const value = e.target.value;
                                    setEditedCategory(value);
                                }}
                                fullWidth
                            >
                                {categoryList.map(category => {
                                    return (
                                        <MenuItem
                                            key={category.id}
                                            value={category}
                                        >
                                            {category.name}
                                        </MenuItem>);
                                })}
                            </TextField>

                            <TextField
                                disabled={!category}
                                select
                                variant="filled"
                                label="Подкатегория"
                                value={editedSubcategory}
                                onChange={e => {
                                    const value = e.target.value;
                                    setEditedSubcategory(value);
                                }}
                                fullWidth
                            >
                                {subcategoryList.map(subcategory => {
                                    return (
                                        <MenuItem
                                            key={subcategory.id}
                                            value={subcategory}
                                        >
                                            {subcategory.name}
                                        </MenuItem>);
                                })}
                            </TextField>

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
                                        setErrorMessage("Длина описания не должна быть меньше 1 и больше 250");
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
            </Box>
        </Box >
    );
};

export default SellerAd;