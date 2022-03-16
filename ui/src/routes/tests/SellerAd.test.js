import SellerAd from '../SellerAd';
import { mount } from 'enzyme';
import axios from 'axios';
import { act } from 'react-dom/test-utils';

jest.mock('axios');

describe('SellerAdTests', () => {
    let wrapper;

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
    const props = {
        location: {
            ad: mAd
        }
    };

    beforeEach(() => {
        axios.get.mockResolvedValue({
            data: [
                {
                    id: 1,
                    name: "mock name"
                }
            ]
        });
        wrapper = mount(<SellerAd {...props} />);
    })

    test('SellerAdRenderTest', () => {
        act(() => {
            mount(<SellerAd {...props} />);
        });
    });

    test('SellerAdSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });
});
