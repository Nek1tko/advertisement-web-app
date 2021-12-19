import React from "react";
import { FilterModal } from "../components/FilterModal";
import AdRecordsTable from "../components/AdRecordsTable";
import Box from "@mui/material/Box";
import { TextField } from "@mui/material";
import Button from "@material-ui/core/Button";
import SearchIcon from '@mui/icons-material/Search';
import axios from 'axios';
import authHeader from "../services/auth-header";
import AuthService from '../services/auth.service';
import { Redirect } from "react-router-dom";

const HomeImpl = props => {
    const [searchValue, setSearchValue] = React.useState("");

    const handleClickSearch = () => {
        console.log("search");
    }

    const [page, setPage] = React.useState("no");

    return (
        <div>
            <Box sx={{ display: "flex" }}>
                <Box sx={{ width: 1 / 3, flex: 1 }}>
                    <FilterModal page={page} />
                </Box>
                <Box sx={{ width: 1 / 3, flex: 1, marginTop: 3 }}>
                    <TextField
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
                <Box sx={{ width: 1 / 3, flex: 1, marginTop: 3 }}>
                    <Button
                        variant="contained"
                        onClick={handleClickSearch}
                        endIcon={<SearchIcon />}
                        color="primary"
                    >
                        Поиск
                    </Button>
                </Box>
            </Box>
            <AdRecordsTable setPage={setPage} />
        </div>
    );
}

const Home = props => {
    if (!AuthService.getUser()) {
        return <Redirect to="/login" />
    }

    return <HomeImpl />
};

export default Home;