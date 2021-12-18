import React from "react";
import Header from "./components/Header";
import { Route, Switch } from "react-router-dom";
import CreateAd from "./routes/CreateAd";
import UserAds from "./routes/UserAds";
import Home from "./routes/Home";
import Login from "./routes/Login";
import Registration from "./routes/Registration";
import PersonalArea from "./routes/PersonalArea";
import SellerAd from "./routes/SellerAd";
import CustomerAd from "./routes/CustomerAd";

function App() {
    return (
        <div className="container">
            <Header />
            <Switch>
                <Route exact path="/login" render={props => <Login {...props} />} />
                <Route exact path="/sign-up" render={props => <Registration {...props} />} />
                <Route exact path="/" render={props => <Home {...props} />} />
                <Route exact path="/personal-area" render={props => <PersonalArea {...props} />} />
                <Route exact path="/my-ads" render={props => <UserAds {...props} />} />
                <Route exact path="/create-ad" render={props => <CreateAd {...props} />} />
                <Route exact path="/seller-ad" render={props => <SellerAd {...props} />} />
                <Route exact path="/customer-ad" render={props => <CustomerAd {...props} />} />
            </Switch>
        </div>
    );
}

export default App;
