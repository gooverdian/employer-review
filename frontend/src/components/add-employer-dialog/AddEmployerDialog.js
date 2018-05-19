import React from 'react';
import TextField from 'material-ui/TextField';
import SearchSelect from 'components/search-select/SearchSelect';
import Button from 'material-ui/Button';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import settings from 'config/settings';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Validator from 'helpers/validator/Validator';
import './AddEmployerDialog.css';

class AddEmployerDialog extends React.Component {
    state = {
        open: false,
        attributes: {
            name: {
                value: '',
                valid: undefined,
                error: undefined
            },
            area_id: {
                value: null,
                valid: undefined,
                error: undefined
            },
            url: {
                value: '',
                valid: undefined,
                error: undefined
            },
        }
    };

    validationRules = {
        name: [
            {rule: 'required', message: 'Необходимо указать название'}
        ],
        area_id: [
            {rule: 'required', message: 'Необходимо выбрать регион'}
        ]
    };

    show = (employerName) => {
        let newState = { open: true };
        if (employerName) {
            newState.attributes = {
                ...this.state.attributes,
                name: {
                    value: employerName,
                    valid: undefined,
                    error: undefined
                }
            }
        }
        this.setState(newState);
    };

    handleClose = (event, reason) => {
        if (reason === 'backdropClick') {
            return;
        }

        this.setState({open: false});
    };

    handleTextFieldChange = (event) => {
        const { name, value } = event.target;
        this.updateAttribute(name, value);
    };

    updateAttribute = (name, value) => {
        this.setState({
            attributes: {
                ...this.state.attributes,
                [name]: Validator.validateAttribute(name, value, this.validationRules)
            }
        });
    };

    handleSubmission = (event) => {
        event.preventDefault();

        let { valid, attributes, } = Validator.validateForm(this.state.attributes, this.validationRules);
        this.setState({attributes: attributes});

        if(!valid) {
            return;
        }

        let formData = Validator.getFormData(attributes);
        ExchangeInterface.addEmployer(formData).then((data) => {
            this.props.onEmployerCreated(data);
            this.handleClose();
        }, (error) => {
            console.log(error);
        });
    };

    getSelectSearchResults = (search, success, failure) => {
        ExchangeInterface.areaSearch(search, 0, settings.selectPageSize).then(
            (data) => success(data),
            (error) => failure(error)
        );
    };

    render() {
        return (
            <div className="dialog">
                <Dialog
                    classes={{paper: "dialog__paper"}}
                    open={this.state.open}
                    onClose={this.handleClose}
                >
                    <DialogTitle>Новая компания</DialogTitle>
                    <DialogContent className="dialog__content">
                        <DialogContentText>
                            Для добавления компании, укажите информацию о ней.
                        </DialogContentText>
                        <form onSubmit={this.handleSubmission}>
                            <div className="form-group">
                                <TextField
                                    fullWidth
                                    label="Название компании"
                                    maxLength={100}
                                    name="name"
                                    value={this.state.attributes.name.value}
                                    onChange={this.handleTextFieldChange}
                                    error={Boolean(this.state.attributes.name.error)}
                                    helperText={this.state.attributes.name.error}
                                />
                            </div>
                            <div className="form-group">
                                <SearchSelect
                                    label="Выберите регион"
                                    getSearchResults={this.getSelectSearchResults}
                                    onChange={this.updateAttribute.bind(this, 'area_id')}
                                    error={this.state.attributes.area_id.error}
                                />
                            </div>
                            <div className="form-group">
                                <TextField
                                    fullWidth
                                    label="Ссылка на сайт компании"
                                    maxLength={100}
                                    name="url"
                                    value={this.state.attributes.url.value}
                                    onChange={this.handleTextFieldChange}
                                    error={Boolean(this.state.attributes.url.error)}
                                    helperText={this.state.attributes.url.error}
                                />
                            </div>
                        </form>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            Отмена
                        </Button>
                        <Button onClick={this.handleSubmission} color="primary">
                            Сохранить
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    };
}

export default AddEmployerDialog;
