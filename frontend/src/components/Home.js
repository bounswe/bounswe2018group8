/**
 * @author Mehmet Calim, Kübra Eryılmaz
 */
import React from 'react';
import { Button } from 'react-bootstrap';
import  { Redirect } from 'react-router-dom';
import axios from 'axios';

import './Home.css';
export var {userID} = '';

export default class Home extends React.Component {
    constructor() {
        super();
        this.state = {
            projects: [],
            isLoading: true,
            errors: null,
            user: '',
            username: '',
        }
        this.callUser = this.callUser.bind(this);
        this.redirectToProfile = this.redirectToProfile.bind(this);
        
    }
    componentDidMount() {
        axios.get("http://52.59.230.90/projects")
            .then(res => {
                this.setState({
                    projects:res.data,
                    isLoading: false
                });
            })
            .catch(error => this.setState({ error, isLoading: false }));
    }

    callUser(userID){
        axios.get("http://52.59.230.90/users/" + userID, {
            headers: {
                Authorization: `JWT ${localStorage.getItem('token')}`
            }
        })
            .then(res => {
                this.setState({
                    user: res.data.username,
                    isLoading: false,
                });
            })
            .catch(error => this.setState({ error, isLoading: false }));
            const {user} = this.state;
        return user;
    }
    redirectToProfile(){
        this.props.history.push('/home');
    }
    render() {
        const { isLoading, projects } = this.state;
        var clientname = '';
        return (
          <React.Fragment>
            <div className="row">
                <div className="col-md-2"></div>
                <div className="col-md-8" id="feed">
                    <img src="/assets/gratelancer.png" className="img-responsive center-block" alt="gratelancer-logo" />
                    <hr/>
                    <div className="col-md-3">   
                        <Button bsStyle="danger" block href="/project">
                        Create a project
                        </Button>
                    </div>
                    <hr/>
                    <hr/>                
                    {!isLoading ? (
                        projects.map(project => {
                            const { id, title, description, deadline, max_price, min_price,
                                status, client, freelancer} = project;
                            if(localStorage.getItem('token')!= null){
                                clientname=this.callUser(client);
                                userID = client;
                            }
                            else{
                                clientname="(login to see who is the owner of this project)"
                            } 
                        return (
                            <div key={id} className="project">
                                <p><b>{title}</b></p>
                                <img src="/assets/freelancer1.jpg" className="img-responsive center-block" />
                                <p><b>
                                    <Button bsStyle="success" href="/user">
                                        Client profile
                                    </Button> 
                                </b></p>
                                <p><b>Project's owner: </b>{clientname}</p>
                                <p><b>Description: </b>{description}</p>
                                <p><b>Deadline: </b>{deadline}</p>
                                <p><b>Price: </b>{min_price} - {max_price} &#8378;</p>
                                <p><b>Status: </b>{status}</p>
                            </div>
                        
                        );
                        })
                    ) : (
                        <p>Loading...</p>
                    )}

                </div>
                <div className="col-md-2"></div>
                </div>
          </React.Fragment>
        );
      }
}
