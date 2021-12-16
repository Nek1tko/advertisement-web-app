import React from "react";
import {Box} from "@material-ui/core";
import {DataGrid} from '@mui/x-data-grid';
import eren from '../img/eren.jpg'

let id = 0;

function createData(image, name, location, price) {
    id += 1
    return {id, image, name, location, price};
}

const rows = [
    createData(eren, 'Танк', 'Улица Дыбенко', 10000000),
    createData(eren, 'Стирательная резинка', 'Проспект Большевиков', 10),
    createData(eren, 'Кот', 'Сенная', 0),
];

const columns = [
    {
        field: 'image',
        headerName: 'Превью',
        minWidth: 100,
        renderCell: (params) => <img
            alt="Ad preview"
            src={params.value}
            style={{height: '100%', width: '100%', objectFit: 'contain'}}
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

export default function AdRecordsTable() {
    return (
        <Box sx={{mt: 2}}>
            <DataGrid
                rowHeight={100}
                autoHeight
                pageSize={10}
                columns={columns}
                rows={rows}
                pagination
                {...rows}
                disableColumnMenu
                disableSelectionOnClick
                disable
            />
        </ Box>
    );
}
