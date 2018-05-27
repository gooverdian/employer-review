import React from 'react';
import List, { ListItem, ListItemText } from 'material-ui/List';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import EmployerReview from './EmployerReview';

class EmployerReviews extends React.Component {
    state = {
        data: {}
    };

    componentDidMount() {
        if (this.props.employerId) {
            ExchangeInterface.getReviews(this.props.employerId).then(
                (data) => {
                    this.setState({data: data});
                },
                function(error) {
                    console.log(error);
                }
            )
        }
    }

    render() {
        if (!this.state.data.reviews || this.state.data.reviews.length === 0) {
            return (
                <List>
                    <ListItem className="nothing-found">
                        <ListItemText primary="Отзывов по компании не найдено" />
                    </ListItem>
                </List>
            );
        }

        return (
            <List>
                {this.state.data.reviews.map((item, index) => (
                    <ListItem
                        key={index}
                    >
                        <EmployerReview
                            data={item}
                            highlight={Number(item.reviewId) === Number(this.props.reviewId)}
                        />
                    </ListItem>
                ))}
            </List>
        );
    }
}

export default EmployerReviews;
