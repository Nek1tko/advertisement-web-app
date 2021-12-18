import React, { useState, useEffect } from "react";
import { Box, MenuItem, InputAdornment } from "@material-ui/core";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import LocalizedDropzoneArea from '../components/LocalizedDropzoneArea';
import { Collapse } from '@material-ui/core';
import { Alert } from "@mui/material";
import AuthService from '../services/auth.service';
import { Redirect } from "react-router-dom";
import axios from 'axios';
import authHeader from "../services/auth-header";

const API_URL = "http://localhost:8080/";

const CreateAdImpl = props => {
    const { history } = props;

    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');

    const [metroList, setMetroList] = useState([]);
    const [metro, setMetro] = useState('');

    const [categoryList, setCategoryList] = useState([]);
    const [category, setCategory] = useState('');

    const [subcategoryList, setSubcategoryList] = useState([]);
    const [subcategory, setSubcategory] = useState('');

    const [alertOpen, setAlertOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const { _, userId, } = AuthService.getUser();

    useEffect(() => {
        axios
            .get(API_URL + "metro", { headers: authHeader() })
            .then(res => {
                setMetroList(res.data);
            })
    }, []);

    useEffect(() => {
        axios
            .get(API_URL + "category", { headers: authHeader() })
            .then(res => {
                setCategoryList(res.data);
            })
    }, []);

    useEffect(() => {
        axios
            .get(API_URL + "category/" + category, { headers: authHeader() })
            .then(res => {
                setSubcategoryList(res.data);
                setSubcategory('');
            })
    }, [category]);

    const handleSubmit = e => {
        e.preventDefault();
        if (name.length < 3 || name.length > 50) {
            setErrorMessage("Название объявления должно быть длиной от 3 до 50 символов");
            setAlertOpen(true);
            return;
        }

        axios
            .post(API_URL + "ad", {
                name: name,
                description: description,
                price: price,
                saler: {
                    id: userId
                },
                metro: {
                    id: metro
                },
                subCategory: {
                    id: subcategory
                },
                isActive: true
            }, { headers: authHeader() })
            .then(res => {
                history.push('/my-ads');
            })

        history.push('/my-ads');
    };

    return (
        <Box
            display='flex'
            flexDirection='column'
            justifyContent='space-between'
            sx={{ mt: 2, width: '50%', m: 'auto' }}
        >
            <Collapse in={alertOpen}>
                <Alert
                    severity="error"
                    sx={{ mb: 2 }}
                >
                    {errorMessage}
                </Alert>
            </Collapse>

            <TextField
                label="Название"
                variant="filled"
                fullWidth
                onChange={e => {
                    setName(e.target.value);
                    setAlertOpen(false);
                }}
                value={name}
            />

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
                        setPrice(e.target.value);
                    }
                }}
                value={price}
            />

            <TextField
                label="Описание"
                variant="filled"
                multiline
                maxRows={8}
                minRows={8}
                onChange={e => {
                    setDescription(e.target.value);
                    setAlertOpen(false);
                }}
                value={description}
                fullWidth
            />

            <TextField
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
                select
                variant="filled"
                label="Категория"
                value={category}
                onChange={e => {
                    const value = e.target.value;
                    setCategory(value);
                    setSubcategory('');
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
                disabled={!category}
                select
                variant="filled"
                label="Подкатегория"
                value={subcategory}
                onChange={e => {
                    const value = e.target.value;
                    setSubcategory(value);
                }}
            >
                {subcategoryList.map(subcategory => {
                    return (
                        <MenuItem
                            key={subcategory.id}
                            value={subcategory.id}
                        >
                            {subcategory.name}
                        </MenuItem>);
                })}
            </TextField>

            <LocalizedDropzoneArea
                filesLimit={3}
                maxFileSize={3145728} // 3 mb
                acceptedFiles={[".jpeg", ".jpg"]}
            />

            <Button
                type="submit"
                variant="contained"
                color="primary"
                onClick={handleSubmit}
                fullWidth
                style={{ marginTop: 20 }}
                disabled={!name || !description || !price || !metro || !category || !subcategory}
            >
                Создать
            </Button>
        </Box >
    )
}

const CreateAd = props => {
    if (!AuthService.getUser()) {
        return <Redirect to="/login" />
    }

    return <CreateAdImpl history={props.history} />
};

export default CreateAd;
