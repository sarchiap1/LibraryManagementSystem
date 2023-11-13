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

    async login(loginRequest) { 
        let partialUrl = "/api/login"; 
        console.log("AuthService", loginRequest);
        const response = await apiClient.post(partialUrl, loginRequest, {
            headers: {
            'Content-Type': 'application/json'
            }
        }); 
        console.log("AuthService", response.data.token);
        apiClient.defaults.headers.common["Authorization"] = "Bearer " + response.data.token;
    },

    async refresh(refreshRequest) { 
        let partialUrl = "/api/refresh"; 
        const response = await apiClient.post(partialUrl, refreshRequest,{ withCredentials: true }); 

        apiClient.defaults.headers.common["Authorization"] = "Bearer " + response.data.token;
    }

}