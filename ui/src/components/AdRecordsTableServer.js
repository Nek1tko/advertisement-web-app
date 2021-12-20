import React from "react";
import {Box} from "@material-ui/core";
import {DataGrid} from '@mui/x-data-grid';
import axios from "axios";
import authHeader from "../services/auth-header";

const API_URL = "http://localhost:8080/";

const columns = [
    {
        field: 'image',
        headerName: 'Превью',
        minWidth: 100,
        renderCell: (params) => <img
            alt="Ad preview"
            src={API_URL + "img/" + params.value}
            style={{ height: '100%', width: '100%', objectFit: 'contain' }}
        />,
        sortable: false
    },
    {field: 'name', headerName: 'Название', minWidth: 400, sortable: false, flex: 1},
    {field: 'location', headerName: 'Метро', minWidth: 200, sortable: false},
    {
        field: 'price',
        headerName: 'Цена',
        width: 100,
        renderCell: (params) => (
            <Box>
                {params.value} ₽
            </Box>
        ),
        sortable: false
    },
];

export default function AdRecordsTable(props) {
    const {history} = props;
    const userId = props.userId;
    const ads = props.ads;
    const rowCount = props.rowCount;
    const API_URL = props.API_URL;

    const rows = ads ? ads.map(ad => {
        return {
            id: ad.id,
            image: ad.previewImagePath,
            name: ad.name,
            description: ad.description,
            location: ad.metro.name,
            price: ad.price,
            is_active: ad.isActive,
            saler: ad.saler,
            metro: ad.metro,
            subcategory: ad.subCategory,
            isFavourite: ad.isFavourite
        };
    }) : [];
    console.log(rows);

    const onPageChange = (page) => {
        page = page + 1;
        props.setPage(page);
        axios
            .post(API_URL, {page: page}, {headers: authHeader()})
            .then(res => {
                console.log(res);
                props.setAds(res.data);
            })
    };

    return (
        <Box
            sx={{
                mt: 2,
                '& .disabled': {
                    backgroundColor: '#eeeeee',
                },
                '& .active': {
                    backgroundColor: '#ffffff',
                }
            }}
        >
            <DataGrid
                rowHeight={100}
                autoHeight
                pageSize={10}
                rows={rows}
                columns={columns}
                rowCount={rowCount}
                getRowClassName={params => {
                    return params.row.is_active ? 'active' : 'disabled';
                }}
                pagination
                {...rows}
                paginationMode="server"
                disableColumnMenu
                disableSelectionOnClick={true}
                onPageChange={(page) => onPageChange(page)}
                disable
                onRowClick={(params) => {
                    const ad = params.row;
                    if (userId === ad.saler.id) {
                        history.push({
                            pathname: '/seller-ad',
                            ad
                        });
                    } else {
                        history.push({
                            pathname: '/customer-ad',
                            ad
                        });
                    }
                }}
            />
        </ Box>
    );
}