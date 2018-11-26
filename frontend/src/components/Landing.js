/**
 * @author Mehmet Calim
 */
import React, { Component } from 'react';
import  { Redirect } from 'react-router-dom'
import Nav from './Nav';
import '../App.css';

class Landing extends React.Component {
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
            fetch('https://mehmet.requestcatcher.com/', {
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
    
    //mehmetcalim: We will use this handle when the logut button will be added.
    handle_logout = () => {
        localStorage.removeItem('token');
        this.setState({ logged_in: false, username: '' });
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
                return <Redirect to='/login' />
            case 'signup':             
                return <Redirect to='/register' />
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
