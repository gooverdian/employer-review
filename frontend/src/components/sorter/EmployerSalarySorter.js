import React from 'react';
import { connect } from 'react-redux';
import { getSalaryByProfFields } from 'modules/employerStatistics';
import Sorter from './Sorter';

class EmployerSalarySorter extends React.Component {
    state = {};

    static getDerivedStateFromProps(nextProps) {
        if (typeof nextProps.items === 'undefined' || nextProps.stateEmployerId !== Number(nextProps.employerId)) {
            nextProps.getSalaryByProfFields(nextProps.employerId);
            return null;
        }

        return { items: nextProps.items };
    }

    render() {
        return (
            <Sorter
                valueSuffix=" руб."
                items={this.state.items}
            />
        );
    }
}

const mapStateToProps = (state) => ({
    items: state.employerStatistics.salary,
    stateEmployerId: state.employerStatistics.salaryEmployerId,
});

export default connect(mapStateToProps, { getSalaryByProfFields })(EmployerSalarySorter);
