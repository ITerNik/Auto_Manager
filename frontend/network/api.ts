import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080'

export class APIService {
    private token: string;

    constructor() {
        this.token = localStorage.getItem('token') ?? '';
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

    async editUserInfo(id: string, body: any) {
        return axios.patch(`/profiles/${id}`, body,{
            headers: this.getAuthHeaders()
        })
    }

    async authenticate(type: string, body: any) {
        const res = await axios.post(`auth/${type}`, body, {
            headers: this.getAuthHeaders()
        })

        const { token } = res.data;

        localStorage.setItem('token', token);
    }
}