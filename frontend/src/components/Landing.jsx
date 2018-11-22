/**
 * @author Mehmet Calim
 */
import React, { Component } from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import Nav from './Nav';
import Home from './Home';
import Profile from './Profile';
import LoginForm from './LoginForm';
import SignupForm from './SignupForm';
import {Navbar,MenuItem,NavItem,NavDropdown, Jumbotron} from "react-bootstrap";
import '../App.css';

class Landing extends Component {
    constructor(props) {
        super(props);
        this.state = {
            displayed_form: '',
            logged_in: localStorage.getItem('token') ? true : false,
            username: ''
        };
    }

    componentDidMount() {
        if (this.state.logged_in) {
            fetch('http://localhost:8000/core/current_user/', {
                headers: {
                    Authorization: `JWT ${localStorage.getItem('token')}`
                }
            })
                .then(res => res.json())
                .then(json => {
                    this.setState({ username: json.username });
                });
        }
    }

    handle_login = (e, data) => {
        e.preventDefault();
        fetch('http://52.59.230.90/user/login/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify()
        })
            .then(res => res.json())
            .then(json => {
                localStorage.setItem('token', json.token);
                this.setState({
                    logged_in: true,
                    displayed_form: '',
                    username: json.user.username
                });
            });
    };

    handle_logout = () => {
        localStorage.removeItem('token');
        this.setState({ logged_in: false, username: '' });
    };

    handle_signup = (e, data) => {
        e.preventDefault();
        fetch('http://52.59.230.90/user/register/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(res => res.json())
            .then(json => {
                localStorage.setItem('token', json.token);
                this.setState({
                    logged_in: true,
                    displayed_form: '',
                    username: json.username
                });
            });
    };

    display_form = form => {
        this.setState({
            displayed_form: form
        });
    };
    /*
      I implemented login/signup with switch. It may change according to
      final design.
    */
    render() {
        let form;
        switch (this.state.displayed_form) {
            case 'login':
                form = <LoginForm handle_login={this.handle_login} />;
                break;
            case 'signup':
                form = <SignupForm handle_signup={this.handle_signup} />;
                break;
            default:
                form = null;
        }

        return (
            <div className="Landing">
                <Nav
                   logged_in={this.state.logged_in}
                   display_form={this.display_form}
                   handle_logout={this.handle_logout}
                />
                {form}
            </div>
        );
    }
}

export default Landing;
