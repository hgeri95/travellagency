import React, {Component} from 'react';
import './App.css';
import Home from './Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import JourneyList from './JourneyList';
import JourneyAdd from './JourneyAdd';
import JourneyReserve from './JourneyReserve';
import ReservationList from './ReservationList';
import ReservationDetail from './ReservationDetail';

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/journeys' exact={true} component={JourneyList}/>
                    <Route path='/journeys/new' exact={true} component={JourneyAdd}/>
                    <Route path='/journeys/reserve/:id' component={JourneyReserve}/>
                    <Route path='/reservations/detail/:id' component={ReservationDetail}/>
                    <Route path='/reservations' exact={true} component={ReservationList}/>
                </Switch>
            </Router>
        );
    }
}

export default App;
