import { useEffect, useState } from "react";
import { useForm, Controller } from "react-hook-form";
import { TextField, Button, MenuItem, Select, FormControl, InputLabel, Container, Typography } from "@mui/material";
import {APIService} from "../../../network/api.ts";

export interface CarFormData {
    id: string;
    car: string;
    carModel: string;
    carColor?: string;
    carVin: number;
    fuelType: string;
    transmission: string;
    image?: string;
}

const FUEL_TYPES = ["Бензин", "Дизель", "Электро", "Гибрид"];
const TRANSMISSIONS = ["Механика", "Автомат", "Робот", "Вариатор"];
const COLORS = ['Синий', 'Белый', 'Черный', 'Красный', 'Зеленый', 'Оранжевый']

export const CarForm = ({ onSubmit }: { onSubmit: (data: CarFormData) => void }) => {
    const { register, handleSubmit, control, watch } = useForm<CarFormData>();
    const [makes, setMakes] = useState<string[]>([]);
    const [models, setModels] = useState<string[]>([]);
    const selectedMake = watch("car");

    const api = new APIService();

    useEffect(() => {
        api.getMakes().then((res) => setMakes(res.data));
    }, []);

    useEffect(() => {
        if (selectedMake) {
            api.getModels(selectedMake).then((res) => setModels(res.data));
        } else {
            setModels([]);
        }
    }, [selectedMake]);

    return (
        <Container className="p-6 shadow-md rounded-md bg-white">
            <Typography variant="h5" className="mb-4">Добавить автомобиль</Typography>
            <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
                <FormControl fullWidth>
                    <InputLabel>Марка</InputLabel>
                    <Controller
                        name="car"
                        control={control}
                        render={({ field }) => (
                            <Select {...field}>
                                {makes.map((make) => (
                                    <MenuItem key={make} value={make}>{make}</MenuItem>
                                ))}
                            </Select>
                        )}
                    />
                </FormControl>

                <FormControl fullWidth disabled={!selectedMake}>
                    <InputLabel>Модель</InputLabel>
                    <Controller
                        name="carModel"
                        control={control}
                        render={({ field }) => (
                            <Select {...field}>
                                {models.map((model) => (
                                    <MenuItem key={model} value={model}>{model}</MenuItem>
                                ))}
                            </Select>
                        )}
                    />
                </FormControl>

                <FormControl fullWidth disabled={!selectedMake}>
                    <InputLabel>Цвет</InputLabel>
                    <Controller
                        name="carColor"
                        control={control}
                        render={({ field }) => (
                            <Select {...field}>
                                {COLORS.map((color) => (
                                    <MenuItem key={color} value={color}>{color}</MenuItem>
                                ))}
                            </Select>
                        )}
                    />
                </FormControl>

                <TextField label="VIN номер" {...register("carVin", { required: true })} fullWidth />

                <FormControl fullWidth>
                    <InputLabel>Тип топлива</InputLabel>
                    <Controller
                        name="fuelType"
                        control={control}
                        render={({ field }) => (
                            <Select {...field}>
                                {FUEL_TYPES.map((type) => (
                                    <MenuItem key={type} value={type}>{type}</MenuItem>
                                ))}
                            </Select>
                        )}
                    />
                </FormControl>

                <FormControl fullWidth>
                    <InputLabel>Коробка передач</InputLabel>
                    <Controller
                        name="transmission"
                        control={control}
                        render={({ field }) => (
                            <Select {...field}>
                                {TRANSMISSIONS.map((transmission) => (
                                    <MenuItem key={transmission} value={transmission}>{transmission}</MenuItem>
                                ))}
                            </Select>
                        )}
                    />
                </FormControl>

                <Button type="submit" variant="contained" color="primary">Добавить</Button>
            </form>
        </Container>
    );
};
