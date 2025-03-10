import axios from 'axios';
import {CarFormData} from "../src/pages/car/CarForm.tsx";

axios.defaults.baseURL = 'http://localhost:8080'

const DEFAULT_COVER = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?ixid=M3w3MjA0OTZ8MHwxfHNlYXJjaHw2fHxjYXJ8ZW58MHx8fHwxNzQxNTcyMTYyfDA&ixlib=rb-4.0.3";

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

    async getCover(query: string) {
        return axios.get(`https://api.unsplash.com/search/photos?query=${query}&page=1`, {
            headers: {
                Authorization: 'Client-ID 0p2CCvyo-22g4o7-YKXssFlbn-eeNzY0umhDogagXbM77'
            }
        })
            .then((res) => (res?.data?.results?.[0]?.urls?.raw ?? DEFAULT_COVER) + '&w=400&h=300&fit=crop')
            .catch(() => DEFAULT_COVER + '&w=400&h=300&fit=crop');
    }

    async authenticate(type: string, body: any) {
        const res = await axios.post(`auth/${type}`, body, {
            headers: this.getAuthHeaders()
        })

        const { token } = res.data;

        localStorage.setItem('token', token);
    }

    async getMakes() {
        return axios.get('params/makes', {
            headers: this.getAuthHeaders()
        });
    }

    async getModels(make: string) {
        return axios.get(`params/models?make=${make}`, {
            headers: this.getAuthHeaders()
        });
    }

    async getCars() {
        return axios.get(`cars/`, {
            headers: this.getAuthHeaders()
        });
    }

    async createCar(car: CarFormData) {
        return axios.post(`cars/`, car, {
            headers: this.getAuthHeaders()
        });
    }
}