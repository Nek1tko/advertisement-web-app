import { createStore, applyMiddleware } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import thunk from "redux-thunk";
import authReducer from "./reducers/authReducer";

const middleware = [thunk];

const store = createStore(
    authReducer,
    composeWithDevTools(applyMiddleware(...middleware))
);

export default store;