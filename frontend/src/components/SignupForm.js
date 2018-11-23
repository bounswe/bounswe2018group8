/**
 * @author Mehmet Calim
 */
import React from 'react';
import { Form, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import PropTypes from 'prop-types';

/* 
  is_client is a boolean condition and 
  its validation was not created yet.
*/

class SignupForm extends React.Component {
  state = {
    username: '',
    email: '',
    password1: '',
    password2: '',
    is_client: ''
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
      <form onSubmit={e => this.props.handle_signup(e, this.state)}>
        <h4>Register</h4>
        <Form inline>
          <FormGroup controlId="formInlineName">
            <ControlLabel> Name </ControlLabel>{' '}
            <FormControl type="name" placeholder="Morgan" />
          </FormGroup>{' '}
          <FormGroup controlId="formInlineEmail">
            <ControlLabel> Surname </ControlLabel>{' '}
            <FormControl type="surname" placeholder="Freelancer" />
          </FormGroup>
        </Form>
        <FormGroup controlId="username" bsSize="medium" class="form-row">
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
        <FormGroup controlId="email" bsSize="medium" class="form-row">
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
        <FormGroup controlId="password" bsSize="medium">
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
        <FormGroup controlId="repassword" bsSize="medium">
            <ControlLabel>Reenter Password</ControlLabel>
            <FormControl
              type="password"
              name="password2"
              placeholder="Reenter the password above"
              value={this.state.password2}
              onChange={this.handle_change}
            />
        </FormGroup> 
        <button type="submit" class="btn btn-primary">Submit</button>
      </form>
    );
  }
}

export default SignupForm;

SignupForm.propTypes = {
  handle_signup: PropTypes.func.isRequired
};
