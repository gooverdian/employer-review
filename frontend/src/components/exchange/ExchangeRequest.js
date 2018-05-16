import axios from 'axios';

class ExchangeRequest {
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

export default ExchangeRequest;
