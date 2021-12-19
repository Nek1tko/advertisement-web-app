import React from "react";
import {FilterModal} from "../components/FilterModal";
import AdRecordsTable from "../components/AdRecordsTable";
import Box from "@mui/material/Box";
import {TextField} from "@mui/material";
import Button from "@material-ui/core/Button";
import SearchIcon from '@mui/icons-material/Search';

const Home = props => {
    const [searchValue, setSearchValue] = React.useState("");

    const handleClickSearch = () => {
        console.log("search");
    }

    return (
        <div>
            <Box sx={{display: "flex"}}>
                <Box sx={{width: 1 / 3, flex: 1}}>
                    <FilterModal/>
                </Box>
                <Box sx={{width: 1 / 3, flex: 1, marginTop: 3}}>
                    <TextField
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
                        variant="outlined"
                        onClick={handleClickSearch}
                        endIcon={<SearchIcon />}
                        style={{
                            backgroundColor: "#a1b8f1",
                        }}
                    >
                        Поиск
                    </Button>
                </Box>
            </Box>
            <AdRecordsTable/>
        </div>
    );
};

export default Home;