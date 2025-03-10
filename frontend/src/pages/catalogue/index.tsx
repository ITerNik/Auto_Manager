import { useEffect, useState } from "react";
import axios from "axios";
import { Container, Grid, Card, CardContent, Typography, Button, FormControl, InputLabel, Select, MenuItem, Slider } from "@mui/material";

interface Service {
    id: string;
    name: string;
    fullName: string;
    addressName: string;
    addressComment: string;
    type: string;
}

const SERVICE_TYPES = ["Обслуживание", "Мойка", "Замена шин", "Замена масла"];

export const ServicesPage = () => {
    const [services, setServices] = useState<Service[]>([]);
    const [filteredServices, setFilteredServices] = useState<Service[]>([]);
    const [radius, setRadius] = useState<number>(1000);
    const [selectedType, setSelectedType] = useState<string>("");
    const [latitude, setLatitude] = useState<number | null>(null);
    const [longitude, setLongitude] = useState<number | null>(null);
    const [likedServices, setLikedServices] = useState<string[]>([]);

    useEffect(() => {
        axios.get("https://freeipapi.com/api/json")
            .then((res) => {
                setLatitude(res.data.latitude);
                setLongitude(res.data.longitude);
            })
            .catch((err) => console.error("Ошибка получения координат:", err));
    }, []);

    useEffect(() => {
        if (latitude && longitude) {
            axios.get(`http://localhost:8080/range?latitude=${latitude}&longitude=${longitude}&types=Автосервис&r=${radius}`)
                .then((res) => {
                    setServices(res.data);
                    setFilteredServices(res.data);
                })
                .catch((err) => console.error("Ошибка загрузки сервисов:", err));
        }
    }, [latitude, longitude, radius]);

    const handleFilter = () => {
        setFilteredServices(
            services.filter((service) => !selectedType || service.type === selectedType)
        );
    };

    const toggleLike = (id: string) => {
        setLikedServices((prev) =>
            prev.includes(id) ? prev.filter((likedId) => likedId !== id) : [...prev, id]
        );
    };

    return (
        <Container className="flex">
            <div className="w-1/4 p-4 fixed left-0 top-20 bg-white shadow-md rounded-md">
                <Typography variant="h6">Фильтры</Typography>
                <Typography>Радиус: {radius} м</Typography>
                <Slider min={500} max={5000} step={500} value={radius} onChange={(_, value) => setRadius(value as number)} />
                <FormControl fullWidth className="mt-4">
                    <InputLabel>Тип сервиса</InputLabel>
                    <Select value={selectedType} onChange={(e) => setSelectedType(e.target.value)}>
                        <MenuItem value="">Все</MenuItem>
                        {SERVICE_TYPES.map((type) => (
                            <MenuItem key={type} value={type}>{type}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <Button variant="contained" color="primary" fullWidth className="mt-4" onClick={handleFilter}>
                    Применить
                </Button>
            </div>
            <Grid container spacing={2} className="ml-1/4 p-4">
                {filteredServices.map((service) => (
                    <Grid item xs={12} sm={6} md={4} key={service.id}>
                        <Card className="shadow-md">
                            <CardContent>
                                <Typography variant="h6">{service.name}</Typography>
                                <Typography variant="body2">{service.fullName}</Typography>
                                <Typography variant="body2">{service.addressName}</Typography>
                                <Typography variant="body2" color="textSecondary">{service.addressComment}</Typography>
                                <Button
                                    variant={likedServices.includes(service.id) ? "contained" : "outlined"}
                                    color="secondary"
                                    className="mt-2"
                                    onClick={() => toggleLike(service.id)}
                                >
                                    {likedServices.includes(service.id) ? "❤️ Лайкнуто" : "🤍 Лайк"}
                                </Button>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};
