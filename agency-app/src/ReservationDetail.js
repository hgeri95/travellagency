import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label, Table} from 'reactstrap';
import AppNavbar from './AppNavbar';

class ReservationDetail extends Component {

    emptyReservation = {
        id: '',
        name: '',
        idNumber: '',
        journey: '',
        numOfPersons: '',
        carReservation: '',
        roomReservation: '',
        tickets: [],

    };

    constructor(props) {
        super(props);
        this.state = {
            reservation: this.emptyReservation
        }
    }

    async componentDidMount() {
        console.log("Mount reservation detail");
        const detailedReservation = await (await fetch(`/api/agency/reservation/${this.props.match.params.id}`)).json();
        console.log(detailedReservation);
        this.setState({reservation: detailedReservation});
    }

    render() {
        const {reservation} = this.state;

        return <div>
            <AppNavbar/>
            <Container>
                <h2>Reservation details</h2>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="5%">Name</th>
                        <th width="5%">ID Number</th>
                        <th width="5%">Number of Persons</th>
                        <th width="5%">Journey</th>
                        <th width="5%">Car Reservation</th>
                        <th width="5%">Room Reservation</th>
                        <th width="5%">Ticket Reservations</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>{reservation.name}</td>
                        <td>{reservation.idNumber}</td>
                        <td>{reservation.numOfPersons}</td>
                        <td>{JSON.stringify(reservation.journey)}</td>
                        <td>{JSON.stringify(reservation.carReservation)}</td>
                        <td>{JSON.stringify(reservation.roomReservation)}</td>
                        <td>{JSON.stringify(reservation.tickets)}</td>
                    </tr>
                    </tbody>
                </Table>
                <Button color="secondary" tag={Link} to="/reservations">Cancel</Button>
            </Container>
        </div>
    }
}

export default withRouter(ReservationDetail);