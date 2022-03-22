/**
 * @group unit
 */

import AuthService from "../../services/auth.service";
import axios from "axios";
import { createBrowserHistory } from "history";
import React from "react";
import { mount } from "enzyme";
import { Router } from "react-router-dom";
import Home from "../Home";
import { act } from "react-dom/test-utils";
import authHeader from "../../services/auth-header";

jest.mock("axios");
jest.mock("../../services/auth.service");
jest.mock("../../services/auth-header");

const eventSearch = {
    target: {
        value: "mockName"
    }
};

describe('HomeTests', () => {
    let wrapper, props, useStateSpy;
    const setState = jest.fn();

    beforeEach(() => {
        props = {
            history: {
                ...createBrowserHistory(),
                push: jest.fn()
            }
        };
        AuthService.getUser.mockReturnValue({ user: "mockUser" });
        axios.post.mockResolvedValue({
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
        authHeader.mockReturnValue({ header: "mockHeader" });
        useStateSpy = jest.spyOn(React, 'useState');
        useStateSpy.mockImplementation((state) => [state, setState]);
        act(async () => wrapper = await mount(
            <Router history={props.history}>
                <Home {...props} />
            </Router>));
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('HomeRenderTest', async () => {
        await act(async () => {
            await mount(<Router history={props.history}>
                <Home {...props} />
            </Router>);
        });
    });

    test('HomeSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('HomeSearchButtonNegativeTest', async () => {
        await act(async () => {
            await wrapper.find('#searchButton').first().props().onClick();
        });
        expect(setState).toHaveBeenCalledTimes(11);
    });

    test('HomeSearchButtonNegativeTest', async () => {
        await act(async () => {
            await wrapper.find('#searchTextField').first().props().onChange(eventSearch);
        });
        expect(setState).toHaveBeenCalledTimes(10);
    });

    test('HomeNotAuthorizedTest', () => {
        AuthService.getUser.mockReturnValue(null);
        wrapper = mount(
            <Router history={props.history}>
                <Home {...props} />
            </Router>);
    });
});
