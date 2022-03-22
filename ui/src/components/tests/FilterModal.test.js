/**
 * @group unit
 */

import '../FilterModal';
import { FilterModal } from '../FilterModal';
import { mount, shallow } from 'enzyme';
import axios from 'axios';
import React from "react";
import { act } from "react-dom/test-utils";

jest.mock('axios');

const props = {
    page: 1,
    setJsonFilter: jest.fn(),
    ads: [],
};

const eventMaxPrice = {
    target: {
        value: 55
    }
};

const eventMaxPrice2 = {
    target: {
        value: 77
    }
};

const eventMinPrice = {
    target: {
        value: 66
    }
};

const eventCategory = {
    target: {
        value: "Category"
    }
};

const eventAdType = {
    target: {
        value: "Активно"
    }
};

const eventAdType2 = {
    target: {
        value: "Неактивно"
    }
};

const eventMetro = {
    target: {
        value: "Metro"
    }
};

const axiosResponseMetro = { data: [{ id: 'metroId', name: 'Lesnaya' }] };

describe('FilterModalTests', () => {
    let wrapper;
    const setState = jest.fn();

    beforeEach(() => {
        wrapper = shallow(<FilterModal{...props} />);
        axios.get.mockResolvedValue(axiosResponseMetro);
        const useStateSpy = jest.spyOn(React, 'useState');
        useStateSpy.mockImplementation((state) => [state, setState]);
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('FilterModalRenderTest', () => {
        mount(<FilterModal{...props} />);
    });

    test('FilterModalSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('FilterModalClickOpenTest', () => {
        act(() => wrapper.find('#filtersButton').props().onClick());
        expect(setState).toHaveBeenCalledWith(true);
    });

    test('FilterModalClickCloseTest', () => {
        act(() => wrapper.find('#dialogModal').props().onClose());
        expect(setState).toHaveBeenCalledWith(false);
    });

    test('FilterModalClickApplyPositiveTest', () => {
        act(() => wrapper.find('#applyButton').props().onClick());
        expect(setState).toHaveBeenCalledWith(false);
    });

    test('FilterModalClickApplyNegativeTest', () => {
        act(() => wrapper.find('#maxPriceTextField').props().onChange(eventMaxPrice));
        act(() => wrapper.find('#minPriceTextField').props().onChange(eventMinPrice));
        act(() => wrapper.find('#applyButton').props().onClick());
        expect(setState).toHaveBeenCalledTimes(0);
    });

    test('FilterModalClickCancelTest', () => {
        act(() => wrapper.find('#dialogModal').props().onBackdropClick());
        expect(setState).toHaveBeenCalledWith(false);
    });

    test('FilterModalClickClearTest', () => {
        act(() => wrapper.find('#clearButton').props().onClick());
        expect(setState).toHaveBeenCalledTimes(0);
    });

    test('FilterModalChangeCategoryTextFieldTest', () => {
        act(() => wrapper.find('#categoryTextField').props().onChange(eventCategory));
        expect(setState).toHaveBeenCalledTimes(0);
    });

    test('FilterModalChangeAdTypeTextFieldTest', () => {
        act(() => wrapper.find('#adTypeTextField').props().onChange(eventAdType));
        expect(setState).toHaveBeenCalledTimes(0);
    });

    test('FilterModalChangeMetroTextFieldTest', () => {
        act(() => wrapper.find('#metroTextField').props().onChange(eventMetro));
        expect(setState).toHaveBeenCalledTimes(0);
    });

    test('FilterModalJsonParseActiveTest', () => {
        act(() => wrapper.find('#maxPriceTextField').props().onChange(eventMaxPrice2));
        act(() => wrapper.find('#minPriceTextField').props().onChange(eventMinPrice));
        act(() => wrapper.find('#categoryTextField').props().onChange(eventCategory));
        act(() => wrapper.find('#metroTextField').props().onChange(eventMetro));
        act(() => wrapper.find('#adTypeTextField').props().onChange(eventAdType));

        act(() => wrapper.find('#applyButton').props().onClick());

        expect(setState).toHaveBeenCalledWith(false);
        expect(props.setJsonFilter).toHaveBeenCalledTimes(2);
    });

    test('FilterModalJsonParseNegativeTest', () => {
        act(() => wrapper.find('#maxPriceTextField').props().onChange(eventMaxPrice2));
        act(() => wrapper.find('#minPriceTextField').props().onChange(eventMinPrice));
        act(() => wrapper.find('#categoryTextField').props().onChange(eventCategory));
        act(() => wrapper.find('#metroTextField').props().onChange(eventMetro));
        act(() => wrapper.find('#adTypeTextField').props().onChange(eventAdType2));

        act(() => wrapper.find('#applyButton').props().onClick());

        expect(setState).toHaveBeenCalledWith(false);
        expect(props.setJsonFilter).toHaveBeenCalledTimes(2);
    });
});
