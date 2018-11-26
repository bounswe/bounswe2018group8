import React, {Component} from 'react';
import { Grid } from 'react-bootstrap';
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