import React, { useState, useEffect } from "react";
import {
    Container,
    Button,
    Dialog,
    DialogContent,
    Card,
    CardMedia,
    CardContent,
    Typography,
    Box,
    DialogTitle
} from "@mui/material";
import Slider from "react-slick";
import {APIService} from "../../../network/api.ts";
import {CarFormData, CarForm} from "./CarForm.tsx";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

export const CarsPage = () => {
    const [cars, setCars] = useState<CarFormData[]>([]);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isMoreOpen, setIsMoreOpen] = useState(false);
    const [editingCar, setEditingCar] = useState<CarFormData | null>(null);

    const api = new APIService();

    useEffect(() => {
        const fetchImages = async () => {
            const cars = await api.getCars();
            const updatedCars = await Promise.all(
                cars.data.map(async (car) => ({
                    ...car,
                    image: car.image || (await api.getCover(`${car.car} ${car.carModel}`)),
                }))
            );
            setCars(updatedCars);
            console.log(updatedCars)
        };

        fetchImages();
    }, []);

    const openModal = (car?: CarFormData) => {
        setEditingCar(car || null);
        setIsModalOpen(true);
    };

    const openMore = (car?: CarFormData) => {
        setEditingCar(car || null);
        setIsMoreOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setEditingCar(null);
    };

    const onSubmit = async (data: CarFormData) => {
        const image = await api.getCover(`${data.car} ${data.carModel}`);

        api.createCar(data);

        setCars([...cars, { ...data, id: Date.now().toString(), image }]);

        closeModal();
    };

    const sliderSettings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 3,
        slidesToScroll: 1,
        centerMode: true,
    };

    return (
        <Container className="flex flex-col items-center mt-8">
            <Typography variant="h4" className="mb-4">Мои автомобили</Typography>

            <Box className="w-full max-w-5xl">
                <Slider {...sliderSettings}>
                    {cars.map((car) => (
                        <Card key={car.id} className="relative m-2 p-4 shadow-lg rounded-lg">
                            <CardMedia component="img" height="200" image={car.image} alt={car.car} />
                            <CardContent className="text-center">
                                <Typography variant="h6">{car.car} {car.carModel}</Typography>
                                <Typography color="textSecondary" overflow="hidden" textOverflow="ellipsis">{car.vicarVin}</Typography>
                                <Button variant="outlined" color="primary" className="mt-4" onClick={() => openMore(car)}>
                                    Подробнее
                                </Button>
                            </CardContent>
                        </Card>
                    ))}
                </Slider>
            </Box>

            <Button variant="contained" color="primary" className="mt-4" onClick={() => openModal()}>
                Добавить автомобиль
            </Button>

            <Dialog open={isModalOpen} onClose={closeModal}>
                <DialogContent>
                    <CarForm onSubmit={onSubmit}/>
                </DialogContent>
            </Dialog>

            <Dialog open={isMoreOpen} onClose={() => setIsMoreOpen(false)}>
                <DialogTitle>
                    Информация об автомобиле
                </DialogTitle>
                <DialogContent>
                    <Typography variant="body1" className="mb-2">
                        <strong>Марка:</strong> {editingCar?.car}
                    </Typography>

                    <Typography variant="body1" className="mb-2">
                        <strong>Модель:</strong> {editingCar?.carModel}
                    </Typography>

                    <Typography variant="body1" className="mb-2">
                        <strong>VIN номер:</strong> {editingCar?.carVin}
                    </Typography>

                    <Typography variant="body1" className="mb-2">
                        <strong>Тип топлива:</strong> {editingCar?.fuelType}
                    </Typography>

                    <Typography variant="body1" className="mb-2">
                        <strong>Коробка передач:</strong> {editingCar?.transmission}
                    </Typography>
                </DialogContent>
            </Dialog>
        </Container>
    );
};
