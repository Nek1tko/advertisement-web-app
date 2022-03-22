/**
 * @group unit
 */

import { mount, shallow } from 'enzyme';
import AdRecordsTable from '../AdRecordsTable';
import { createBrowserHistory } from 'history';
import { act } from 'react-dom/test-utils';

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

describe('AdRecordsTableTests', () => {
    let mHistory, wrapper, props

    beforeEach(() => {
        mHistory = {
            ...createBrowserHistory(),
            push: jest.fn()
        };
        props = {
            history: mHistory,
            userId: mUserId,
            ads: mAds
        }
        wrapper = mount(<AdRecordsTable {...props} />);
    })

    afterEach(() => {
        jest.clearAllMocks();
    })

    test('AdRecordTableTest', () => {
        mount(<AdRecordsTable {...props} />);
    });

    test('AdRecordTableSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('AdRecordTableDataGridTest', () => {
        const dataGrid = wrapper.find('#adDataGrid').first();

        let mClick = {
            row: {
                saler: {
                    id: mUserId
                }
            }
        }
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
});
