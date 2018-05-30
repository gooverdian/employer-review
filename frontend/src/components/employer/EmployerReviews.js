import React from 'react';
import List, { ListItem, ListItemText } from 'material-ui/List';
import EmployerReview from './EmployerReview';
import { getEmployerReviews } from 'modules/employerReviews';
import { connect } from 'react-redux';


class EmployerReviews extends React.Component {
    state = {
        data: {}
    };

    static getDerivedStateFromProps(nextProps) {
        console.log(nextProps);
        if (!nextProps.data) {
            nextProps.getEmployerReviews(nextProps.employerId, nextProps.reviewType);
            return null;
        }

        return { data: nextProps.data };
    }

    render () {
        const { reviews } = this.state.data;
        if (!reviews || reviews.length === 0) {
            return (
                <List>
                    <ListItem className="nothing-found">
                        <ListItemText primary="Отзывов по компании не найдено" />
                    </ListItem>
                </List>
            );
        }

        return (
            <div>
                {reviews.map((item, index) => {
                    return (
                        <EmployerReview
                            key={index}
                            data={item}
                            highlight={Number(item.reviewId) === Number(this.props.reviewId)}
                        />
                    )})
                }
            </div>
        );
    }
}

const mapStateToProps = (state, ownProps) => ({ data: state.employerReviews[ownProps.reviewType] });

export default connect(mapStateToProps, { getEmployerReviews })(EmployerReviews);
