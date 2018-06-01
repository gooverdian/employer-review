import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import { withRouter } from 'react-router-dom';
import EmployerReviews from './EmployerReviews';
import settings from 'config/settings';
import EmployerSalarySorter from 'components/sorter/EmployerSalarySorter';
import EmployerDurationSorter from 'components/sorter/EmployerDurationSorter';
import EmployerReviewsSorter from 'components/sorter/EmployerReviewsSorter';
import Typography from 'material-ui/Typography';
import './EmployerTabs.css';

export const reviewTypesUrls = {
    [settings.reviewTypes.employee]: 'employee-reviews',
    [settings.reviewTypes.interviewee]: 'interviewee-reviews',
};

const tabIndexByUrl = {
    'employee-reviews': 0,
    'interviewee-reviews': 1,
};

class EmployerTabs extends React.Component {
    state = {
        activeTabIndex: 0
    };

    constructor(props) {
        super(props);
        if (props.match.params.reviewTypeUrl && tabIndexByUrl[props.match.params.reviewTypeUrl]) {
            this.state = {
                activeTabIndex: tabIndexByUrl[props.match.params.reviewTypeUrl]
            };
        }
    }

    handleTabChange = (event, value) => {
        this.setState({ activeTabIndex: value });
    };

    render() {
        const { activeTabIndex } = this.state;
        return (
            <div>
                <AppBar position="static" color="default">
                    <Tabs
                        value={activeTabIndex}
                        onChange={this.handleTabChange}
                        scrollable
                        scrollButtons="on"
                    >
                        <Tab label="Отзывы сотрудников" />
                        <Tab label="Отзывы об интервью" />
                        <Tab label="Заработная плата" />
                        <Tab label="Кадры" />
                        <Tab label="Количество отзывов" />
                    </Tabs>
                </AppBar>
                <div className="tab-content">
                    {activeTabIndex === 0 && <EmployerReviews
                        reviewType={settings.reviewTypes.employee}
                        employerId={this.props.match.params.employerId}
                        reviewId={this.props.match.params.reviewId}
                    />}
                    {activeTabIndex === 1 && <EmployerReviews
                        reviewType={settings.reviewTypes.interviewee}
                        employerId={this.props.match.params.employerId}
                        reviewId={this.props.match.params.reviewId}
                    />}
                    {activeTabIndex === 2 && <div>
                        <Typography variant="title">
                            Средние зарплаты по профессиональным областям
                        </Typography>
                        <EmployerSalarySorter
                            employerId={this.props.match.params.employerId}
                        />
                    </div>}
                    {activeTabIndex === 3 && <div>
                        <Typography variant="title">
                            Среднее время работы в компании по профобластям
                        </Typography>
                        <EmployerDurationSorter
                            employerId={this.props.match.params.employerId}
                        />
                    </div>}
                    {activeTabIndex === 4 && <div>
                        <Typography variant="title">
                            Количество отзывов, оставленное представителями разных профобластей
                        </Typography>
                        <EmployerReviewsSorter
                            employerId={this.props.match.params.employerId}
                        />
                    </div>}
                </div>
            </div>
        );
    }
}

export default withRouter(EmployerTabs);
