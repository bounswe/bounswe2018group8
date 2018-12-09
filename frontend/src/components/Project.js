/**
 * @author Mehmet Calim
 */
import React from 'react';
import {FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import  { Redirect } from 'react-router-dom';
import axios from 'axios';
import './Project.css';

class Project extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      title: '',
      description: '',
      deadline: '',
      max_price: '',
      min_price: '',
    };
    this.headers = {
        'Content-Type': 'application/json',
        'Authorization': `JWT ${localStorage.getItem('token')}`
    };
    this.data = {
            title: this.state.title,
            description: this.state.description,
            deadline: this.state.deadline,
            max_price: this.state.max_price,
            min_price: this.state.min_price,
    };
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

  // @mehmetcalim: I added project creation request function here.


  submit(e) {
    e.preventDefault();
    axios.post('http://52.59.230.90/projects/', 
        {
        title: this.state.title,
        description: this.state.description,
        deadline: this.state.deadline,
        max_price: this.state.max_price,
        min_price: this.state.min_price,
        },
        {
        headers: {
            Authorization: `JWT ${localStorage.getItem('token')}`
        }
        }).then(res => {
        this.props.history.push('/home');
    }).catch(() => this.setState({

      error: true
    }));
  }

  render() {
    if(localStorage.getItem('token')== null){
        return <Redirect to='/' />
    }    
    else{
        return (
        <div className="row">
            <div className="col-md-4"></div>
            <div className="col-md-4" id="form">
            <form onSubmit={e => this.submit(e)}>
            <img src="/assets/gratelancer.png" className="img-responsive center-block" alt="gratelancer-logo" />

                <FormGroup controlId="first_name" className="form-row">
                <ControlLabel> Title </ControlLabel>
                <FormControl
                    autoFocus
                    type="text"
                    name="title"
                    placeholder="ex: Android Project"
                    value={this.state.title}
                    onChange={this.handle_change}
                />
                </FormGroup>{' '}
                <FormGroup controlId="Description" className="textarea">
                <ControlLabel> Description </ControlLabel>
                <FormControl
                    type="text"
                    name="description"
                    placeholder="ex: Tetris"
                    value={this.state.description}
                    onChange={this.handle_change}
                />
                </FormGroup>
                <FormGroup controlId="Deadline" className="form-row">
                <ControlLabel>Deadline</ControlLabel>
                <FormControl
                    type="text"
                    name="deadline"
                    placeholder="2019-04-05T09:05:00Z"
                    value={this.state.deadline}
                    onChange={this.handle_change}
                />
                </FormGroup>
                <FormGroup controlId="max_price" className="form-row">
                <ControlLabel>Max price</ControlLabel>
                <FormControl
                    type="text"
                    name="max_price"
                    pattern="[0-9]*"
                    placeholder="ex: 1000"
                    value={this.state.max_price}
                    onChange={this.handle_change}
                />
                </FormGroup>
                <FormGroup controlId="min_price" className="form-row">
                <ControlLabel>Min price</ControlLabel>
                <FormControl
                    type="text"
                    name="min_price"
                    pattern="[0-9]*"
                    placeholder="ex: 500"
                    value={this.state.min_price}
                    onChange={this.handle_change}
                />
                </FormGroup>
            <button type="submit" className="btn btn-primary btn-block">Create</button>
            </form>
            </div>
            <div className="col-md-4"></div>
        </div>
        );
    }
  } 
}
export default Project;
