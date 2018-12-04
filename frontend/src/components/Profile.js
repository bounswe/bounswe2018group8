/**
 * @author Mehmet Calim, Kübra Eryılmaz
 */
import React, {Component} from 'react';
import  { Redirect } from 'react-router-dom';
import './Profile.css';
import axios from 'axios';

export var currentUser ={
    userID : '',
};
export default class Profile extends Component {
    constructor() {
        super();
        this.state = {
            user: '',
            isLoading: true,
            errors: null
        }
      }
    // @mehmetcalim: Get API request is added in order to get current user data.
    componentDidMount() {
        axios.get("http://52.59.230.90/users/self/", {
            headers: {
                Authorization: `JWT ${localStorage.getItem('token')}`
            }
        })
            .then(res => {
                console.log(res);
                this.setState({
                    user: JSON.parse(localStorage.getItem('user')),
                    isLoading: false,
                    isUser: true,
                });
            })
            .catch(error => this.setState({ error, isLoading: false }));
    }
    render() {
        if(localStorage.getItem('token')== null){
            return <Redirect to='/' />
        }    
        else{
            const { isLoading, user } = this.state;
            currentUser.userID = user.pk;
            console.log(currentUser.userID);
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
            </React.Fragment>
            );
        }    
      }
}
