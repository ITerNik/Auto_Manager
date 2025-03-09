import axios from 'axios';

axios.defaults.baseURL = 'https://localhost:8080'

export class APIService {
    private token: string;

    constructor() {
        this.token = localStorage.getItem('token');
    }

    private getAuthHeaders() {
        return {
            Authorization: `Bearer ${this.token}`
        }
    }

    async getUserInfo() {
        return axios.get('/profiles/', {
            headers: this.getAuthHeaders()
        });
    }
}