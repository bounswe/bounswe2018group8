/**
 * @author Mehmet Calim
 */
import React from 'react';
import {FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import axios from 'axios';
import './SignupForm.css';
/*
  is_client is a boolean condition and
  its validation was not created yet.
*/

// @mehmetcalim: I added 2 state fields:first_name and last_name.
class SignupForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: '',
      email: '',
      password1: '',
      password2: '',
      first_name: '',
      last_name: '',
      //is_client: true,
    };
  }

  handle_change = e => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState(prevstate => {
      const newState = { ...prevstate };
      newState[name] = value;
      return newState;
    });
  };

  // @mehmetcalim: I added register request function here instead of Landing.js and also used axios.

  submit(e) {
    e.preventDefault();
    axios.post('http://52.59.230.90/register/', {
      username: this.state.username,
      email: this.state.email,
      password1: this.state.password1,
      password2: this.state.password2,
      first_name: this.state.first_name,
      last_name: this.state.last_name,
      //is_client: this.state.is_client,

    }).then(res => {
      localStorage.setItem('user_info', res.data);
      this.props.history.push('/login')
    }).catch(() => this.setState({

      error: true
    }));
  }

  // @mehmetcalim: Shows sign-up form.
  render() {
    return (
      <div className="row">
        <div className="col-md-4"></div>
        <div className="col-md-4" id="form">
        <form onSubmit={e => this.submit(e)}>
          <img src="/assets/gratelancer-black.png" className="img-responsive center-block form-logo" alt="gratelancer-logo" />

            <FormGroup controlId="first_name" className="form-row">
              <FormControl
                autoFocus
                type="text"
                name="first_name"
                placeholder="Name"
                value={this.state.first_name}
                onChange={this.handle_change}
              />
            </FormGroup>{' '}
            <FormGroup controlId="last_name" className="form-row">
              <FormControl
                type="text"
                name="last_name"
                placeholder="Surname"
                value={this.state.last_name}
                onChange={this.handle_change}
              />
            </FormGroup>
            <FormGroup controlId="username" className="form-row">
              <FormControl
                type="text"
                name="username"
                placeholder="Username"
                value={this.state.username}
                onChange={this.handle_change}
              />
            </FormGroup>
            <FormGroup controlId="email" className="form-row">
              <FormControl
                type="email"
                name="email"
                placeholder="E-mail"
                value={this.state.email}
                onChange={this.handle_change}
              />
            </FormGroup>
            <FormGroup controlId="password">
              <FormControl
                type="password"
                name="password1"
                minLength="8"
                placeholder="Password"
                value={this.state.password1}
                onChange={this.handle_change}
              />
            </FormGroup>
            <FormGroup controlId="repassword">
              <FormControl
                type="password"
                name="password2"
                placeholder="Re-enter password"
                value={this.state.password2}
                onChange={this.handle_change}
              />
            </FormGroup>
          <button type="submit" className="btn btn-primary btn-block">Sign Up</button>
        </form>
        </div>
        <div className="col-md-4"></div>
      </div>
    );
  }
}

export default SignupForm;
