/**
 * @author Mehmet Calim
 */
import React from 'react';
import {FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import axios from 'axios';

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
      is_client: 'false'
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

  // @mehmetcalim: I added register request function here instead of Landing.js
   and also used axios.
  submit(e) {
    e.preventDefault();
    axios.post('http://52.59.230.90/user/register/', {
      username: this.state.username,
      email: this.state.email,
      password1: this.state.password1,
      password2: this.state.password2,
      first_name: this.state.first_name,
      last_name: this.state.last_name,
      is_client: false

    }).then(res => {
      localStorage.setItem('jwt', res.data);
      this.props.history.push('/login')
    }).catch(() => this.setState({

      error: true
    }));
  }

  render() {
    return (
      <form onSubmit={e => this.submit(e)}>
        <h4>Register</h4>
          <FormGroup controlId="first_name" className="form-row">
            <ControlLabel> Name </ControlLabel>
            <FormControl
              autoFocus
              type="text" 
              name="first_name"
              placeholder="Morgan"
              value={this.state.first_name}
              onChange={this.handle_change}
            />
          </FormGroup>{' '}
          <FormGroup controlId="last_name" className="form-row">
            <ControlLabel> Surname </ControlLabel>
            <FormControl 
              type="text"
              name="last_name"
              placeholder="Gratelancer"
              value={this.state.last_name}
              onChange={this.handle_change}
            />
          </FormGroup>
          <FormGroup controlId="username" className="form-row">
            <ControlLabel>Username</ControlLabel>
            <FormControl
              type="text"
              name="username"
              placeholder="Enter your username"
              value={this.state.username}
              onChange={this.handle_change}
            />
          </FormGroup>
          <FormGroup controlId="email" className="form-row">
            <ControlLabel>Email</ControlLabel>
            <FormControl
              type="email"
              name="email"
              placeholder="Enter your email"
              value={this.state.email}
              onChange={this.handle_change}
            />
          </FormGroup>        
          <FormGroup controlId="formControlsSelect">
            <ControlLabel>Select user type</ControlLabel>
            <FormControl componentClass="select" placeholder="select">
              <option value="Client">Client</option>
              <option value="Freelancer">Freelancer</option>
            </FormControl>
          </FormGroup>
          <FormGroup controlId="password">
            <ControlLabel>Password</ControlLabel>
            <FormControl
              type="password"
              name="password1"
              minLength="8"
              placeholder="Enter a min 8 characters password"
              value={this.state.password1}
              onChange={this.handle_change}
            />
          </FormGroup>
          <FormGroup controlId="repassword">
            <ControlLabel>Reenter Password</ControlLabel>
            <FormControl
              type="password"
              name="password2"
              placeholder="Reenter the password above"
              value={this.state.password2}
              onChange={this.handle_change}
            />
          </FormGroup> 
        <button type="submit" className="btn btn-primary">Submit</button>
      </form>
    );
  }
}

export default SignupForm;

