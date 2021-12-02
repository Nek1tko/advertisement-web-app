import React from "react"
import Header from "./components/Header";
import {Route, Switch} from "react-router-dom";
import CreateAd from "./routes/CreateAd";
import MyAds from "./routes/MyAds";
import Home from "./routes/Home";

function App() {
    return (
        <div className="container">
            <Header/>
            <Switch>
                <Route exact from="/" render={props => <Home {...props} />}/>
                <Route exact path="/my-ads" render={props => <MyAds {...props} />}/>
                <Route exact path="/create-ad" render={props => <CreateAd {...props} />}/>
            </Switch>
        </div>
    );
}

export default App;
