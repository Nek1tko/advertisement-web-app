/**
 * Через route CreateAd создание объявление и редирект в route UserAds
 *
 * @group integration
 */

import axios from "axios";
import {mount} from "enzyme";
import CreateAd from "../routes/CreateAd";
import {act} from "react-dom/test-utils";
import {Router} from "react-router-dom";
import {createBrowserHistory} from 'history';
import React from "react";
import AuthService from "../services/auth.service";

jest.mock('form-data');
jest.mock('axios');
jest.mock("../services/auth.service");


const event = {
    preventDefault: jest.fn()
};

const eventName = {
    target: {
        value: "mockName"
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
        name: JSON.stringify({file: 'mockImg'})
    }
];

const whenStable = async () =>
    await act(async () => {
        await new Promise((resolve) => setTimeout(resolve, 0));
    });

describe('CreateAdTests', () => {
    let wrapper;
    const history = createBrowserHistory();

    beforeEach(() => {
        history.push("/create-ad");
        const props = {
            history: history
        }
        FormData = jest.fn();
        AuthService.getUser.mockReturnValue({user: "mockUser"});
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

        act(() => wrapper = mount(
            <Router history={props.history}>
                <CreateAd {...props} />
            </Router>));
    })

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('CreateAdSubmitAdIntegrationTest', async () => {
        expect(history.location.pathname).toBe("/create-ad");

        //setup all necessary fields
        wrapper.find('#adNameTextField').first().props().onChange(eventName);
        wrapper.find('#categoryTextField').first().props().onChange(eventCategory);
        wrapper.find('#subcategoryTextField').first().props().onChange(eventSubCategory);
        wrapper.find('#descriptionTextField').first().props().onChange(eventDescription);
        wrapper.find('#priceTextField').first().props().onChange(eventPrice);
        wrapper.find('#metroTextField').first().props().onChange(eventMetro);
        wrapper.find('#localizedDropzoneArea').props().onChange(imgs);

        await whenStable();
        wrapper.update();

        expect(wrapper.find('#adNameTextField').first().props().value).toBe(eventName.target.value);
        expect(wrapper.find('#categoryTextField').first().props().value).toBe(eventCategory.target.value);
        expect(wrapper.find('#categoryTextField').first().props().value).toBe(eventCategory.target.value);
        expect(wrapper.find('#descriptionTextField').first().props().value).toBe(eventDescription.target.value);
        expect(wrapper.find('#priceTextField').first().props().value).toBe(eventPrice.target.value);
        expect(wrapper.find('#metroTextField').first().props().value).toBe(eventMetro.target.value);

        wrapper.find('#submitButton').first().props().onClick(event);

        await whenStable();
        wrapper.update();

        expect(history.location.pathname).toBe("/my-ads");
    });
});
