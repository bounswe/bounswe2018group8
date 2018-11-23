/**
 * @author Kübra Eryılmaz
 */
import React, { Component } from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import Nav from './components/Nav';
import Home from './components/Home';
import Profile from './components/Profile';
import Landing from './components/Landing';
import './App.css';
import CustomNavbar from "./components/CustomNavbar";

class App extends Component {
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
