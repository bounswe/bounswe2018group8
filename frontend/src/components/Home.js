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
            search: "",
        }

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }
    // mehmetcalim: this function send get reqeust to get all projects to homepage.

    componentDidMount() {
        axios.get("http://52.59.230.90/projects/")
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

    // kubraeryılmaz: updates search value.
    updateSearch(event){
        this.setState({search: event.target.value});
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

    //kubraeryılmaz: searching is done by filtering while mapping.
    render() {
        const { isLoading, projects } = this.state;
        var project_id;
        var clientname;
        var url;
        var url2;
        // <div className="row">
        //     <div className="col-md-12">
        //         <Button bsStyle="primary" href="/project">
        //             Create a project
        //         </Button>
        //         <input className="pull-right"
        //                type="text"
        //                placeholder="Search..."
        //                value = {this.state.search}
        //                onChange={this.updateSearch.bind(this)}/>
        //     </div>
        // </div>
        return (
          <React.Fragment>
            <div className="row">
                <div className="col-md-3"></div>
                <div className="col-md-6" id="feed">
                    {!isLoading ? (
                        projects.filter(project => project.title.toLowerCase().includes(this.state.search.toLowerCase())).map(project => {
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
                                <p><b>Owner: </b><Link to={url}>{clientname}</Link></p>
                                <p><b>Description: </b>{description}</p>
                                <p><b>Deadline: </b>{deadline.toString().substr(0,10) + " " + deadline.toString().substr(11,5)}</p>
                                <p><b>Price: </b>{min_price} - {max_price} &#8378;</p>
                                <p><b>Status: </b>{status}</p>
                                <p className="text-center">
                                    <Button bsStyle="default" href={url2}>
                                        See details
                                    </Button>
                                </p>
                            </div>

                        );
                        })
                    ) : (
                        <p>Loading...</p>
                    )}

                    <img src="/assets/gratelancer-black.png" className="img-responsive center-block logo" alt="gratelancer-logo" />
                </div>
                <div className="col-md-3"></div>
            </div>
            </React.Fragment>
        );
    }
}
