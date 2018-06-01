import React from 'react';
import { connect } from 'react-redux';
import { getReviewsByProfFields } from 'modules/employerStatistics';
import Sorter from './Sorter';

class EmployerReviewsSorter extends React.Component {
    state = {};

    static getDerivedStateFromProps(nextProps) {
        if (typeof nextProps.items === 'undefined' || nextProps.stateEmployerId !== Number(nextProps.employerId)) {
            nextProps.getReviewsByProfFields(nextProps.employerId);
            return null;
        }

        return { items: nextProps.items };
    }

    render() {
        return (
            <Sorter
                minValue={0}
                items={this.state.items}
            />
        );
    }
}

const mapStateToProps = (state) => ({
    items: state.employerStatistics.reviews,
    stateEmployerId: state.employerStatistics.reviewsEmployerId,
});

export default connect(mapStateToProps, { getReviewsByProfFields })(EmployerReviewsSorter);
