import AuthService from "../auth.service";
import authHeader from "../auth-header";

jest.mock('../auth.service');

const user = {
    token: "token"
};

const returnedValue = {
    Authorization: 'Bearer ' + user.token
};

describe('AuthServiceHeader', () => {
    test("AuthServiceHeaderTest", () => {
        AuthService.getUser.mockReturnValue(user);
        expect(authHeader()).toStrictEqual(returnedValue);
    });
});
