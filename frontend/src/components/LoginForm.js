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
    <div className="row">
      <div className="col-md-4"></div>
      <div className="col-md-4" id="form">
        <form onSubmit={e => this.submit(e)}>
          <img src="/assets/gratelancer.png" className="img-responsive center-block" alt="gratelancer-logo" />

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
          <button type="submit" className="btn btn-primary btn-block">Log In</button>
        </form>
        </div>
        <div className="col-md-4"></div>
      </div>
    );
  }
}

export default LoginForm;
