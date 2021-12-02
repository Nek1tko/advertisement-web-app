import React from "react";
import {makeStyles} from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import Button from "@material-ui/core/Button";
import {withRouter} from "react-router-dom";
import {Avatar, Box, Tooltip} from "@material-ui/core";

const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1
    },
    menuButton: {
        marginRight: theme.spacing(2)
    },
    title: {
        [theme.breakpoints.down("xs")]: {
            flexGrow: 1
        }
    },
    headerOptions: {
        display: "flex",
        flex: 1,
        justifyContent: "space-evenly"
    }
}));

const Header = props => {
    const {history} = props;
    const classes = useStyles();

    const [anchorElUser, setAnchorElUser] = React.useState(null);

    const handleButtonClick = pageURL => {
        history.push(pageURL);
    };

    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const menuItems = [
        {
            menuTitle: "Главная",
            pageURL: "/"
        },
        {
            menuTitle: "Мои объявления",
            pageURL: "/my-ads"
        },
        {
            menuTitle: "Создать объявление",
            pageURL: "/create-ad"
        }
    ];

    const settings = ['Профиль', 'Выйти'];

    return (
        <div className={classes.root}>
            <AppBar style={{background: '#669999'}} position="static">
                <Toolbar>
                    <Typography variant="h5" className={classes.title}>
                        АВИТО 2
                    </Typography>
                    <div className={classes.headerOptions}>
                        {menuItems.map(menuItem => {
                            const {menuTitle, pageURL} = menuItem;
                            return (
                                <Button style={{color: '#FFFFFF', fontSize: '18px'}}
                                        onClick={() => handleButtonClick(pageURL)}>
                                    {menuTitle}
                                </Button>
                            );
                        })}
                    </div>
                    <Box sx={{flexGrow: 0}}>
                        <Tooltip title="Открыть настройки">
                            <IconButton onClick={handleOpenUserMenu} sx={{p: 0}}>
                                <Avatar/>
                            </IconButton>
                        </Tooltip>
                        <Menu
                            sx={{mt: '45px'}}
                            id="menu-appbar3"
                            anchorEl={anchorElUser}
                            anchorOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                            }}
                            keepMounted
                            transformOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                            }}
                            open={Boolean(anchorElUser)}
                            onClose={handleCloseUserMenu}
                        >
                            {settings.map((setting) => (
                                <MenuItem key={setting} onClick={handleCloseUserMenu}>
                                    <Typography>{setting}</Typography>
                                </MenuItem>
                            ))}
                        </Menu>
                    </Box>
                </Toolbar>
            </AppBar>
        </div>
    );
};

export default withRouter(Header);
