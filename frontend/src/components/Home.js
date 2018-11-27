/**
 * @author Mehmet Calim, Kübra Eryılmaz
 */
import React from 'react';
import { Grid } from 'react-bootstrap';
import axios from 'axios';

import './Home.css';

export default class Home extends React.Component {
    constructor() {
        super();
        this.state = {
            projects: [],
            isLoading: true,
            errors: null
        }
      }
    componentDidMount() {
        axios.get("http://52.59.230.90/")
            .then(res => {
                console.log(res);
                this.setState({
                    projects:res.data,
                    isLoading: false
                });
            })
            .catch(error => this.setState({ error, isLoading: false })); 
    }
    render() {
        const { isLoading, projects } = this.state;
        return (
          <React.Fragment>
            <h2><b>Hello, This is Gratelancer!</b></h2>
            <hr/>
            <div>
              {!isLoading ? (
                projects.map(project => {
                    const { id, title, description, deadline, max_price, min_price,
                        status, client, freelancer} = project;
                  return (
                    <div key={id}>
                        <p><b>Project Title: </b>{title}</p>
                        <p><b>Project Description: </b>{description}</p>
                        <p><b>Project Deadline: </b>{deadline}</p>
                        <p><b>Project Max Price Limit: </b>{max_price}</p>
                        <p><b>Project Min Price Limit: </b>{min_price}</p>
                        <p><b>Project Status: </b>{status}</p>
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