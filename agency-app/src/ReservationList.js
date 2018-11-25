import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class ReservationList extends Component {

    constructor(props) {
        super(props);
        this.state = {reservations:[], isLoading: true};
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('/api/agency/reservation/all')
            .then(response => response.json())
            .then(data => this.setState({reservations: data, isLoading: false}));
    }

    render() {
        const {reservations, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>
        }

        const reservationList = reservations.map(reservation => {
            return <tr key={reservation.id}>
                <td>{reservation.name}</td>
                <td>{reservation.idNumber}</td>
                <td>{reservation.carReservationId}</td>
                <td>{reservation.roomReservationId}</td>
                <td>{reservation.ticketReservationIds}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/reservations/detail/"+reservation.id}>Details</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <h3>Reservations</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">ID Number</th>
                            <th width="20%">Car Reservation ID</th>
                            <th width="20%">Room Reservation ID</th>
                            <th width="20%">Ticket Reservation IDs</th>
                        </tr>
                        </thead>
                        <tbody>
                        {reservationList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default ReservationList;