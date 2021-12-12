import React from "react";
import Header from "./components/Header";
import { Route, Switch } from "react-router-dom";
import CreateAd from "./routes/CreateAd";
import UserAds from "./routes/UserAds";
import Home from "./routes/Home";
import Login from "./routes/Login";

function App() {
    return (
        <div className="container">
            <Header />
            <Switch>
                <Route exact from="/" render={props => <Home {...props} />} />
                <Route exact from="/login" render={props => <Login {...props} />} />
                <Route exact path="/my-ads" render={props => <UserAds {...props} />} />
                <Route exact path="/create-ad" render={props => <CreateAd {...props} />} />
            </Switch>
        </div>
    );
}

export default App;
