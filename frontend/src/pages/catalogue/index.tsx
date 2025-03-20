import { useEffect, useState } from "react";
import axios from "axios";
import {
    Container,
    Grid,
    Card,
    CardContent,
    Typography,
    Button,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    Slider,
    Box, Modal, TextField, Rating
} from "@mui/material";
import {APIService} from "../../../network/api.ts";

interface Service {
    id: string;
    name: string;
    fullName: string;
    addressName: string;
    addressComment: string;
    type: string;
    address: {
        city: string;
        street: string;
        house: string;
    }
}

const SERVICE_TYPES = ["Автосервис", "Автомойка", "Заправка", 'Электрическая заправка'];

export const ServicesPage = () => {
    const [services, setServices] = useState<Service[]>([
        {
            id: "1",
            name: "АвтоМастер",
            addressName: "ул. Пушкина, 10",
            fullName: "Автомастерская Пушкина",
            addressComment: "",
            type: "",
            address: {
                city: "",
                street: "",
                house: ""
            }
        },
    ]);
    const [radius, setRadius] = useState<number>(1000);
    const [selectedType, setSelectedType] = useState<string>("");
    const [latitude, setLatitude] = useState<number | null>(null);
    const [longitude, setLongitude] = useState<number | null>(null);
    const [likedServices, setLikedServices] = useState<string[]>([]);

    const [open, setOpen] = useState(false);
    const [reviews, setReviews] = useState([]);
    const [newReview, setNewReview] = useState("");

    const handleOpen = (service) => {
        api.getReviews().then(setReviews)
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setNewReview("");
    };

    const handleAddReview = () => {
        if (newReview.trim()) {
            console.log("Новый отзыв:", newReview);
            setNewReview("");
        }
    };

    const api = new APIService();

    useEffect(() => {
        axios.get("https://freeipapi.com/api/json")
            .then((res) => {
                setLatitude(res.data.latitude);
                setLongitude(res.data.longitude);
            })
            .catch((err) => console.error("Ошибка получения координат:", err));
    }, []);

    const fetchServices = () => {
        if (latitude && longitude) {
            api.getPlaces(latitude, longitude, radius, selectedType).then((res) => {
                    setServices(res.data);
                })
                .catch((err) => console.error("Ошибка загрузки сервисов:", err));
        }
    };

    useEffect(() => {
        fetchServices();
    }, [latitude, longitude]);

    const handleFilter = () => {
        fetchServices();
    };

    const toggleLike = (id: string) => {
        setLikedServices((prev) =>
            prev.includes(id) ? prev.filter((likedId) => likedId !== id) : [...prev, id]
        );
    };

    return (
        <Container className="flex justify-center">
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
            <div className="ml-1/4 w-2/4 p-4">
                <Grid container spacing={2} direction="column">
                    {services.map((service) => (
                        <Grid item key={service.id}>
                            <Card className="shadow-md">
                                <CardContent>
                                    <Typography variant="h6">{service.name}</Typography>
                                    <Typography variant="body2">{service.fullName}</Typography>
                                    <Typography variant="body2">{service.addressName}</Typography>
                                    <Typography variant="body2" color="textSecondary">{service.addressComment}</Typography>
                                    <Typography variant="body2" color="textSecondary">{`${service?.address?.city || ''}, ${service?.address?.street || ''}, ${service?.address?.house || ''}`}</Typography>
                                    <Box display="flex" flexDirection="row" gap={4}>
                                        <Button
                                            variant={likedServices.includes(service.id) ? "contained" : "outlined"}
                                            color="secondary"
                                            className="mt-2"
                                            onClick={() => toggleLike(service.id)}
                                        >
                                            {likedServices.includes(service.id) ? "❤️ Лайкнуто" : "🤍 Лайк"}
                                        </Button>
                                        <Button variant="outlined" onClick={() => handleOpen(service)} className="mt-2">
                                            Отзывы
                                        </Button>
                                    </Box>
                                </CardContent>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
            </div>
            <Modal open={open} onClose={handleClose}>
                <Box className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white p-6 rounded-lg shadow-lg w-96">
                    <Typography variant="h6" className="mb-2">
                        Отзывы
                    </Typography>

                    {/* Форма добавления нового отзыва */}
                    <TextField
                        label="Напишите отзыв..."
                        multiline
                        fullWidth
                        rows={3}
                        variant="outlined"
                        value={newReview}
                        onChange={(e) => setNewReview(e.target.value)}
                        className="mb-2"
                    />
                    <Button variant="contained" color="primary" onClick={handleAddReview} fullWidth>
                        Добавить отзыв
                    </Button>

                    {/* Список отзывов */}
                    {reviews.map((review) => (
                            <Box key={review.id} className="mt-4 p-2 border rounded">
                                <Typography variant="subtitle1">
                                    <strong>{review.author}</strong>
                                </Typography>
                                <Rating value={review.rating} readOnly size="small" />
                                <Typography variant="body2">{review.description}</Typography>
                            </Box>
                        ))}
                </Box>
            </Modal>
        </Container>
    );
};
