/**
 * @author Mehmet Calim, Kübra Eryılmaz
 */
import React, {Component} from 'react';
import  { Redirect } from 'react-router-dom';
import { Button, Modal, FormControl, FormGroup, ControlLabel } from 'react-bootstrap';
import './Profile.css';
import axios from 'axios';

export default class Profile extends Component {
    constructor() {
        super();
        this.state = {
            user: [],
            deposit_amount: '',
            isLoading: true,
            errors: null,
            show: false,
        }
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
      }
    // @mehmetcalim: Get API request is added in order to get current user data.
    componentDidMount() {
        axios.get("http://52.59.230.90/users/self/", {
            headers: {
                Authorization: `JWT ${localStorage.getItem('token')}`
            }
        })
            .then(res => {
                this.setState({
                    user: res.data,
                    isLoading: false,
                    isUser: true,
                });
            })
            .catch(error => this.setState({ error, isLoading: false }));
    }
    submit(e) {
        e.preventDefault();
        axios.post('http://52.59.230.90/users/self', {
          deposit_amount: this.state.deposit_amount,
        },{
            headers: {
            Authorization: `JWT ${localStorage.getItem('token')}`
            }
        }).then(res => {
                this.setState({
                    user: res.data,
                    isLoading: false,
                    isUser: true,
                });
            })
            .catch(error => this.setState({error, isLoading: false}));
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
    render() {
        if(localStorage.getItem('token')== null){
            return <Redirect to='/' />
        }    
        else{
            const { isLoading, user } = this.state;
            return (
            <React.Fragment>
                <div className="row">
                    <div className="col-md-4"></div>
                    <div className="col-md-4" id="profile">
                    <h2><b>User Profile</b></h2>
                    <hr/>
                        <div>
                        {!isLoading ? (
                            <div key={user.pk}>
                                <p><b>Name: </b>{user.first_name}</p>
                                <p><b>Surname: </b>{user.last_name}</p>
                                <p><b>Email: </b>{user.email}</p>
                                <p><b>username: </b>{user.username}</p>
                                <p><b>Bio: </b>{user.bio}</p>
                                <p><b>Balance: </b>{user.balance}</p>
                                <hr/>
                                <div className="col-md-4 col-md-offset-5">   
                                    <Button bsStyle="success" bsSize="large" onClick={this.handleShow}>
                                        Deposit
                                    </Button>
                                <br/>
                                <br/>
                                </div>
                                <br/>
                                <Modal show={this.state.show} onHide={this.handleClose}>
                                    <Modal.Header closeButton>
                                        <Modal.Title>Request to deposit</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <form onSubmit={e => this.submit(e)}>
                                            <FormGroup controlId="deposit_amount">
                                                <ControlLabel>Please specify your deposit amount</ControlLabel>
                                                <FormControl
                                                    autoFocus
                                                    type="text"
                                                    name="deposit_amount"
                                                    placeholder="Enter your deposit amount"
                                                    value={this.state.deposit_amount}
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
