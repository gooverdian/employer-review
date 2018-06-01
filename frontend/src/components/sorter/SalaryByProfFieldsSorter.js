import React from 'react';
import { connect } from 'react-redux';
import { getSalaryByProfFields } from 'modules/mainStatistics';
import Sorter from './Sorter';

class SalaryByProfFieldsSorter extends React.Component {
    state = {};

    static getDerivedStateFromProps(nextProps) {
        if (typeof nextProps.items === 'undefined') {
            nextProps.getSalaryByProfFields();
            return null;
        }

        return { items: nextProps.items };
    }

    render() {
        return (
            <Sorter
                maxItemCount={15}
                valueSuffix=" руб."
                items={this.state.items}
            />
        );
    }
}

const mapStateToProps = (state) => ({ items: state.mainStatistics.salaryByProfFields });

export default connect(mapStateToProps, { getSalaryByProfFields })(SalaryByProfFieldsSorter);
