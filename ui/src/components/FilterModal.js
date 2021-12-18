import React from "react";
import Button from "@mui/material/Button";
import {DataGrid, GridActionsCellItem} from "@mui/x-data-grid";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import DeleteIcon from "@mui/icons-material/Delete";
import AddIcon from "@mui/icons-material/Add";
import DialogTitle from "@mui/material/DialogTitle";
import Dialog from "@mui/material/Dialog";
import FilterListIcon from "@mui/icons-material/FilterList";

const selectFields = ["Метро", "Категория", "Мин. цена", "Макс. цена"];
let initialRows = [];

export const FilterModal = () => {
    let columns = [
        {
            field: "filterValue",
            headerName: "Значение фильтра",
            width: 220,
            editable: true,
        },
        {
            field: "filteringField",
            headerName: "Поле",
            width: 220,
            type: "singleSelect",
            valueOptions: selectFields,
            editable: true,
        },
        {
            field: "actions",
            headerName: "Удаление",
            type: "actions",
            width: 100,
            getActions: (params) => [
                <GridActionsCellItem
                    icon={<DeleteIcon/>}
                    label="Delete"
                    onClick={deleteFilter(params.id)}
                />,
            ],
        },
    ];

    const [bufferRows, setBufferRows] = React.useState(initialRows);

    const [rows, setRows] = React.useState(initialRows);
    const deleteFilter = React.useCallback(
        (id) => () => {
            setTimeout(() => {
                setRows((prevRows) => prevRows.filter((row) => row.id !== id));
            });
        },
        []
    );

    const addFilter = React.useCallback(() => {
        setTimeout(() => {
            const defaultRow = {
                id: rows.at(-1).id + 1,
                filterValue: "New filter",
                filteringField: selectFields[0],
            };
            setRows([...rows, defaultRow]);
        });
    }, [rows]);

    const handleEdit = React.useCallback(
        (e) => {
            setTimeout(() => {
                const array = rows.map((r) => {
                    if (r.id === e.id) {
                        return {...r, [e.field]: e.value};
                    } else {
                        return {...r};
                    }
                });
                setRows(array);
            });
        },
        [rows]
    );

    const [open, setOpen] = React.useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };
    const handleApply = () => {
        setBufferRows(rows);
    };
    const handleCancel = () => {
        setRows(bufferRows);
    };

    return (
        <div>
            <Button
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
                open={open}
                onClose={handleClose}
                onBackdropClick={handleCancel}
                minWidth="xs"
                fullWidth
            >
                <DialogTitle>
                    Фильтры
                </DialogTitle>
                <DialogContent dividers>
                    <DataGrid
                        onCellEditCommit={handleEdit}
                        rows={rows}
                        columns={columns}
                        pageSize={6}
                        autoHeight
                        autoWidth
                        hideFooterSelectedRowCount
                        disableColumnFilter
                        disableColumnMenu
                        disableDensitySelector
                        hideFooterPagination
                    />
                </DialogContent>
                <DialogActions>
                    <Button
                        autoFocus
                        onClick={addFilter}
                        startIcon={<AddIcon/>}
                        style={{marginRight: "auto"}}
                    >
                        Добавить фильтр
                    </Button>
                    <Button autoFocus onClick={handleCancel}>
                        Отменить
                    </Button>
                    <Button autoFocus onClick={handleApply}>
                        Применить
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};
