import React from "react";
import { Box } from "@material-ui/core";
import { DataGrid } from '@mui/x-data-grid';
import eren from '../img/eren.jpg'

var id = 0

function createData(image, name, location, price) {
    id += 1
    return { id, image, name, location, price };
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
            src={params.value}
            style={{ height: '100%', width: '100%', objectFit: 'contain' }}
        />
    },
    { field: 'name', headerName: 'Название', minWidth: 400, sortable: false },
    { field: 'location', headerName: 'Метро', minWidth: 200, sortable: false },
    { field: 'price', headerName: 'Цена', width: 100, sortable: false },
];

export default function AdRecordsTable() {
    const [page, setPage] = React.useState(0);

    return (
        <Box>
            <DataGrid
                rowHeight={100}
                autoHeight
                page={page}
                onPageChange={(newPage) => setPage(newPage)}
                pageSize={10}
                columns={columns}
                rows={rows}
                pagination
                {...rows}
                disableColumnMenu
            />
        </ Box>
    );
}
