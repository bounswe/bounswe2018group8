/**
 * @author Mehmet Calim, Kübra Eryılmaz
 */
import React from 'react';
import { Button, Modal, FormControl, FormGroup, ControlLabel } from 'react-bootstrap';
import  { Link } from 'react-router-dom';
import axios from 'axios';

import './Home.css';

export default class Home extends React.Component {
    constructor() {
        super();
        this.state = {
            projects: [],
            project_id: '',
            isLoading: true,
            errors: null,
            show: false,
            amount: '',
        }    
        
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }
    // mehmetcalim: this function send get reqeust to get all projects to homepage.

    componentDidMount() {
        axios.get("http://52.59.230.90/projects")
            .then(res => {
                this.setState({
                    projects:res.data,
                    isLoading: false
                });
                //localStorage.setItem('projects_info', JSON.stringify(res.data));
            })
            .catch(error => this.setState({ error, isLoading: false }));
    }
    // mehmetcalim: these handles for biding popup.

    handleClose() {
        this.setState({ show: false });
    }
    
    handleShow() {
        if(localStorage.getItem('token')== null){
            this.setState({ show: false });
        }
        else{
            this.setState({ show: true });
        }
    }

    handle_change = e => {
        const name = e.target.name;
        const value = e.target.value;
        this.setState(prevstate => {
          const newState = { ...prevstate };
          newState[name] = value;
          return newState;
        });
    };

    // mehmetcalim: this submit function belongs to popup window.
    
    submit(e,id) {
        e.preventDefault();
        axios.post('http://52.59.230.90/projects/' + id +'/',
            {
            amount: this.state.amount,
            }, 
            {
            headers: {
                Authorization: `JWT ${localStorage.getItem('token')}`
            }
            }
        ).then(res => {
          //localStorage.setItem('project_details', res.data);
        }).catch(() => this.setState({
          error: true
        }));
    }
    

    render() {
        const { isLoading, projects } = this.state;
        var project_id;
        var clientname;
        var url;
        var url2;
        return (
          <React.Fragment>
            <div className="row">
                <div className="col-md-2"></div>
                <div className="col-md-8" id="feed">
                    <Link to="/"> <img src="/assets/gratelancer.png" className="img-responsive center-block" alt="gratelancer-logo" /> </Link>
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
                            const { id, client_id, client_username, freelancer_id, freelancer_username, status, bids, title,
                                description, max_price, min_price, deadline} = project;
                                this.project_id={id};
                            if(localStorage.getItem('token')!= null){
                                project_id=id;
                                clientname=client_username;
                                url=`/user/${client_id}`;
                                url2=`/projects/${id}`;
                            }
                            else{
                                clientname="(login to see who is the owner of this project)"
                                url="/";
                                url2="/";
                            } 
                        return (
                            <div key={id} className="project">
                                <p><b>{title}</b></p>
                                <img src="/assets/freelancer1.jpg" className="img-responsive center-block" />
                                <p><b>Project's owner: </b><Link to={url}>{clientname}</Link></p>
                                <p><b>Description: </b>{description}</p>
                                <p><b>Deadline: </b>{deadline.toString().substr(0,10) + " " + deadline.toString().substr(11,5)}</p>
                                <p><b>Price: </b>{min_price} - {max_price} &#8378;</p>
                                <p><b>Status: </b>{status}</p>
                                <p><b>Project details and biding:</b>
                                    <Button bsStyle="success" bsSize="xsmall" block href={url2}>
                                        Project details
                                    </Button>
                                </p>
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
