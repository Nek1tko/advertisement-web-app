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
        } else {
            setAlertOpen(true);
        }
    };
    const handleCancel = () => {
        setOpen(false);
        setAlertOpen(false);
    };

    const handleClearFilters = () => {
        setMetro('');
        setCategory('');
        setMinPrice('');
        setMaxPrice('');
        setActive('');
    }

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

    return (
        <div>
            <Button
                id="filtersButton"
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
                id="dialogModal"
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
                        id="metroTextField"
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
                                <MenuItem id={metro.id} key={metro.id} value={metro.id}>
                                    {metro.name}
                                </MenuItem>);
                        })}
                    </TextField>

                    <TextField
                        id="categoryTextField"
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
                        id="minPriceTextField"
                        label="Минимальная цена"
                        variant="filled"
                        type="number"
                        fullWidth
                        InputProps={{
                            endAdornment: <InputAdornment position="end">₽</InputAdornment>,
                        }}
                        onChange={e => {
                            if (e.target.value >= 0 && e.target.value <= 99999999) {
                                setMinPrice(e.target.value);
                            }
                        }}
                        value={minPrice}
                    />

                    <TextField
                        id="maxPriceTextField"
                        label="Максимальная цена"
                        variant="filled"
                        type="number"
                        fullWidth
                        InputProps={{
                            endAdornment: <InputAdornment position="end">₽</InputAdornment>,
                        }}
                        onChange={e => {
                            if (e.target.value >= 0 && e.target.value <= 99999999) {
                                setMaxPrice(e.target.value);
                            }
                        }}
                        value={maxPrice}
                    />

                    <TextField
                        id="adTypeTextField"
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
                    <Button
                        id="clearButton"
                        autoFocus
                        onClick={handleClearFilters}
                        style={{marginRight: "auto"}}
                    >
                        Сбросить фильтры
                    </Button>
                    <Button autoFocus onClick={handleCancel}>
                        Отменить
                    </Button>
                    <Button id="applyButton" autoFocus onClick={handleApply}>
                        Применить
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};
