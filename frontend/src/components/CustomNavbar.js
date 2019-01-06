import React, {Component} from 'react';
import { Navbar, Nav, NavItem, FormGroup, FormControl, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import './CustomNavbar.css';

// kubraeryilmaz: custom navigation bar implementation.
export default class CustomNavbar extends Component {
    render(){
        const { onSearchChanged } = this.props;
        return(
           <Navbar>
               <Navbar.Header>
                   <Navbar.Toggle/>
                   <Navbar.Brand>
                       <Link to="/"><img src="/assets/gratelancer3.png" className="img-responsive navbar-brand" alt="gratelancer-logo" /></Link>
                   </Navbar.Brand>
                   <Navbar.Toggle/>
               </Navbar.Header>
               <Navbar.Collapse>
                   <Navbar.Form pullLeft>
                       <FormGroup>
                           <FormControl type="text" placeholder="Search" onChange={(ev) => onSearchChanged(ev.target.value)} />
                       </FormGroup>{' '}
                       <Button type="submit"><span className="glyphicon glyphicon-search"></span></Button>
                   </Navbar.Form>
                   <Nav pullRight>
                       <NavItem eventKey={1} componentClass={Link} href="/home" to="/home">
                           Home
                       </NavItem>
                       <NavItem eventKey={2} componentClass={Link} href="/profile" to="/profile">
                           Profile
                       </NavItem>
                       <NavItem eventKey={2} componentClass={Link} href="/project" to="/project">
                           <span className="glyphicon glyphicon-plus"></span>
                       </NavItem>
                   </Nav>
               </Navbar.Collapse>
           </Navbar>
        )
    }
}
