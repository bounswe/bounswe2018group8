import React, {Component} from 'react';
import { Navbar, Nav, NavItem,  } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import './CustomNavbar.css';

export default class CustomNavbar extends Component {
    render(){
        return(
           <Navbar>
               <Navbar.Header>
                   <Navbar.Brand>
                       <Link to="/">Gratelancer</Link>
                   </Navbar.Brand>
                   <Navbar.Toggle/>
               </Navbar.Header>
               <Navbar.Collapse>
                   <Nav pullRight>
                       <NavItem eventKey={1} componentClass={Link} to="/home">
                           Home
                       </NavItem>
                       <NavItem eventKey={2} componentClass={Link} to="/profile">
                           Profile
                       </NavItem>

                   </Nav>
               </Navbar.Collapse>
           </Navbar>
        )
    }
}