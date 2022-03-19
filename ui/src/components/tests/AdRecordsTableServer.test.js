import { mount, shallow } from 'enzyme';
import AdRecordsTable from '../AdRecordsTableServer';
import { createBrowserHistory } from 'history';
import { act } from 'react-dom/test-utils';
import axios from 'axios';

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

describe('AdRecordsTableServerTests', () => {
    let mHistory, wrapper, props
    const mSetPage = jest.fn();
    const mSetAds = jest.fn();

    beforeEach(() => {
        mHistory = {
            ...createBrowserHistory(),
            push: jest.fn()
        };
        props = {
            history: mHistory,
            userId: mUserId,
            rowCount: 2,
            API_URL: "api",
            setPage: mSetPage,
            setAds: mSetAds,
            ads: mAds
        }
        wrapper = mount(<AdRecordsTable {...props} />);
    })

    afterEach(() => {
        jest.clearAllMocks();
    })

    test('AdRecordTableServerTest', () => {
        mount(<AdRecordsTable {...props} />);
    });

    test('AdRecordTableServerSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('AdRecordTableServerOnRowClickTest', () => {
        const dataGrid = wrapper.find('#adDataGrid').first();

        let mClick = {
            row: {
                saler: {
                    id: mUserId
                }
            }
        };
        act(() => {
            dataGrid.props().onRowClick(mClick);
        });

        expect(mHistory.push).toHaveBeenCalledWith({ ad: mClick.row, pathname: "/seller-ad" });

        mClick.row.saler.id = mUserId + 1;
        act(() => {
            dataGrid.props().onRowClick(mClick);
        });

        expect(mHistory.push).toHaveBeenCalledWith({ ad: mClick.row, pathname: "/customer-ad" });
    });

    test('AdRecordTableServerOnPageChangeTest', () => {
        const dataGrid = wrapper.find('#adDataGrid').first();

        const mData = "mData";
        axios.post.mockResolvedValue({ data: mData });

        const mPage = 1;
        act(() => {
            dataGrid.props().onPageChange(mPage);
        });

        expect(axios.post).toHaveBeenCalledTimes(1);
        expect(axios.post.mock.calls[0][0]).toBe("api");
        expect(mSetPage).toHaveBeenCalledWith(mPage + 1);
    });
});
