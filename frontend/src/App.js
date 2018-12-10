/**
 * @author Mehmet Calim, Kübra Eryılmaz
 */
import React, { Component } from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import Home from './components/Home';
import Profile from './components/Profile';
import Landing from './components/Landing';
import Login from './components/LoginForm';
import Register from './components/SignupForm';
import Project from './components/Project';
import CustomNavbar from './components/CustomNavbar';
import ClientProfile from './components/ClientProfile';
import SelectedProject from './components/SelectedProject';
import './App.css';


/*
  @mehmetcalim: I added login and register pages routes because of 
  changes in the Landing.js page which is about disributing login
  and signup pages into 2 URLs instead of case usage on main page.
*/
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
              <Route path="/login" component={Login} />
              <Route path="/register" component={Register} />
              <Route path="/project" component={Project} />
              <Route path="/user/:id" component={ClientProfile} />
              <Route path="/projects/:id" component={SelectedProject} />
            </div>
          </Router>
      </div>
    );
  }
}

export default App;
