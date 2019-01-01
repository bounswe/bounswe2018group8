/**
 * @author Mehmet Calim
 */
import React from 'react';
import PropTypes from 'prop-types';
import {Button} from "react-bootstrap";


function Nav(props) {
  const wellStyles = { maxWidth: 400, margin: '0 auto 0px' };

  const logged_in_nav = (
    <div className="text-center">
    <button onClick={props.handle_logout} type="submit" className="btn btn-primary btn-block">Logout</button>
    </div>
  );

  const logged_out_nav = (
    <div className="well" style={wellStyles}>
      <Button bsStyle="primary"  block
      onClick={() => props.display_form('login')}>
        Login
      </Button>
      <Button  block
      onClick={() => props.display_form('signup')}>
        Register
      </Button>
    </div>
  );

  return (
  <div>
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
