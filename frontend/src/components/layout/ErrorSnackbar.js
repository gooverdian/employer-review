import React from 'react';
import Snackbar from 'material-ui/Snackbar';
import { connect } from 'react-redux'
import { withRouter } from 'react-router-dom'
import { resetErrorMessage } from 'modules/errorMessage'
import IconButton from 'material-ui/IconButton';
import ErrorIcon from '@material-ui/icons/Error';
import CloseIcon from '@material-ui/icons/Close';
import './ErrorSnackbar.css';


class ErrorSnackbar extends React.Component {
    state = {
        open: false,
        messageInfo: {},
        queue: [],
    };


    static getDerivedStateFromProps(nextProps, prevState) {
        console.log('handling');
        if (nextProps.errorMessage) {
            const newMessageInfo = {
                message: nextProps.errorMessage,
                key: new Date().getTime(),
            };

            if (prevState.open) {
                prevState.queue.push(newMessageInfo);
                return { open: false };
            } else {
                return {
                    messageInfo: newMessageInfo,
                    open: true,
                }
            }
        }

        return null;
    }

    processQueue = () => {
        if (this.state.queue.length > 0) {
            this.setState({
                messageInfo: this.state.queue.shift(),
                open: true,
            });
        }
    };

    handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        this.props.resetErrorMessage();
        this.setState({ open: false });
    };

    handleExited = () => {
        this.processQueue();
    };

    render() {
        return (
            <Snackbar
                key={this.state.messageInfo.key}
                anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
                open={this.state.open}
                autoHideDuration={10000}
                onClose={this.handleClose}
                onExited={this.handleExited}
                message={<div className="error-bar">
                    <ErrorIcon className="error-bar__icon" />
                    <span className="error-bar__text">{this.state.messageInfo.message}</span>
                </div>}
                action={[
                    <IconButton
                        key="close"
                        color="inherit"
                        onClick={this.handleClose}
                    >
                        <CloseIcon />
                    </IconButton>,
                ]}
            />
        );
    }
}

const mapStateToProps = (state) => ({
    errorMessage: state.errorMessage,
});

export default withRouter(connect(mapStateToProps, {resetErrorMessage})(ErrorSnackbar));
