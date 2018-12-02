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
            <div className="row">
              <div className="col-md-2"></div>
              <div className="col-md-8" id="feed">
                <img src="/assets/gratelancer.png" className="img-responsive center-block" alt="gratelancer-logo" />
                <hr/>

                  {!isLoading ? (
                    projects.map(project => {
                        const { id, title, description, deadline, max_price, min_price,
                            status, client, freelancer} = project;
                      return (
                        <div key={id} className="project">
                            <p><b>{title}</b></p>
                            <img src="/assets/freelancer1.jpg" className="img-responsive center-block" alt="project-image" />

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
