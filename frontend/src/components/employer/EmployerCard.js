import React from 'react';
import {Grid} from 'react-flexbox-grid';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import {Button} from 'components/router-button/RouterButton';

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
            <Grid>
                <h1>
                    <Button
                        className="pull-right"
                        raised primary
                        href={"/review/add/" + this.state.employer.id}
                        label="Оставить отзыв о компании"
                    />
                    {this.state.employer.name}
                </h1>
            </Grid>
        );
    }
}

export default EmployerCard;
