/**
 * @group unit
 */

import { createBrowserHistory } from "history";
import AuthService from "../../services/auth.service";
import authHeader from "../../services/auth-header";
import axios from "axios";
import React from "react";
import { mount } from "enzyme";
import { Router } from "react-router-dom";
import UserAds from "../UserAds";
import { act } from "react-dom/test-utils";

jest.mock('axios');
jest.mock("../../services/auth.service");
jest.mock("../../services/auth-header");

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

const mAd = {
    id: 1,
    description: "mock description",
    price: "123",
    name: "mock name",
    metro: mMetro,
    subcategory: mSubcategory,
    is_active: true,
    isFavourite: true,
};

describe('UserAdsTests', () => {
    let wrapper, props;
    const setState = jest.fn();

    beforeEach(() => {
        props = {
            history: {
                ...createBrowserHistory(),
                push: jest.fn()
            }
        };
        AuthService.getUser.mockReturnValue({ userId: "mockUser" });
        authHeader.mockReturnValue({ header: "mockHeader" });
        axios.get.mockResolvedValue({
            data: [
                mAd,
                mAd
            ]
        });
        const useStateSpy = jest.spyOn(React, 'useState');
        useStateSpy.mockImplementation((state) => [state, setState]);
        wrapper = mount(
            <Router history={props.history}>
                <UserAds {...props} />
            </Router>);
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('UserAdsRenderTest', () => {
        act(() => {
            mount(<Router history={props.history}>
                <UserAds {...props} />
            </Router>);
        });
    });

    test('UserAdsSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('UserAdsNotAuthorizedTest', () => {
        AuthService.getUser.mockReturnValue(null);
        wrapper = mount(
            <Router history={props.history}>
                <UserAds {...props} />
            </Router>);
    });
});
