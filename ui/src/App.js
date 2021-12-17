import React from "react";
import Header from "./components/Header";
import { Route, Switch } from "react-router-dom";
import CreateAd from "./routes/CreateAd";
import UserAds from "./routes/UserAds";
import Home from "./routes/Home";
import Login from "./routes/Login";
<<<<<<< HEAD
import Registration from "./routes/Registration";
=======
>>>>>>> 14987e9fb03f735672b05ea7e2ac190680e229fb
import PersonalArea from "./routes/PersonalArea";

function App() {
    return (
        <div className="container">
            <Header />
            <Switch>
<<<<<<< HEAD
                <Route exact path="/login" render={props => <Login {...props} />} />
                <Route exact path="/sign-up" render={props => <Registration {...props} />} />
                <Route exact path="/" render={props => <Home {...props} />} />
                <Route exact path="/personal-area" render={props => <PersonalArea {...props} />} />
=======
                <Route exact from="/" render={props => <Home {...props} />} />
                <Route exact from="/login" render={props => <Login {...props} />} />
                <Route exact from="/personal-area" render={props => <PersonalArea {...props} />} />
>>>>>>> 14987e9fb03f735672b05ea7e2ac190680e229fb
                <Route exact path="/my-ads" render={props => <UserAds {...props} />} />
                <Route exact path="/create-ad" render={props => <CreateAd {...props} />} />
            </Switch>
        </div>
    );
}

export default App;
