/**
 * @author Mehmet Calim
 */
import React, { Component } from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import Nav from './components/Nav';
import Home from './components/Home';
import Profile from './components/Profile';
import Landing from './components/Landing';
import LoginForm from './components/LoginForm';
import SignupForm from './components/SignupForm';
import {Navbar,MenuItem,NavItem,NavDropdown} from "react-bootstrap";
import './App.css';
import CustomNavbar from "./components/CustomNavbar";

class App extends Component {
  /* 
    I implemented login/signup with switch. It may change according to
    final design.
  */
  render() {
    return (
      <div className="App">
          <Router>
            <div>
              <CustomNavbar/>
              <Route exact path="/"  component={Landing}/>
              <Route path="/profile" component={Profile} />
              <Route path="/home" component={Home} />
            </div>
          </Router>
      </div>
    );
  }
}

export default App;
