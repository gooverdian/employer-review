import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import { withRouter } from 'react-router-dom';
import EmployerReviews from "./EmployerReviews";
import './EmployerTabs.css';

class EmployerTabs extends React.Component {
    state = {
        activeTabIndex: 0
    };

    handleTabChange = (event, value) => {
        this.setState({ activeTabIndex: value });
    };

    render() {
        console.log(this.props.match);
        const { activeTabIndex } = this.state;
        return (
            <div>
                <AppBar position="static" color="default">
                    <Tabs value={activeTabIndex} onChange={this.handleTabChange}>
                        <Tab label="Отзывы сотрудников" />
                        <Tab label="Отзывы об интервью" />
                        <Tab label="Какой-то таб" />
                    </Tabs>
                </AppBar>
                <div className="tab-content">
                    {activeTabIndex === 0 && <EmployerReviews
                        employerId={this.props.match.params.employerId}
                        reviewId={this.props.match.params.reviewId}
                    />}
                    {activeTabIndex === 1 && "Отзывы об интервью"}
                    {activeTabIndex === 2 && ""}
                </div>
            </div>
        );
    }
}

export default withRouter(EmployerTabs);