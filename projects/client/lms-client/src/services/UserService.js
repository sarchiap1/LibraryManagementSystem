import axios from 'axios'
import rC from './runtimeConfig.js';

let configParamName = "VUE_APP_API_BASE_URL";
console.log("Initialize service with url:" + rC.runtimeConfig(configParamName));

const apiClient = axios.create({
    baseURL: rC.runtimeConfig(configParamName),
    withCredentials: true,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
})

export default {

    async user() { 
        let partialUrl = "/api/user"; 
        const response = await apiClient.post(partialUrl,null, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        console.log("UserService", response);
    },

}