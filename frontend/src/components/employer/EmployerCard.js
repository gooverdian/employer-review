import React from 'react';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import Button from 'material-ui/Button';
import { Link } from 'react-router-dom';

class EmployerCard extends React.Component {
    state = {
        employer: {}
    };

    constructor(params) {
        super();
        if (params.employerId) {
            let instance = this;
            ExchangeInterface.getEmployer(params.employerId).then(function(data) {
                instance.setState({employer: data});
            }, function(error) {
                console.log(error);
            });
        }
    }

    render () {
        return (
            <h1>
                <Button
                    variant="raised"
                    color="primary"
                    className="pull-right"
                    component={Link}
                    to={"/review/add/" + this.state.employer.id}
                >
                    {"Оставить отзыв о компании"}
                </Button>
                {this.state.employer.name}
            </h1>
        );
    }
}

export default EmployerCard;
