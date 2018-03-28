import axios from 'axios';
import settings from 'config/settings';

class RestExchange {
    constructor(request) {
        for (let key in request) {
            if (request.hasOwnProperty(key)) {
                this.request[key] = request[key];
            }
        }
    }

    request = {
        method: 'get',
        url: '/',
        params: {}
    };

    methods = {
        get: function(url, params, resolve, reject) {
            axios.request({
                url: url,
                method: 'get',
                params: params
            }).then(function(response) {
                resolve(response.data);
            }, function(error) {
                reject(error.response);
            });
        },
        post: function(url, data, resolve, reject) {
            axios.request({
                url: url,
                method: 'post',
                data: data
            }).then(function(response) {
                resolve(response.data);
            }, function(error) {
                reject(error.response);
            });
        }
    };

    perform() {
        let instance = this;
        return new Promise(function(resolve, reject) {
            if (instance.methods[instance.request.method]) {
                instance.methods[instance.request.method](
                    instance.request.url,
                    instance.request.params,
                    resolve,
                    reject
                )
            } else {
                reject('Non existent method');
            }
        });
    };

}

const ExchangeInterface = {
    employerSearch: function (text, page = 0, perPage = settings.defaultPageSize) {
        return new RestExchange({
            url: settings.apiUrls.employer,
            params: {
                text: text,
                page: page,
                per_page: perPage,
            }
        }).perform();
    },
    getEmployer: function (employerId) {
        return new RestExchange({
            url: settings.apiUrls.employer + employerId
        }).perform();
    },
    addReview: function (reviewFormData) {
        console.log(reviewFormData);
        return new RestExchange({
            method: 'post',
            url: settings.apiUrls.review,
            params: reviewFormData
        }).perform();
    }
};

export default ExchangeInterface;
