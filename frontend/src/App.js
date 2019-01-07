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

  constructor(props) {
    super(props);
    this.state = {
        searchedString: "",
        user: null,
    };
  }

  componentDidMount() {
    const userInfoString = localStorage.getItem('user');
    if(userInfoString !== null) {
      this.setState({
          user: JSON.parse(userInfoString),
      });
    }
  }

  handleSearchChange = (searchedString) => {
      this.setState({
          searchedString
      });
  };
  // @mehmetcalim: all pages are defined here.
  render() {
    return (
      <div className="App">
          <Router>
            <div>
              <CustomNavbar onSearchChanged={this.handleSearchChange} />
              <Route exact path="/"  component={Landing}/>
              <Route path="/profile" component={Profile} />
              <Route path="/home" render={() => <Home searchedString={this.state.searchedString} />} />
              <Route path="/login" component={Login} />
              <Route path="/register" component={Register} />
              <Route path="/project" component={Project} />
              <Route path="/user/:id" component={ClientProfile} />
              <Route path="/projects/:id" render={(routeProps) => <SelectedProject {...routeProps} user={this.state.user} />} />
            </div>
          </Router>
      </div>
    );
  }
}

export default App;
