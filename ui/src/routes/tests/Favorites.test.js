import { mount, shallow } from 'enzyme';
import Favorites from '../Favorites';
import { BrowserRouter, Router } from 'react-router-dom';
import AuthService from '../../services/auth.service';
import { createBrowserHistory } from 'history'
import { act } from 'react-dom/test-utils';
import authHeader from "../../services/auth-header";
import axios from "axios";
import React from "react";

jest.mock("../../services/auth-header");
jest.mock("../../services/auth.service");
jest.mock('axios');

const mCategory = {
    id: 1,
    name: "mock category"
};

const mSubcategory = {
    id: 1,
    name: "mock subcategory",
    category: mCategory
};

const mMetro = {
    id: 1,
    name: "Улица Дыбенко",
};

const mUserId = 1;

const mAds = [
    {
        id: 1,
        description: "mock description",
        price: "123",
        name: "mock name",
        metro: mMetro,
        subCategory: mSubcategory,
        isActive: true,
        isFavourite: true,
        saler: { id: mUserId }
    },
    {
        id: 2,
        description: "mock description 2",
        price: "1234",
        name: "mock name 2",
        metro: mMetro,
        subCategory: mSubcategory,
        isActive: true,
        isFavourite: true,
        saler: { id: mUserId + 1 }
    },
];

describe('FavoritesTests', () => {
    let mHistory, wrapper

    beforeEach(() => {
        mHistory = {
            ...createBrowserHistory(),
            push: jest.fn()
        };
        AuthService.getUser.mockReturnValue({ user: "mockUser" });
        authHeader.mockReturnValue({ header: "mockHeader" });
        axios.get.mockResolvedValue({ data: mAds });
        wrapper = mount(
            <Router history={mHistory}>
                <Favorites />
            </Router>
        );
    })

    afterEach(() => {
        jest.clearAllMocks();
    })

    test('FavoritesRenderTest', () => {
        mount(
            <BrowserRouter>
                <Favorites />
            </BrowserRouter>
        );
    });

    test('FavoritesSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('FavoritesNotAuthorizedTest', () => {
        AuthService.getUser.mockReturnValue(null);
        wrapper = mount(
            <Router history={mHistory}>
                <Favorites />
            </Router>);
    });
});
