import React from "react";
import { Box } from "@material-ui/core";
import { DataGrid } from '@mui/x-data-grid';

const columns = [
    {
        field: 'image',
        headerName: 'Превью',
        minWidth: 100,
        renderCell: (params) => <img
            alt="Ad preview"
            src={params.value}
            style={{ height: '100%', width: '100%', objectFit: 'contain' }}
        />,
        sortable: false
    },
    { field: 'name', headerName: 'Название', minWidth: 400, sortable: false, flex: 1 },
    { field: 'location', headerName: 'Метро', minWidth: 200, sortable: false },
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
    const { history } = props;
    const userId = props.userId;
    const ads = props.ads;

    const rows = ads ? ads.map(ad => {
        return {
            id: ad.id,
            image: null,
            name: ad.name,
            description: ad.description,
            location: ad.metro.name,
            price: ad.price,
            is_active: ad.isActive,
            saler: ad.saler.id,
            metro: ad.metro,
            subcategory: ad.subCategory
        };
    }) : [];

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
                columns={columns}
                rows={rows}
                getRowClassName={params => {
                    return params.row.is_active ? 'active' : 'disabled';
                }}
                pagination
                {...rows}
                disableColumnMenu
                disableSelectionOnClick={true}
                disable
                onRowClick={(params) => {
                    if (userId === params.row.saler) {
                        const ad = params.row;
                        history.push({
                            pathname: '/seller-ad',
                            ad
                        });
                    }
                }}
            />
        </ Box>
    );
}
