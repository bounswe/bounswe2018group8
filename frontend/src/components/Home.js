/**
 * @author Mehmet Calim, Kübra Eryılmaz
 */
import React from 'react';
import { Button } from 'react-bootstrap';
import  { Link } from 'react-router-dom';
import axios from 'axios';

import './Home.css';

export default class Home extends React.Component {
    constructor() {
        super();
        this.state = {
            projects: [],
            isLoading: true,
            errors: null,
        }        
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

    render() {
        const { isLoading, projects } = this.state;
        var clientname;
        var url;
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
                            const { id, client_id, client_username, freelancer_id, freelancer_username, status, title,
                                description, max_price, min_price, deadline} = project;
                            if(localStorage.getItem('token')!= null){
                                clientname=client_username;
                                url="{`/user/${client_id}`}";
                            }
                            else{
                                clientname="(login to see who is the owner of this project)"
                                url="/";
                            } 
                        return (
                            <div key={id} className="project">
                                <p><b>{title}</b></p>
                                <img src="/assets/freelancer1.jpg" className="img-responsive center-block" />
                                <p><b>Project's owner: </b><Link to={`/user/${client_id}`}>{clientname}</Link></p>
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
