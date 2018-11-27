/**
 * @author Mehmet Calim
 */
import React from 'react';
import { FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import './Login.css';
import axios from 'axios'

class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: ''
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

  // @mehmetcalim: I added login request function here instead of Landing.js

  submit(e) {
    e.preventDefault();
    axios.post('http://52.59.230.90/user/login/', {
      username: this.state.username,
      password: this.state.password
    }).then(res => {
      localStorage.setItem('token', res.data.token);
      localStorage.setItem('user', JSON.stringify(res.data.user));
      this.props.history.push('/home')
    }).catch(() => this.setState({
      error: true
    }));
  }

  render() {
    return (
      <form onSubmit={e => this.submit(e)}>
        <h4>Log In</h4>
        <FormGroup controlId="username">
            <ControlLabel>Username</ControlLabel>
            <FormControl
              autoFocus
              type="text"
              name="username"
              placeholder="Enter your username"
              value={this.state.username}
              onChange={this.handle_change}
            />
        </FormGroup>
        <FormGroup controlId="password">
            <ControlLabel>Password</ControlLabel>
            <FormControl
              type="password"
              name="password"
              minLength="8"
              placeholder="Enter your password"
              value={this.state.password}
              onChange={this.handle_change}
            />
        </FormGroup>
        <button type="submit" className="btn btn-primary">Submit</button>
      </form>
    );
  }
}

export default LoginForm;

