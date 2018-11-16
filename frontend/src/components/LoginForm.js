/**
 * @author Mehmet Calim
 */
import React from 'react';
import { FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import './Login.css';
import PropTypes from 'prop-types';

class LoginForm extends React.Component {
  state = {
    username: '',
    password: ''
  };

  handle_change = e => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState(prevstate => {
      const newState = { ...prevstate };
      newState[name] = value;
      return newState;
    });
  };

  render() {
    return (
      <form onSubmit={e => this.props.handle_login(e, this.state)}>
        <h4>Log In</h4>
        <FormGroup controlId="username" bsSize="medium">
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
        <FormGroup controlId="password" bsSize="medium">
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
        <button type="submit" class="btn btn-primary">Submit</button>
      </form>
    );
  }
}

export default LoginForm;

LoginForm.propTypes = {
  handle_login: PropTypes.func.isRequired
};
