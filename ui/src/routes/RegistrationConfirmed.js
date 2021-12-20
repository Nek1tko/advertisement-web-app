import Redirect from "react-router-dom/es/Redirect";
import {Component} from "react";

export default class RegistrationConfirmed extends Component {
    state = {
        redirect: false
    }

    componentDidMount() {
        this.id = setTimeout(() => this.setState({redirect: true}), 2000)
    }

    componentWillUnmount() {
        clearTimeout(this.id)
    }

    render() {
        return this.state.redirect
            ? <Redirect to="/login"/>
            : <div>Регистрация прошла успешно!</div>
    }
}