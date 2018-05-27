import React from 'react';
import List, { ListItem, ListItemText } from 'material-ui/List';
import { Link } from 'react-router-dom';
import Typography from 'material-ui/Typography';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import EmployerAvatar from 'components/employer/EmployerAvatar';
import { getTopEmployers } from 'modules/topEmployers';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import './TopEmployers.css';

class TopEmployers extends React.Component {
    state = {};

    static getDerivedStateFromProps(nextProps) {
        if (!nextProps.items || !nextProps.items.length) {
            nextProps.getTopEmployers();
        }

        return null;
    }

    render() {
        if (!this.props.items || this.props.items.length === 0) {
            return null;
        }

        return (
            <div>
                <Typography variant="title">
                    Лучшие компании
                </Typography>
                <List>
                    {this.props.items.map((item) => (
                        <ListItem
                            key={item.id}
                            button
                            component={Link}
                            to={"/employer/" + item.id}
                        >
                            <ListItemText primary={item.name} secondary={item.areaName} />
                            <EmployerAvatar src={item.logoUrl} />
                            {
                                item.rating ? (
                                    <Typography className="rating-plate" variant="display1" gutterBottom={false}>
                                        <span className="rating-plate__rating">
                                            <StarBorderIcon classes={{root: 'rating-plate__rating-icon'}}/>
                                            {item.rating.toFixed(1)}
                                        </span>
                                        <span className="rating-plate__count">
                                            {item.peopleRated}
                                        </span>
                                    </Typography>
                                ) : ''
                            }
                        </ListItem>
                    ))}
                </List>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        items: state.topEmployers.items
    }
};

export default withRouter(connect(mapStateToProps, { getTopEmployers })(TopEmployers));
