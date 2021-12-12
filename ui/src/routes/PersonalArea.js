import React, { useState } from "react";
import { Box, Button, Typography } from "@material-ui/core";

const PersonalArea = props => {
    const [name, setName] = useState('Test name');
    const [surname, setSurname] = useState('Test surname');
    const [phone, setPhone] = useState('+7 (888) 888-88-88');

    return (
        <Box
            display='flex'
            flexDirection='column'
            justifyContent='space-between'
            sx={{ mt: 10, width: '25%', m: 'auto' }}
        >
            <Typography variant="h4" align="center">
                {name}
            </Typography>

            <Typography variant="h4" align="center" style={{ marginTop: 30 }}>
                {surname}
            </Typography>

            <Typography variant="h5" align="center" style={{ color: "#666666", marginTop: 30 }}>
                {phone}
            </Typography>

            <Button
                variant="contained"
                color="primary"
                fullWidth
                mt={20}
                style={{ textTransform: 'none', marginTop: 30 }}
            >
                Редактировать
            </Button>
        </Box>
    );
};

export default PersonalArea;
