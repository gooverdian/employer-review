import React from 'react';
import { connect } from 'react-redux';
import { getDurationByProfFields } from 'modules/employerStatistics';
import Sorter from './Sorter';

class EmployerDurationSorter extends React.Component {
    state = {};

    static getDerivedStateFromProps(nextProps) {
        if (typeof nextProps.items === 'undefined' || nextProps.stateEmployerId !== Number(nextProps.employerId)) {
            nextProps.getDurationByProfFields(nextProps.employerId);
            return null;
        }

        return { items: nextProps.items };
    }

    render() {
        return (
            <Sorter
                valueSuffix=" мес."
                items={this.state.items}
            />
        );
    }
}

const mapStateToProps = (state) => ({
    items: state.employerStatistics.duration,
    stateEmployerId: state.employerStatistics.durationEmployerId,
});

export default connect(mapStateToProps, { getDurationByProfFields })(EmployerDurationSorter);
