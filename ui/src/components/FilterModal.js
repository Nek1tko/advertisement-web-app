import React, {useEffect, useState} from "react";
import Button from "@mui/material/Button";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import Dialog from "@mui/material/Dialog";
import FilterListIcon from "@mui/icons-material/FilterList";
import {Collapse, InputAdornment, MenuItem, TextField} from "@material-ui/core";
import axios from "axios";
import authHeader from "../services/auth-header";
import {Alert} from "@mui/material";

const API_URL = "http://localhost:8080/";
const API_URL_SEARCH = "http://localhost:8080/ad/page";

export const FilterModal = (props) => {
    const [metro, setMetro] = useState('');
    const [metroList, setMetroList] = useState([]);

    const [category, setCategory] = useState('');
    const [categoryList, setCategoryList] = useState([]);

    const [minPrice, setMinPrice] = useState('');
    const [maxPrice, setMaxPrice] = useState('');

    const typeList = [
        {
            id: 0,
            name: "Активно",
        },
        {
            id: 1,
            name: "Неактивно",
        },
        {
            id: 2,
            name: "Все",
        }
    ];
    const [isActive, setActive] = useState('');

    const [alertOpen, setAlertOpen] = useState(false);

    useEffect(() => {
        axios
            .get(API_URL + "metro", {headers: authHeader()})
            .then(res => {
                setMetroList(res.data);
            })
    }, []);

    useEffect(() => {
        axios
            .get(API_URL + "category", {headers: authHeader()})
            .then(res => {
                setCategoryList(res.data);
            })
    }, []);

    useEffect(() => {
        axios
            .get(API_URL + "metro", {headers: authHeader()})
            .then(res => {
                setMetroList(res.data);
            })
    }, []);

    const [open, setOpen] = React.useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
        setAlertOpen(false);
    };
    const handleApply = () => {
        if (validatePrice()) {
            setOpen(false);
            setAlertOpen(false);
            const jsonFilter = parseRowsToJSON();
            props.setJsonFilter(jsonFilter);
            // postRequest(jsonFilter);
        } else {
            setAlertOpen(true);
        }
    };
    const handleCancel = () => {
        setOpen(false);
        setAlertOpen(false);
    };

    const validatePrice = () => {
        let isCorrect = true;
        if (minPrice !== "" && maxPrice !== "") {
            if (minPrice > maxPrice) {
                isCorrect = false;
            }
        }
        return isCorrect;
    }

    const parseRowsToJSON = () => {
        let jsonModel = {};
        jsonModel["page"] = props.page;
        if (minPrice !== "") {
            jsonModel["minPrice"] = minPrice;
        }
        if (maxPrice !== "") {
            jsonModel["maxPrice"] = maxPrice;
        }
        if (metro !== '') {
            jsonModel["metroId"] = metro;
        }
        if (category !== '') {
            jsonModel["categoryId"] = category;
        }
        if (isActive !== '') {
            if (isActive === "Активно") {
                jsonModel["isActive"] = true;
            } if (isActive === "Неактивно") {
                jsonModel["isActive"] = false;
            }
        }
        console.log(jsonModel);
        props.setJsonFilter(jsonModel);
        return jsonModel;
    }

    const postRequest = (jsonFilter) => {
        axios
            .post(API_URL_SEARCH, {...jsonFilter}, {headers: authHeader()})
            .then(res => {
                props.setAds(res.data);
            })
    }

    return (
        <div>
            <Button
                onClick={handleClickOpen}
                variant="contained"
                startIcon={<FilterListIcon/>}
                style={{
                    backgroundColor: "#FFFFFF",
                    borderColor: "#000000",
                    color: "#000000",
                    marginTop: 20,
                }}
            >
                Фильтры
            </Button>
            <Dialog
                open={open}
                onClose={handleClose}
                onBackdropClick={handleCancel}
                minwidth="xs"
                fullWidth
            >
                <DialogTitle>
                    Фильтры
                </DialogTitle>
                <DialogContent dividers>
                    <Collapse in={alertOpen}>
                        <Alert
                            severity="error"
                            sx={{mb: 2}}
                        >
                            Мин. цена не может быть больше макс. цены!
                        </Alert>
                    </Collapse>
                    <TextField
                        fullWidth
                        select
                        variant="filled"
                        label="Метро"
                        value={metro}
                        onChange={e => {
                            setMetro(e.target.value);
                        }}
                    >
                        {metroList.map(metro => {
                            return (
                                <MenuItem key={metro.id} value={metro.id}>
                                    {metro.name}
                                </MenuItem>);
                        })}
                    </TextField>

                    <TextField
                        fullWidth
                        select
                        variant="filled"
                        label="Категория"
                        value={category}
                        onChange={e => {
                            const value = e.target.value;
                            setCategory(value);
                        }}
                    >
                        {categoryList.map(category => {
                            return (
                                <MenuItem
                                    key={category.id}
                                    value={category.id}
                                >
                                    {category.name}
                                </MenuItem>);
                        })}
                    </TextField>

                    <TextField
                        label="Минимальная цена"
                        variant="filled"
                        type="number"
                        fullWidth
                        InputProps={{
                            endAdornment: <InputAdornment position="end">₽</InputAdornment>,
                        }}
                        onChange={e => {
                            if (e.target.value >= 0) {
                                setMinPrice(e.target.value);
                            }
                        }}
                        value={minPrice}
                    />

                    <TextField
                        label="Максимальная цена"
                        variant="filled"
                        type="number"
                        fullWidth
                        InputProps={{
                            endAdornment: <InputAdornment position="end">₽</InputAdornment>,
                        }}
                        onChange={e => {
                            if (e.target.value >= 0) {
                                setMaxPrice(e.target.value);
                            }
                        }}
                        value={maxPrice}
                    />

                    <TextField
                        fullWidth
                        select
                        variant="filled"
                        label="Тип объявления"
                        value={isActive}
                        onChange={e => {
                            const value = e.target.value;
                            setActive(value);
                        }}
                    >
                        {typeList.map(adType => {
                            return (
                                <MenuItem
                                    key={adType.id}
                                    value={adType.name}
                                >
                                    {adType.name}
                                </MenuItem>);
                        })}
                    </TextField>
                </DialogContent>
                <DialogActions>
                    <Button autoFocus onClick={handleCancel}>
                        Отменить
                    </Button>
                    <Button autoFocus onClick={handleApply}>
                        Применить
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};
