import axios from "axios";
import React from "react";
import {mount} from "enzyme";
import CustomerAd from "../CustomerAd";
import {act} from "react-dom/test-utils";
import SellerAd from "../SellerAd";

jest.mock('axios');

const mockedResponse = {
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

const mAd = {
    id: 1,
    description: "mock description",
    price: "123",
    name: "mock name",
    saler: {
        name: "mock name",
    },
    metro: mMetro,
    subcategory: mSubcategory,
    is_active: true,
    isFavourite: true,
};

const props = {
    location: {
        ad: mAd
    }
};

describe('CustomerAdTests', () => {
    let wrapper;
    const setState = jest.fn();

    beforeEach(() => {
        axios.get.mockResolvedValue(mockedResponse);
        axios.put.mockResolvedValue(mockedResponse);
        const useStateSpy = jest.spyOn(React, 'useState');
        useStateSpy.mockImplementation((state) => [state, setState]);
        wrapper = mount(<CustomerAd{...props}/>);
    })

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('CustomerAdRenderTest', () => {
        act(() => {
            mount(<SellerAd {...props}/>);
        });
    });

    test('CustomerAdSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('CustomerAdFavButtonTest', async () => {
        await act(async () => {
            await wrapper.find('#favButton').first().props().onClick();
        });
        expect(setState).toHaveBeenCalledTimes(0);
    });
});