import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class JourneyList extends Component {

    constructor(props) {
        super(props);
        this.state = {journeys: [], isLoading: true};
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('/api/agency/journey/all')
            .then(response => response.json())
            .then(data => this.setState({journeys: data, isLoading: false}));
    }

    render() {
        const {journeys, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>
        }

        const journeyList = journeys.map(journey => {
            return <tr key={journey.id}>
                <td style={{whiteSpace: 'nowrap'}}>{journey.fromCity}</td>
                <td>{journey.toCity}</td>
                <td>{journey.start}</td>
                <td>{journey.end}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/journeys/reserve/" + journey.id}>Reserve</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                 <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/journeys/new">Add Journey</Button>
                    </div>
                    <h3>Journeys</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">From</th>
                            <th width="20%">To</th>
                            <th width="20%">Start</th>
                            <th width="20%">End</th>
                        </tr>
                        </thead>
                        <tbody>
                        {journeyList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );


    }
}

export default JourneyList;