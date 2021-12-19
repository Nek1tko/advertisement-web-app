import React, { useState, useEffect } from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import Button from "@material-ui/core/Button";
import { withRouter } from "react-router-dom";
import { Avatar, Box, Tooltip } from "@material-ui/core";
import AuthService from '../services/auth.service';
import Collapse from '@mui/material/Collapse';
import { useSelector } from 'react-redux';
import store from '../store';

const Header = props => {
    const { history } = props;

    const [anchorElUser, setAnchorElUser] = useState(null);
    const [isOpen, setIsOpen] = useState(false);

    useEffect(() => {
        store.subscribe(() => {
            setIsOpen(AuthService.getUser() !== null);
        })
    }, store)

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

    const handleSignOut = () => {
        setAnchorElUser(null);
        AuthService.signOut();
        history.push('/login');
    }

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

    const settingsItems = [
        {
            settingsTitle: "Профиль",
            pageURL: "/personal-area",
            handler: handleCloseUserMenu
        },
        {
            settingsTitle: "Избранное",
            pageURL: "/favorites",
            handler: handleCloseUserMenu
        },
        {
            settingsTitle: "Выйти",
            pageURL: "/login",
            handler: handleSignOut
        }
    ];

    return (
        <AppBar style={{ background: 'primary' }} position="static">
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
                    <Collapse in={isOpen}>
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
                                const { settingsTitle, pageURL, handler } = settingsItem;
                                return (
                                    <MenuItem key={settingsTitle} onClick={() => handler(pageURL)}>
                                        <Typography>{settingsTitle}</Typography>
                                    </MenuItem>
                                );
                            })}
                        </Menu>
                    </Collapse>

                </Box>
            </Toolbar>
        </AppBar >
    );
};

export default withRouter(Header);
