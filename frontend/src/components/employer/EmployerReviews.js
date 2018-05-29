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

        console.log(this.props.reviewId);
        return (
            <div>
                {this.state.data.reviews.map((item, index) => {
                    console.log(item.review_id);
                    return (
                        <EmployerReview
                            key={index}
                            data={item}
                            highlight={Number(item.review_id) === Number(this.props.reviewId)}
                        />
                    )})
                }
            </div>
        );
    }
}

export default EmployerReviews;
