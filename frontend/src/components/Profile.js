import React, {Component, PropTypes} from 'react';
import { Link } from 'react-router-dom';
import { Jumbotron, Grid, Row, Col, Image, Button } from 'react-bootstrap';
import './Profile.css';
import axios from 'axios';

export default class Profile extends Component {
    constructor() {
        super();
        this.state = {
            profile: [],
            logged_in: localStorage.getItem('token') ? true : false,
            username: '',
            isLoading: true,
            errors: null
        }
      }
    componentDidMount() {
        axios.get("http://52.59.230.90/user/current", {
            headers: {
                Authorization: `JWT ${localStorage.getItem('token')}`
            }
        })
            .then(res => {
                console.log(res);
                this.setState({
                    profile:res.data,
                    isLoading: false,
                    username: res.username
                });
            })
            .catch(error => this.setState({ error, isLoading: false })); 
    }
    render() {
        const { isLoading, profile } = this.state;
        return (
          <React.Fragment>
            <h2><b>User Profile</b></h2>
            <hr/>
            <div>
              {!isLoading ? (
                profile.map(user_profile => {
                    const { pk, username, email, first_name, last_name, is_client,
                        bio, projects, profile} = user_profile;
                  return (
                    <div key={pk}>
                        <p><b>Name: </b>{first_name}</p>
                        <p><b>Surname: </b>{last_name}</p>
                        <p><b>Username: </b>{username}</p>
                        <p><b>Bio: </b>{bio}</p>
                        <p><b>Projects: </b>{projects}</p>
                        <p><b>Skills: </b>{profile}</p>
                        <hr/>
                    </div>
                  );
                })
              ) : (
                <p>Loading...</p>
              )}
            </div>
          </React.Fragment>
        );
      }    
}
