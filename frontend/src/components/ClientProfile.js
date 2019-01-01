/**
 * @author Mehmet Calim
 */
import React, {Component} from 'react';
import  { Redirect } from 'react-router-dom';
import './Profile.css';
import axios from 'axios';

export default class ClientProfile extends Component {
    constructor() {
        super();
        this.state = {
            user: '',
            isLoading: true,
            errors: null,
            user_id: '',
        }
    }
    // @mehmetcalim: Get API request is added in order to get current user data.
    componentDidMount() {
        if(localStorage.getItem('token')!= null){
            this.user_id = this.props.match.params.id;
            axios.get("http://52.59.230.90/users/"+ this.user_id , {
                headers: {
                    Authorization: `JWT ${localStorage.getItem('token')}`
                }
            })
                .then(res => {
                    this.setState({
                        user: res.data,
                        isLoading: false,
                    });
                })
                .catch(error => this.setState({ error, isLoading: false }));
        }
    }
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
                                <p><b>Projects: </b>{user.projects}</p>
                                <p><b>Skills: </b>{user.skills}</p>
                                <hr/>
                            </div>

                        ) : (
                        <p>Loading...</p>
                    )}

                        </div>
                    </div>
                    <div className="col-md-4"></div>
                </div>
                <div className="fill"></div>
            </React.Fragment>
            );
        }
      }
}
