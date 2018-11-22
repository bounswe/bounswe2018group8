import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import { Jumbotron, Grid, Row, Col, Image, Button } from 'react-bootstrap';
import './Home.css';

export default class Home extends Component {
    render(){
        return(
            <Grid>
                <h2>Hello, This is Gratelancer</h2>
                <p>Projects will come here</p>
            </Grid>
        )
    }
}