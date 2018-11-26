/**
 * @author Mehmet Calim
 */
import React from 'react';
import PropTypes from 'prop-types';
import {Button} from "react-bootstrap";


function Nav(props) {
  const wellStyles = { maxWidth: 400, margin: '0 auto 0px' };

  const logged_in_nav = (
    <ul>
      <li onClick={props.handle_logout}>logout</li>
    </ul>
  );

  const logged_out_nav = (
    <div className="well" style={wellStyles}>
      <Button bsStyle="primary" bsSize="large" block 
      onClick={() => props.display_form('login')}>
        Login
      </Button>
      <Button bsSize="large" block
      onClick={() => props.display_form('signup')}>
        Register
      </Button>
    </div>
  );

  return (
  <div>
    {/*{navBarInstance}*/}
    {props.logged_in ? logged_in_nav : logged_out_nav}
  </div>   
 );
}

export default Nav;

Nav.propTypes = {
  logged_in: PropTypes.bool.isRequired,
  display_form: PropTypes.func.isRequired,
  handle_logout: PropTypes.func.isRequired
};
