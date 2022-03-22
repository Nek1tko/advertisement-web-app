/**
 * @group unit
 */

import SellerAd from '../SellerAd';
import { mount, shallow } from 'enzyme';
import axios from 'axios';
import { act } from 'react-dom/test-utils';
import React from "react";
import { render, fireEvent } from '@testing-library/react';

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

describe('SellerAdTests', () => {
    let wrapper;
    const mSetState = jest.fn();
    let mUseState;

    const props = {
        location: {
            ad: mAd
        }
    };

    const mGetResponse = {
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

    beforeEach(() => {
        axios.get.mockResolvedValue(mGetResponse);
        mUseState = jest.spyOn(React, 'useState');
        mUseState.mockImplementation((init) => [init, mSetState]);
        wrapper = mount(<SellerAd {...props} />);
    })

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('SellerAdRenderTest', () => {
        act(() => {
            mount(<SellerAd {...props} />);
        });
    });

    test('SellerAdSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('SellerAdEditButtonTest', () => {
        act(() => {
            const editButton = wrapper.find('#editButton').first();
            editButton.simulate('click');
        });

        expect(mSetState).toHaveBeenCalledWith(true);
    });

    test('SellerAdCancelButtonTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));
        jest.clearAllMocks();

        act(() => {
            const editButton = wrapper.find('#editButton').first();
            editButton.simulate('click');
        });

        act(() => {
            wrapper.update();
        });

        act(() => {
            const dived = wrapper.dive();
            const cancelButton = dived.find('#cancelButton').first();
            cancelButton.simulate('click');
        });

        expect(mSetState.mock.calls[0][0]).toBe(true);
        expect(mSetState.mock.calls[1][0]).toBe(false);
        expect(mSetState.mock.calls[2][0]).toBe(false);
        expect(mSetState.mock.calls[3][0]).toBe(mAd.description);
    });

    test('SellerAdSaveButtonTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));
        jest.clearAllMocks();

        const mPutResponse = {
            data: {
                description: "axios mock description",
                name: "axios mock name",
                price: 1001,
                metro: "axios mock metro",
                subCategory: {
                    category: "axios mock category"
                },
                isActive: false,
            }
        }
        axios.put.mockResolvedValue(mPutResponse);

        act(() => {
            const dived = wrapper.dive();
            const saveButton = dived.find('#saveButton').first();
            saveButton.simulate('click');
        });

        expect(axios.put).toHaveBeenCalledTimes(1);
        expect(axios.put.mock.calls[0][0]).toBe("http://localhost:8080/ad");
    });

    test('SellerAdSaveButtonNegativeTest', () => {
        mUseState.mockRestore();
        act(() => wrapper = shallow(<SellerAd {...props} />));

        let dived = wrapper.dive();
        act(() => {
            const subcategoryTextField = dived.find('#subcategoryTextField').first();
            subcategoryTextField.props().onChange({ target: { value: "" } });
        });

        act(() => {
            wrapper.update();
        });

        dived = wrapper.dive();
        act(() => {
            const saveButton = dived.find('#saveButton').first();
            saveButton.simulate('click');
        });

        expect(axios.put).toHaveBeenCalledTimes(0);
    });

    test('SellerAdFavoriteIconButtonTest', () => {
        axios.put.mockResolvedValue({
            data: {
                isFavourite: true
            }
        });

        act(() => {
            const favButton = wrapper.find('#favoriteIconButton').first();
            favButton.props().onClick();
        });

        expect(axios.put).toHaveBeenCalledTimes(1);
        expect(axios.put.mock.calls[0][0]).toBe("http://localhost:8080/ad/favourites");
    });

    test('SellerAdDescriptionTextFieldTest', () => {
        const descriptionTextField = wrapper.find('#descriptionTextField').first();
        expect(descriptionTextField.props().value).toBe(mAd.description);

        const mDescription = "mDescription";
        act(() => {
            descriptionTextField.props().onChange({ target: { value: mDescription } });
        });

        expect(mSetState).toHaveBeenCalledWith(mDescription);
    });

    test('SellerAdIsActiveCheckBoxTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));

        const mIsActive = true;
        act(() => {
            const dived = wrapper.dive();
            const isActiveFormControlLabel = dived.find('#isActiveFormControlLabel').first()
            isActiveFormControlLabel.props().control.props.onChange({ target: { checked: mIsActive } });
        });

        expect(mSetState).toHaveBeenCalledWith(!mIsActive);
    });

    test('SellerAdNameTextFieldTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));
        jest.clearAllMocks();

        const dived = wrapper.dive();
        const nameTextField = dived.find('#nameTextField').first();
        expect(nameTextField.props().value).toBe(mAd.name);

        const mName = "correct mock name";
        act(() => {
            nameTextField.props().onChange({ target: { value: mName } });
        });

        expect(mSetState.mock.calls[0][0]).toBe(mName);
        expect(mSetState.mock.calls[1][0]).toBe(false);
    });

    test('SellerAdNameTextFieldNegativeTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));
        jest.clearAllMocks();

        const dived = wrapper.dive();
        const nameTextField = dived.find('#nameTextField').first();
        expect(nameTextField.props().value).toBe(mAd.name);

        const mName = "";
        act(() => {
            nameTextField.props().onChange({ target: { value: mName } });
        });

        expect(mSetState.mock.calls[0][0]).toBe(true);
        expect(mSetState.mock.calls[1][0]).toBe("Название объявления должно быть длиной от 3 до 50 символов");
    });

    test('SellerAdMetroTextFieldTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));

        const dived = wrapper.dive();
        const metroTextField = dived.find('#metroTextField').first();
        expect(metroTextField.props().value).toBe(mAd.metro);

        const mMetro = "mock metro";
        act(() => {
            metroTextField.props().onChange({ target: { value: mMetro } });
        });

        expect(mSetState).toHaveBeenCalledWith(mMetro);
    });

    test('SellerAdPriceTextFieldTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));

        const dived = wrapper.dive();
        const priceTextField = dived.find('#priceTextField').first();
        expect(priceTextField.props().value).toBe(mAd.price);

        const mPrice = 1001;
        act(() => {
            priceTextField.props().onChange({ target: { value: mPrice } });
        });

        expect(mSetState).toHaveBeenCalledWith(mPrice);
    });

    test('SellerAdCategoryTextFieldTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));

        const dived = wrapper.dive();
        const categoryTextField = dived.find('#categoryTextField').first();
        expect(categoryTextField.props().value).toBe(mAd.subcategory.category);

        const mCategory = "mockcategory";
        act(() => {
            categoryTextField.props().onChange({ target: { value: mCategory } });
        });

        expect(mSetState).toHaveBeenCalledWith(mCategory);
    });

    test('SellerAdSubcategoryTextFieldTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));

        const dived = wrapper.dive();
        const subcategoryTextField = dived.find('#subcategoryTextField').first();
        expect(subcategoryTextField.props().value).toBe(mAd.subcategory);

        const mSubcategory = "mocksubcategory";
        act(() => {
            subcategoryTextField.props().onChange({ target: { value: mSubcategory } });
        });

        expect(mSetState).toHaveBeenCalledWith(mSubcategory);
    });

    test('SellerAdEditedDescriptionTextFieldTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));
        jest.clearAllMocks();

        const dived = wrapper.dive();
        const descriptionTextField = dived.find('#editedDescriptionTextField').first();
        expect(descriptionTextField.props().value).toBe(mAd.description);

        const mDescription = "mock correct description";
        act(() => {
            descriptionTextField.props().onChange({ target: { value: mDescription } });
        });

        expect(mSetState.mock.calls[0][0]).toBe(mDescription);
        expect(mSetState.mock.calls[1][0]).toBe(false);
    });

    test('SellerAdEditedDescriptionTextFieldNegativeTest', () => {
        act(() => wrapper = shallow(<SellerAd {...props} />));
        jest.clearAllMocks();

        const dived = wrapper.dive();
        const descriptionTextField = dived.find('#editedDescriptionTextField').first();
        expect(descriptionTextField.props().value).toBe(mAd.description);

        const mDescription = "";
        act(() => {
            descriptionTextField.props().onChange({ target: { value: mDescription } });
        });

        expect(mSetState.mock.calls[0][0]).toBe(true);
        expect(mSetState.mock.calls[1][0]).toBe("Длина описания не должна быть меньше 1 и больше 250");
    });
});
