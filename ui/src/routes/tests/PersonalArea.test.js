/**
 * @group unit
 */

import React from "react";
import AuthService from "../../services/auth.service";
import { act } from "react-dom/test-utils";
import { mount, shallow } from "enzyme";
import { Router } from "react-router-dom";
import PersonalArea from "../PersonalArea";
import { createBrowserHistory } from "history";
import axios from "axios";

jest.mock('axios');
jest.mock("../../services/auth.service");

const mockUserAxios = {
    data: {
        name: "mockName",
        surname: "mockSurname",
    }
}

const mockUserAuthService = {
    userId: "mockId",
    phoneNumber: "mockPhoneNumber",
}

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


describe('PersonalAreaTests', () => {
    let wrapper, props;
    const setState = jest.fn();

    beforeEach(() => {
        props = {
            history: {
                ...createBrowserHistory(),
                push: jest.fn()
            }
        };
        AuthService.getUser.mockReturnValue(mockUserAuthService);
        axios.get.mockResolvedValue(mockUserAxios);
        axios.put.mockResolvedValue(mockUserAxios);
        const useStateSpy = jest.spyOn(React, 'useState');
        useStateSpy.mockImplementation((state) => [state, setState]);
        wrapper = mount(
            <Router history={props.history}>
                <PersonalArea />
            </Router>);
    });

    afterEach(() => {
        jest.clearAllMocks();
    });


    test('PersonalAreaRenderTest', () => {
        act(() => {
            mount(<Router history={props.history}>
                <PersonalArea />
            </Router>);
        });
    });

    test('PersonalAreaSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('PersonalAreaOpenButtonTest', async () => {
        await act(async () => {
            await wrapper.find('#openButton').first().props().onClick();
        });
        expect(setState).toHaveBeenCalledTimes(5);
    });

    test('PersonalAreaCloseButtonTest', async () => {
        await act(async () => wrapper = await shallow(<PersonalArea />));
        jest.clearAllMocks();

        const dived = wrapper.dive();
        await act(async () => {
            await dived.find('#closeButton').first().props().onClick();
        });

        expect(setState).toHaveBeenCalledTimes(4);
    });

    test('PersonalAreaSaveButtonTest', async () => {
        await act(async () => wrapper = await shallow(<PersonalArea />));
        jest.clearAllMocks();

        const dived = wrapper.dive();
        await act(async () => {
            await dived.find('#saveButton').first().props().onClick();
        });

        expect(setState).toHaveBeenCalledTimes(4);
    });

    test('PersonalAreaPositiveChangeNameTextFieldTest', async () => {
        await act(async () => wrapper = await shallow(<PersonalArea />));
        jest.clearAllMocks();

        const dived = wrapper.dive();
        await act(async () => {
            const nameTextField = await dived.find('#nameTextField');
            await nameTextField.props().onChange(eventName);
        });

        expect(setState).toHaveBeenCalledTimes(2);

    });

    test('PersonalAreaNegativeChangeNameTextFieldTest', async () => {
        await act(async () => wrapper = await shallow(<PersonalArea />));
        jest.clearAllMocks();

        const dived = wrapper.dive();
        await act(async () => {
            const nameTextField = await dived.find('#nameTextField');
            await nameTextField.props().onChange(eventName2);
        });

        expect(setState).toHaveBeenCalledTimes(2);
    });

    test('PersonalAreaPositiveChangeSurnameTextFieldTest', async () => {
        await act(async () => wrapper = await shallow(<PersonalArea />));
        jest.clearAllMocks();

        const dived = wrapper.dive();
        await act(async () => {
            const nameTextField = await dived.find('#surnameTextField');
            await nameTextField.props().onChange(eventName);
        });

        expect(setState).toHaveBeenCalledTimes(2);

    });

    test('PersonalAreaNegativeChangeSurnameTextFieldTest', async () => {
        await act(async () => wrapper = await shallow(<PersonalArea />));
        jest.clearAllMocks();

        const dived = wrapper.dive();
        await act(async () => {
            const nameTextField = await dived.find('#surnameTextField');
            await nameTextField.props().onChange(eventName2);
        });

        expect(setState).toHaveBeenCalledTimes(2);
    });

    test('PersonalAreaNotAuthorizedTest', () => {
        AuthService.getUser.mockReturnValue(null);
        wrapper = mount(
            <Router history={props.history}>
                <PersonalArea />
            </Router>);
    });
});
