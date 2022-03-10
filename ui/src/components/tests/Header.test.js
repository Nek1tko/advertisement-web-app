import { mount, shallow } from 'enzyme';
import Header from '../Header';
import { BrowserRouter, Router } from 'react-router-dom';
import AuthService from '../../services/auth.service';
import { createBrowserHistory } from 'history'
import { userSignIn, userSignOut } from '../../actions/authActionCreators';
import store from '../../store';
import { act } from 'react-dom/test-utils';

describe('HeaderTests', () => {
    let mHistory, wrapper

    beforeEach(() => {
        mHistory = {
            ...createBrowserHistory(),
            push: jest.fn()
        };
        wrapper = mount(
            <Router history={mHistory}>
                <Header />
            </Router>
        );
    })

    afterEach(() => {
        act(() => {
            store.dispatch(userSignOut());
        })
    })

    test('HeaderRenderTest', () => {
        mount(
            <BrowserRouter>
                <Header />
            </BrowserRouter>
        );
    });

    test('HeaderSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('HeaderHomeMenuButtonsTest', () => {
        const homeButton = wrapper.find('[id="homeButton"]').first()
        homeButton.simulate('click');

        expect(mHistory.push).toHaveBeenCalledTimes(1);
        expect(mHistory.push).toBeCalledWith("/");
    });

    test('HeaderMyAdsMenuButtonsTest', () => {
        const myAdsButton = wrapper.find('[id="Мои объявления"]').first()
        myAdsButton.simulate('click');

        expect(mHistory.push).toHaveBeenCalledTimes(1);
        expect(mHistory.push).toBeCalledWith("/my-ads");
    });

    test('HeaderCreateAdMenuButtonsTest', () => {
        const createAdButton = wrapper.find('[id="Создать объявление"]').first()
        createAdButton.simulate('click');

        expect(mHistory.push).toHaveBeenCalledTimes(1);
        expect(mHistory.push).toBeCalledWith("/create-ad");
    });

    test('HeaderSettingsCollapseTest', () => {
        const mUser = {
            userId: 0,
            phoneNumber: "phone",
            token: "token"
        };

        const settingsCollapse = wrapper.find('[id="settingsCollapse"]').first();
        expect(settingsCollapse.prop("in")).toBe(false);

        act(() => {
            store.dispatch(userSignIn(mUser.phoneNumber, mUser.userId, mUser.token));
            wrapper.update();
        });

        const unsubscribe = store.subscribe(() => {
            expect(settingsCollapse.prop("in")).toBe(true);
        })
        unsubscribe()
    });

    test('HeaderSettingsIconButtonTest', () => {
        const settingsMenu = wrapper.find('[id="menu-appbar3"]').first();
        expect(settingsMenu.prop("anchorEl")).toBe(null)

        const settingsIconButton = wrapper.find('[id="settingsIconButton"]').first();
        settingsIconButton.simulate("click");

        // smthg odd
        expect(settingsMenu.prop("anchorEl")).toBe(null)
    });

    test('HeaderPersonalAreaSettingsMenuItemTest', () => {
        const personalAreaSettingsMenuItem = wrapper.find('[id="Профиль"]').first();
        personalAreaSettingsMenuItem.simulate('click');

        expect(mHistory.push).toHaveBeenCalledTimes(1);
        expect(mHistory.push).toBeCalledWith("/personal-area");
    });

    test('HeaderFavoritesSettingsMenuItemTest', () => {
        const favoritesSettingsMenuItem = wrapper.find('[id="Избранное"]').first();
        favoritesSettingsMenuItem.simulate('click');

        expect(mHistory.push).toHaveBeenCalledTimes(1);
        expect(mHistory.push).toBeCalledWith("/favorites");
    });

    test('HeaderSignOutSettingsMenuItemTest', () => {
        const mAuthServiceSignOut = jest.spyOn(AuthService, "signOut");
        mAuthServiceSignOut.mockResolvedValue();

        const signOutSettingsItem = wrapper.find('[id="Выйти"]').first();
        signOutSettingsItem.simulate('click');

        expect(mHistory.push).toHaveBeenCalledTimes(1);
        expect(mHistory.push).toBeCalledWith("/login");
        expect(mAuthServiceSignOut).toHaveBeenCalledTimes(1);
    });
});
