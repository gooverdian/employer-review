import settings from 'config/settings';
import ExchangeRequest from './ExchangeRequest';

const ExchangeInterface = {
    employerSearch: function (text, page = 0, perPage = settings.defaultPageSize) {
        return new ExchangeRequest({
            url: settings.apiUrls.employer,
            params: {
                text: text,
                page: page,
                per_page: perPage,
            }
        }).perform();
    },
    areaSearch: function (text, page = 0, perPage = settings.defaultPageSize) {
        return new ExchangeRequest({
            url: settings.apiUrls.area,
            params: {
                text: text,
                page: page,
                per_page: perPage,
            }
        }).perform();
    },
    getReviews: function (employerId, page = 0, perPage = settings.defaultPageSize) {
        return new ExchangeRequest({
            url: settings.apiUrls.review,
            params: {
                employer_id: employerId,
                page: page,
                per_page: perPage,
            }
        }).perform();
    },
    getEmployer: function (employerId) {
        return new ExchangeRequest({
            url: settings.apiUrls.employer + employerId
        }).perform();
    },
    getArea: function (areaId) {
        return new ExchangeRequest({
            url: settings.apiUrls.area + areaId
        }).perform();
    },
    addReview: function (reviewFormData) {
        return new ExchangeRequest({
            method: 'post',
            url: settings.apiUrls.review,
            params: reviewFormData
        }).perform();
    },
    addEmployer: function (employerFormData) {
        return new ExchangeRequest({
            method: 'post',
            url: settings.apiUrls.employer,
            params: employerFormData
        }).perform();
    }
};

export default ExchangeInterface;
