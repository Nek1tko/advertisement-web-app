import React, {useEffect} from "react";
import {FilterModal} from "../components/FilterModal";
import AdRecordsTableServer from "../components/AdRecordsTableServer";
import Box from "@mui/material/Box";
import {TextField} from "@mui/material";
import Button from "@material-ui/core/Button";
import SearchIcon from '@mui/icons-material/Search';
import axios from 'axios';
import authHeader from "../services/auth-header";
import AuthService from '../services/auth.service';
import {Redirect} from "react-router-dom";

const API_URL = "http://localhost:8080/ad/page";
const API_URL_COUNT = "http://localhost:8080/ad/page/count";

const HomeImpl = props => {
    const [searchValue, setSearchValue] = React.useState("");
    const [ads, setAds] = React.useState([]);
    const [rowCount, setRowCount] = React.useState(0);
    const [page, setPage] = React.useState(1);
    const {_, userId,} = AuthService.getUser();
    const [jsonFilter, setJsonFilter] = React.useState({});

    useEffect(() => {
        axios
            .post(API_URL_COUNT, {}, {headers: authHeader()})
            .then(res => {
                setRowCount(res.data);
            })
    }, []);

    useEffect(() => {
        axios
            .post(API_URL, {page: page}, {headers: authHeader()})
            .then(res => {
                setAds(res.data);
            })
    }, [page]);

    const handleClickSearch = () => {
        if (Object.keys(jsonFilter).length === 0) {
            axios
                .post(API_URL_COUNT, {title: searchValue}, {headers: authHeader()})
                .then(res => {
                    setRowCount(res.data);
                })

            axios
                .post(API_URL, {page: page, title: searchValue}, {headers: authHeader()})
                .then(res => {
                    setAds(res.data);
                })
        } else {
            axios
                .post(API_URL_COUNT, { ...jsonFilter, title: searchValue}, {headers: authHeader()})
                .then(res => {
                    setRowCount(res.data);
                })

            axios
                .post(API_URL, {...jsonFilter, title: searchValue}, {headers: authHeader()})
                .then(res => {
                    setAds(res.data);
                })
        }
    }

    return (
        <div>
            <Box sx={{display: "flex"}}>
                <Box sx={{width: 1 / 3, flex: 1}}>
                    <FilterModal page={page} setJsonFilter={setJsonFilter} setAds={setAds}/>
                </Box>
                <Box sx={{width: 1 / 3, flex: 1, marginTop: 3}}>
                    <TextField
                        id="searchTextField"
                        type="search"
                        size="small"
                        label="Поиск"
                        variant="outlined"
                        onChange={e => {
                            setSearchValue(e.target.value);
                        }}
                        value={searchValue}
                        fullWidth
                    />
                </Box>
                <Box sx={{width: 1 / 3, flex: 1, marginTop: 3}}>
                    <Button
                        id="searchButton"
                        variant="contained"
                        onClick={handleClickSearch}
                        endIcon={<SearchIcon/>}
                        color="primary"
                    >
                        Поиск
                    </Button>
                </Box>
            </Box>
            <AdRecordsTableServer
                userId={userId}
                ads={ads}
                rowCount={rowCount}
                API_URL={API_URL}
                setPage={(page) => setPage(page)}
                setAds={(ads) => setAds(ads)}
                history={props.history}
            />
        </div>
    );
}

const Home = props => {
    if (!AuthService.getUser()) {
        return <Redirect to="/login"/>
    }

    return <HomeImpl history={props.history}/>
};

export default Home;