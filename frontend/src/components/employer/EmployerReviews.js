import React from 'react';
import List, { ListItem } from 'material-ui/List';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import EmployerReview from './EmployerReview';

class EmployerReviews extends React.Component {
    state = {
        data: {}
    };

    constructor(params) {
        super();
        this.state.data = params.data || {};
    }

    componentDidMount() {
        if (this.props.employerId) {
            let instance = this;
            ExchangeInterface.getReviews(this.props.employerId).then(
                function(data) {
                    instance.setState({data: data});
                },
                function(error) {
                    console.log(error);
                }
            )
        }
    }

    generateResultsList() {
        if (!this.state.data.reviews) {
            return '';
        }

        if (this.state.data.reviews.length === 0) {
            return (
                <List>
                    <ListItem
                        className="nothing-found"
                        caption="Отзывов по компании не найдено"
                    />
                </List>
            );
        }

        return (
            <List ripple>
                {this.state.data.reviews.map((item, index) => (
                    <ListItem
                        key={index}
                        itemContent={<EmployerReview
                            data={item}
                            highlight={String(item.reviewId) === String(this.props.reviewId)}
                        />}
                    />
                ))}
            </List>
        );
    }

    render() {
        return (
            <div>
                <h3>Отзывы о компании</h3>
                {this.generateResultsList()}
            </div>
        );
    }
}

export default EmployerReviews;
