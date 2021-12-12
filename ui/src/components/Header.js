import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import Button from "@material-ui/core/Button";
import { withRouter } from "react-router-dom";
import { Avatar, Box, Tooltip } from "@material-ui/core";

const Header = props => {
    const { history } = props;

    const [anchorElUser, setAnchorElUser] = React.useState(null);

    const handleButtonClick = pageURL => {
        history.push(pageURL);
    };

    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseUserMenu = pageURL => {
        setAnchorElUser(null);
        history.push(pageURL);
    };

    const homeItem = {
        menuTitle: "Главная",
        pageURL: "/"
    }
    const menuItems = [
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
    const settingsItems = [
        {
            settingsTitle: "Профиль",
            pageURL: "/personal-area"
        },
        {
            settingsTitle: "Выйти",
            pageURL: "/login"
        }
    ];

    return (
        <AppBar style={{ background: '#669999' }} position="static">
            <Toolbar>
                <Button
                    style={{ color: '#FFFFFF' }}
                    onClick={() => handleButtonClick(homeItem.pageURL)} >
                    <Typography variant="h4" sx={{ flexGrow: 1 }}>
                        {homeItem.menuTitle}
                    </Typography>
                </Button>

                <Box sx={{ flexGrow: 1, display: 'flex', ml: 2 }}>
                    {menuItems.map(menuItem => {
                        const { menuTitle, pageURL } = menuItem;
                        return (
                            <Box sx={{ px: 2 }}>
                                <Button
                                    style={{ color: '#FFFFFF', textTransform: 'none', fontSize: '18px' }}
                                    onClick={() => handleButtonClick(pageURL)}
                                >
                                    {menuTitle}
                                </Button>
                            </Box>
                        );
                    })}
                </Box>

                <Box sx={{ flexGrow: 0 }}>
                    <Tooltip title="Открыть настройки">
                        <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                            <Avatar />
                        </IconButton>
                    </Tooltip>
                    <Menu
                        sx={{ mt: '45px' }}
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
                        {settingsItems.map((settingsItem) => {
                            const { settingsTitle, pageURL } = settingsItem;
                            return (
                                <MenuItem key={settingsTitle} onClick={() => handleCloseUserMenu(pageURL)}>
                                    <Typography>{settingsTitle}</Typography>
                                </MenuItem>
                            );
                        })}
                    </Menu>
                </Box>
            </Toolbar>
        </AppBar >
    );
};

export default withRouter(Header);
