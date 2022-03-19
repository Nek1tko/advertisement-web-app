import React, { useState, useEffect } from 'react';
import AdRecordsTable from "../components/AdRecordsTable";
import AuthService from '../services/auth.service';
import { Redirect } from "react-router-dom";
import axios from 'axios';
import authHeader from "../services/auth-header";

const API_URL = "http://localhost:8080/ad/favourites/";

const FavoritesImpl = props => {
    const [ads, setAds] = React.useState([]);
    const { _, userId, } = AuthService.getUser();

    useEffect(() => {
        axios
            .get(API_URL + userId, { headers: authHeader() })
            .then(res => {
                setAds(res.data);
            })
    }, [userId, setAds]);

    return <AdRecordsTable
        ads={ads}
        userId={userId}
        history={props.history}
    />;
}

const Favorites = props => {
    if (!AuthService.getUser()) {
        return <Redirect to="/login" />
    }

    return <FavoritesImpl history={props.history} />;
}

export default Favorites;
