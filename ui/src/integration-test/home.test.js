/**
 * При переключении страницы на route Home в component AdRecordsTableServer корректно будут
 * отображены полученные из mock-запроса записи
 * 
 * @group integration
 */

import axios from "axios";
import { createBrowserHistory } from "history";
import React from "react";
import { mount, shallow } from "enzyme";
import { Router } from "react-router-dom";
import { BrowserRouter } from 'react-router-dom';
import Login from "../routes/Login";
import Home from "../routes/Home";
import App from "../App";
import { act } from "react-dom/test-utils";
import store from '../store';
import { waitFor } from '@testing-library/react';
import AuthService from "../services/auth.service";

jest.mock("axios");
jest.mock("../services/auth.service");

const mUserId = 1;
const mPhoneNumber = "mock phone";
const mToken = "mock token";

const mUser = {
    phoneNumber: mPhoneNumber,
    userId: mUserId,
    token: mToken
};

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

const mGetAdsResponse = { data: mAds };

const whenStable = async () =>
    await act(async () => {
        await new Promise((resolve) => setTimeout(resolve, 0));
    });

describe('HomeTests', () => {
    let wrapper;
    const history = createBrowserHistory();

    beforeEach(() => {
        AuthService.getUser.mockReturnValue({ user: mUser });
        axios.post.mockImplementation((url) => {
            if (url === "http://localhost:8080/ad/page/count") {
                return Promise.resolve(2);
            } else {
                return Promise.resolve(mGetAdsResponse);
            }
        });
        axios.get.mockResolvedValue({
            data: [
                {
                    id: 1,
                    name: "mock name"
                },
                {
                    id: 2,
                    name: "mock name"
                },
            ]
        });
        history.push("/");
        const props = {
            history: history
        }
        wrapper = mount(
            <Router history={props.history}>
                <Home {...props} />
            </Router>
        );
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('HomeTableFillingTest', async () => {
        expect(history.location.pathname).toBe("/");

        const nextButton = wrapper.find('[title="Go to next page"]').first();
        nextButton.props().onClick();

        await whenStable();
        wrapper.update();

        expect(wrapper.text().includes(mAds[0].name)).toBe(true);
        expect(wrapper.text().includes(mAds[0].metro.name)).toBe(true);
        expect(wrapper.text().includes(mAds[1].name)).toBe(true);
        expect(wrapper.text().includes(mAds[1].metro.name)).toBe(true);
    });
});
