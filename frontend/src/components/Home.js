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
            bid_amount: '',
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
                localStorage.setItem('projects_info', JSON.stringify(res.data));
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
    
    submit(e) {
        e.preventDefault();
        axios.post('http://52.59.230.90/projects/' + this.project_id, {
          bid_amount: this.state.bid_amount,
        }).then(res => {
          localStorage.setItem('project_details', res.data);
        }).catch(() => this.setState({
          error: true
        }));
    }
    

    render() {
        const { isLoading, projects } = this.state;
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
                            const { id, client_id, client_username, freelancer_id, freelancer_username, status, title,
                                description, max_price, min_price, deadline} = project;
                                this.project_id={id};
                            if(localStorage.getItem('token')!= null){
                                clientname=client_username;
                                url=`/user/${client_id}`;
                                url2="";
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
                                    <div className="col-md-4 col-md-offset-5">   
                                    <Button bsStyle="success" bsSize="large" onClick={this.handleShow} href={url2}>
                                        BID
                                    </Button>
                                    </div>
                                    <br/>
                                    <br/>
                                    <Modal show={this.state.show} onHide={this.handleClose}>
                                        <Modal.Header closeButton>
                                            <Modal.Title>Request to bid on this project</Modal.Title>
                                        </Modal.Header>
                                        <Modal.Body>
                                            <form onSubmit={e => this.submit(e)}>
                                                <FormGroup controlId="bid_amount">
                                                    <ControlLabel>Please specify your bid amount</ControlLabel>
                                                    <FormControl
                                                        autoFocus
                                                        type="text"
                                                        name="bid_amount"
                                                        placeholder="Enter your bid amount"
                                                        value={this.state.bid_amount}
                                                        onChange={this.handle_change}
                                                    />
                                                </FormGroup>
                                                <button type="submit" className="btn btn-primary">Request</button>
                                            </form>
                                            <br/>
                                            <br/>
                                        </Modal.Body>
                                        <Modal.Footer>
                                            <Button onClick={this.handleClose}>Close</Button>
                                        </Modal.Footer>
                                    </Modal>
 
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
