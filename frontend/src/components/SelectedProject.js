/**
 * @author Mehmet Calim
 */
import React, {Component} from 'react';
import  { Link,Redirect } from 'react-router-dom';
import { Button, Modal, FormControl, FormGroup, ControlLabel } from 'react-bootstrap';
import './Profile.css';
import axios from 'axios';

export default class SelectedProject extends Component {
    constructor() {
        super();
        this.state = {
            project: [],
            isLoading: true,
            errors: null,
            project_id: '',
            amount: '',
            show: false,
        }
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }
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
    // @mehmetcalim: Get API request is added in order to get selected project data.
    componentDidMount() {
        if(localStorage.getItem('token')!= null){
            this.project_id = this.props.match.params.id;
            axios.get("http://52.59.230.90/projects/"+ this.project_id + "/", {
                headers: {
                    Authorization: `JWT ${localStorage.getItem('token')}`
                }
            })
                .then(res => {
                    this.setState({
                        project: res.data,
                        isLoading: false,
                    });
                })
                .catch(error => this.setState({ error, isLoading: false }));
        }
        console.log(this.project);
    }

    // @mehmetcalim: Submit button to bid project.

    submit(e) {
        e.preventDefault();
        const { project } = this.state;
        axios.post('http://52.59.230.90/projects/' + project.id +'/bid/',
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
        if(localStorage.getItem('token')== null){
            return <Redirect to='/' />
        }    
        else{
            const { isLoading, project } = this.state;
            var url=`/user/${project.client_id}`;
            var deadline=project.deadline;
            return (
            <React.Fragment>
                <div className="row">
                    <div className="col-md-4"></div>
                    <div className="col-md-4" id="selected_profile">
                    <h2><b>Project Details</b></h2>
                    <hr/>
                        <div>
                        {!isLoading ? (
                            <div key={project.id} className="project">
                                <p><b>{project.title}</b></p>
                                <img src="/assets/freelancer1.jpg" className="img-responsive center-block" />
                                <p><b>Project's owner: </b><Link to={url}>{project.client_username}</Link></p>
                                <p><b>Description: </b>{project.description}</p>
                                <p><b>Deadline: </b>{deadline.toString().substr(0,10) + " " + deadline.toString().substr(11,5)}</p>
                                <p><b>Price: </b>{project.min_price} - {project.max_price} &#8378;</p>
                                <p><b>Status: </b>{project.status}</p>
                                <p><b>Average Bid:</b><br/>{project.average_bid+'\n'}</p>
                                <p><b>Total number of bids:</b><br/>{project.bid_count+'\n'}</p>

                                <div className="col-md-4 col-md-offset-5">   
                                <Button bsStyle="success" bsSize="large" onClick={this.handleShow}>
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
                                            <FormGroup controlId="amount">
                                                <ControlLabel>Please specify your bid amount</ControlLabel>
                                                <FormControl
                                                    autoFocus
                                                    type="text"
                                                    name="amount"
                                                    placeholder="Enter your bid amount"
                                                    value={this.state.amount}
                                                    onChange={this.handle_change}
                                                />
                                            </FormGroup>
                                            <button type="submit" className="btn btn-primary" href="/home">Request</button>
                                        </form>
                                        <br/>
                                        <br/>
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button onClick={this.handleClose}>Close</Button>
                                    </Modal.Footer>
                                </Modal>
                                
                        </div>

                        ) : (
                        <p>Loading...</p>
                    )}
                    
                        </div>
                    </div>
                    <div className="col-md-4"></div>
                </div>
            </React.Fragment>
            );
        }    
    }
}
