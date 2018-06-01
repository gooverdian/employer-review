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
    getTopEmployers: function(size = settings.defaultTopSize) {
        return new ExchangeRequest({
            url: settings.apiUrls.bestEmployers,
            params: {
                size: size,
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
    getGeneralStatistics: function() {
        return new ExchangeRequest({
            url: settings.apiUrls.generalStatistics,
        }).perform();
    },
    getSalaryByProfFields: function(employerId = undefined) {
        return new ExchangeRequest({
            url: settings.apiUrls.salaryByProfFields + (employerId ? employerId : ''),
        }).perform();
    },
    getEmploymentDurationByProfField: function(employerId = undefined) {
        return new ExchangeRequest({
            url: settings.apiUrls.employmentDurationByProfField + (employerId ? employerId : ''),
        }).perform();
    },
    getReviews: function (employerId, reviewType = undefined, page = 0, perPage = settings.defaultPageSize) {
        return new ExchangeRequest({
            url: settings.apiUrls.review,
            params: {
                review_type: reviewType,
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
