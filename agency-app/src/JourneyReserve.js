import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';

class JourneyReserve extends Component {

    emptyItem = {
        id: '',
        start: '',
        end: '',
        fromCity: '',
        toCity: '',
        availableCars: [],
        availableRooms: [],
        availableTickets: []
    };

    emptyPostItem = {
        idNumber: '',
        journeyId: '',
        name: '',
        numOfPersons: '',
        roomId: '',
        carId: '',
        ticketIds: []
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            postItem: this.emptyPostItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        console.log("Mount reserve");
        const detailedJourney = await (await fetch(`/api/agency/journey/${this.props.match.params.id}`)).json();
        console.log(detailedJourney);
        this.setState({item: detailedJourney});
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let postItem = {...this.state.postItem};
        postItem[name] = value;
        this.setState({postItem});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {postItem} = this.state;
        const journeyId = this.props.match.params.id;
        let ticketIds;
        if (postItem.ticketIds.length > 0) {
            console.log(postItem.ticketIds);
            ticketIds = postItem.ticketIds.split(",");
        } else {
            ticketIds = [];
        }
        postItem.ticketIds = ticketIds;
        postItem.journeyId = journeyId;

        console.log({postItem});

        await fetch('/api/agency/reservation', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postItem),})
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
        const {postItem} = this.state;

        const carsList  =  item.availableCars.map(car => {
            return <tr key={car.id}>
                <td>{car.id}</td>
                <td>{car.type}</td>
                <td>{car.seats}</td>
                <td>{car.bootSize}</td>
            </tr>
        });

        const roomsList = item.availableRooms.map(room => {
           return <tr key={room.id}>
            <td>{room.id}</td>
            <td>{room.city}</td>
            <td>{room.size}</td>
           </tr>
        });

        const ticketsList = item.availableTickets.map(ticket => {
            return <tr key={ticket.id}>
                <td>{ticket.id}</td>
                <td>{ticket.fromCity}</td>
                <td>{ticket.toCity}</td>
                <td>{ticket.start}</td>
            </tr>
        });

        return <div>
            <AppNavbar/>
            <Container>
                <h2>Journey Details</h2>
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
                    <tr>
                        <td>{item.fromCity}</td>
                        <td>{item.toCity}</td>
                        <td>{item.start}</td>
                        <td>{item.end}</td>
                    </tr>
                    </tbody>
                </Table>

                <h5>Cars</h5>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="20%">Id</th>
                        <th width="20%">Place</th>
                        <th width="20%">Size</th>
                    </tr>
                    </thead>
                    <tbody>
                    {carsList}
                    </tbody>
                </Table>

                <h5>Rooms</h5>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="20%">Id</th>
                        <th width="20%">Place</th>
                        <th width="20%">Size</th>
                    </tr>
                    </thead>
                    <tbody>
                    {roomsList}
                    </tbody>
                </Table>

                <br/>
                <h5>Plane Tickets</h5>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="20%">Id</th>
                        <th width="20%">From</th>
                        <th width="20%">To</th>
                        <th width="20%">Start</th>
                    </tr>
                    </thead>
                    <tbody>
                    {ticketsList}
                    </tbody>
                </Table>

                <h2>Reservation Form</h2>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value = {postItem.name || ''}
                               onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="idNumber">ID Card Number</Label>
                        <Input type="text" name="idNumber" id="idNumber" value = {postItem.idNumber || ''}
                               onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="numOfPersons">Number of Persons</Label>
                        <Input type="text" name="numOfPersons" id="numOfPersons" value = {postItem.numOfPersons || ''}
                               onChange={this.handleChange} placeholder="1"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="carId">Car ID</Label>
                        <Input type="text" name="carId" id="carId" value = {postItem.carId || ''}
                               onChange={this.handleChange} placeholder="1"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="roomId">Room ID</Label>
                        <Input type="text" name="roomId" id="roomId" value = {postItem.roomId || ''}
                               onChange={this.handleChange} placeholder="1"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="ticketIds">Ticket IDs</Label>
                        <Input type="text" name="ticketIds" id="ticketIds" value = {postItem.ticketIds || ''}
                               onChange={this.handleChange} placeholder="1,2,3"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Reserve</Button>
                        <Button color="secondary" tag={Link} to="/journeys">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
            </div>
    }
}

export default withRouter(JourneyReserve);