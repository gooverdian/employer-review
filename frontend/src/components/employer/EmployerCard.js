import React from 'react';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import Button from 'material-ui/Button';
import { Link } from 'react-router-dom';
import './EmployerCard.css';

class EmployerCard extends React.Component {
    state = {
        employer: {}
    };

    componentDidMount() {
        if (this.props.employerId) {
            ExchangeInterface.getEmployer(this.props.employerId).then(
                (data) => {
                    this.setState({employer: data});
                },
                function(error) {
                    console.log(error);
                }
            )
        }
    }

    render () {
        return (
            <h1 className="section-title">
                <div className="section-title__controls">
                    <Button
                        variant="raised"
                        color="primary"
                        component={Link}
                        to={"/review/add/" + this.state.employer.id}
                    >
                        Оставить отзыв о компании
                    </Button>
                </div>
                {this.state.employer.name}
            </h1>
        );
    }
}

export default EmployerCard;
