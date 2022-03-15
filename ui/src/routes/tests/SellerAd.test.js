import SellerAd from '../SellerAd';
import { mount, shallow } from 'enzyme';

describe('SellerAdTests', () => {
    let wrapper

    beforeEach(() => {
        wrapper = mount(<SellerAd />);
    })

    test('SellerAdRenderTest', () => {
        mount(<SellerAd />);
    });

    test('SellerAdSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });
});
