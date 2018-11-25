import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class  JourneyAdd extends Component {

    emptyItem = {
        fromCity: '',
        toCity: '',
        start: '',
        end: ''
    };

    constructor(props) {
        super(props);
        this.state = {item: this.emptyItem};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/api/agency/journey', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),})
            .then(response => {
                if (!response.ok) {
                   throw Error(response.statusText);
                } else {
                    this.props.history.push('/journeys')
                }
            })
            .catch(error => alert(error));
    };

    render() {
        const {item} = this.state;

        return <div>
            <AppNavbar/>
            <Container>
                <h2>Add Journey</h2>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="fromCity">From</Label>
                        <Input type="text" name="fromCity" id="fromCity" value = {item.fromCity || ''}
                            onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="toCity">To</Label>
                        <Input type="text" name="toCity" id="toCity" value = {item.toCity || ''}
                               onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="start">Start</Label>
                        <Input type="text" name="start" id="start" value = {item.start || ''}
                               onChange={this.handleChange} placeholder="2018-01-01"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="end">End</Label>
                        <Input type="text" name="end" id="end" value = {item.end || ''}
                               onChange={this.handleChange} placeholder="2018-01-02"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>
                        <Button color="secondary" tag={Link} to="/journeys">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }


}

export default withRouter(JourneyAdd);