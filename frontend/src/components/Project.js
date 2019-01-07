/**
 * @author Mehmet Calim
 */
import React from 'react';
import DatePicker from "react-datepicker";
import {FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import  { Redirect } from 'react-router-dom';
import axios from 'axios';
import './Project.css';
import "react-datepicker/dist/react-datepicker.css";

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
    this.handleChange = this.handleChange.bind(this);
  }

  // @ozankinasakal: I added date handling from datepicker.
  handleChange(date) {
      this.setState({
          deadline: date
      });
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

  // @mehmetcalim: If the user is a guest, redirects her to login/register page.
  render() {
    var user = localStorage.getItem('user');
    if(localStorage.getItem('token')== null){
        return <Redirect to='/' />
    }
    // @mehmetcalim: Shows project creation form.
    // @Kubra Eryılmaz : Added calendar type deadline.
    else{
        return (
        <div className="row">
            <div className="col-md-4"></div>
            <div className="col-md-4" id="form">
            <form onSubmit={e => this.submit(e)}>
            <img src="/assets/gratelancer-black.png" className="img-responsive center-block" alt="gratelancer-logo" />
                <ControlLabel className="text-center"> Create a project </ControlLabel>
                <FormGroup controlId="project_title" className="form-row">

                <FormControl
                    autoFocus
                    type="text"
                    name="title"
                    placeholder="Title"
                    value={this.state.title}
                    onChange={this.handle_change}
                />
                </FormGroup>{' '}
                <FormGroup controlId="Description" className="textarea">

                <FormControl
                    componentClass="textarea"
                    rows={6}
                    type="textarea"
                    name="description"
                    placeholder="Description"
                    value={this.state.description}
                    onChange={this.handle_change}
                />
                </FormGroup>
                <FormGroup controlId="Deadline" className="form-row">


                    <DatePicker
                        showTimeSelect
                        timeFormat="HH:mm"
                        placeholderText="Deadline"
                        timeIntervals={15}
                        timeCaption="time"
                        dateFormat="yyyy/MM/dd h:mm aa"
                        selected={this.state.deadline}
                        value={this.state.deadline}
                        onChange={this.handleChange}
                    />

                </FormGroup>
                <FormGroup controlId="max_price" className="form-row">

                <FormControl
                    type="text"
                    name="max_price"
                    pattern="[0-9]*"
                    placeholder="Maximum Bid Price (&#8378;)"
                    value={this.state.max_price}
                    onChange={this.handle_change}
                />
                </FormGroup>
                <FormGroup controlId="min_price" className="form-row">

                <FormControl
                    type="text"
                    name="min_price"
                    pattern="[0-9]*"
                    placeholder="Minimum Bid Price (&#8378;)"
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
