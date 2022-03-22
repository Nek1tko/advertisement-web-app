/**
 * @group unit
 */

import axios from "axios";
import { mount } from "enzyme";
import CreateAd from "../CreateAd";
import { act } from "react-dom/test-utils";
import { Router } from "react-router-dom";
import { createBrowserHistory } from 'history';
import React from "react";
import AuthService from "../../services/auth.service";
import authHeader from "../../services/auth-header";

jest.mock('form-data');
jest.mock('axios');
jest.mock("../../services/auth.service");
jest.mock("../../services/auth-header");


const event = {
    preventDefault: jest.fn()
};

const eventName = {
    target: {
        value: "mockName"
    }
};

const eventName2 = {
    target: {
        value: "m"
    }
};

const eventDescription = {
    target: {
        value: "mockDescription"
    }
};

const eventPrice = {
    target: {
        value: 156
    }
};

const eventCategory = {
    target: {
        value: "mockCategory"
    }
};

const eventSubCategory = {
    target: {
        value: "mockSubCategory"
    }
};

const eventMetro = {
    target: {
        value: "mockMetro"
    }
};

const imgs = [
    {
        name: JSON.stringify({ file: 'mockImg' })
    }
];


describe('CreateAdTests', () => {
    let wrapper, props;
    const setState = jest.fn();

    beforeEach(() => {
        props = {
            history: {
                ...createBrowserHistory(),
                push: jest.fn()
            }
        };

        FormData = jest.fn();
        AuthService.getUser.mockReturnValue({ user: "mockUser" });
        authHeader.mockReturnValue({ header: "mockHeader" });
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
        axios.post.mockResolvedValue({
            data: {
                id: "ID"
            }
        });
        const useStateSpy = jest.spyOn(React, 'useState');
        useStateSpy.mockImplementation((state) => [state, setState]);
        wrapper = mount(
            <Router history={props.history}>
                <CreateAd {...props} />
            </Router>);
    })

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('CreateAdRenderTest', () => {
        act(() => {
            mount(<Router history={props.history}>
                <CreateAd {...props} />
            </Router>);
        });
    });

    test('CreateAdSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('CreateAdSubmitAdPositiveTest', async () => {
        //setup all necessary fields
        await act(async () => {
            await wrapper.find('#adNameTextField').first().props().onChange(eventName);
            await wrapper.find('#categoryTextField').first().props().onChange(eventCategory);
            await wrapper.find('#subcategoryTextField').first().props().onChange(eventSubCategory);
            await wrapper.find('#descriptionTextField').first().props().onChange(eventDescription);
            await wrapper.find('#priceTextField').first().props().onChange(eventPrice);
            await wrapper.find('#metroTextField').first().props().onChange(eventMetro);
            await wrapper.find('#localizedDropzoneArea').props().onChange(imgs);

            await wrapper.update();
        });

        await act(async () => {
            await wrapper.find('#submitButton').first().props().onClick(event);
        });

        expect(props.history.push).toHaveBeenCalledTimes(1);
        expect(props.history.push).toHaveBeenCalledWith("/my-ads");
    });

    test('CreateAdSubmitAdNegativeTest', async () => {
        //setup all necessary fields
        await act(async () => {
            await wrapper.find('#adNameTextField').first().props().onChange(eventName2);
            await wrapper.find('#categoryTextField').first().props().onChange(eventCategory);
            await wrapper.find('#subcategoryTextField').first().props().onChange(eventSubCategory);
            await wrapper.find('#descriptionTextField').first().props().onChange(eventDescription);
            await wrapper.find('#priceTextField').first().props().onChange(eventPrice);
            await wrapper.find('#metroTextField').first().props().onChange(eventMetro);
            await wrapper.find('#localizedDropzoneArea').props().onChange(imgs);

            await wrapper.update();
        });

        await act(async () => {
            await wrapper.find('#submitButton').first().props().onClick(event);
        });

        expect(props.history.push).toHaveBeenCalledTimes(0);
    });

    test('CreateAdNotAuthorizedTest', () => {
        AuthService.getUser.mockReturnValue(null);
        wrapper = mount(
            <Router history={props.history}>
                <CreateAd {...props} />
            </Router>);
    });
});
