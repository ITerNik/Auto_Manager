import { useState } from "react";
import { useForm } from "react-hook-form";
import { TextField, Button, Container, Typography, Box } from "@mui/material";
import {APIService} from "../../../network/api.ts";
import {useNavigate} from "react-router-dom";

interface AuthForm {
    email: string;
    password: string;
    name?: string;
    surname?: string;
    confirmPassword?: string;
}

const removeEmptyFields = (data: AuthForm) => {
    return Object.fromEntries(
        Object.entries(data)
            .filter(([_, value]) => value !== "" && value !== null && value !== undefined)
    );
};

export const AuthPage = () => {
    const [isRegister, setIsRegister] = useState(false);

    const api = new APIService();

    const navigate = useNavigate();

    const {
        register,
        handleSubmit,
        watch,
        formState: { errors },
    } = useForm<AuthForm>();

    const onSubmit = (data: AuthForm) => {
        const { confirmPassword, ...requestData } = data;
        api.authenticate(isRegister ? 'sign-up' : 'sign-in', removeEmptyFields(requestData)).then(() => navigate('/')).catch(alert);
    };

    return (
        <Container className="flex flex-col items-center justify-center min-h-screen">
            <Box className="w-full max-w-md p-6 rounded-lg shadow-lg bg-white">
                <Typography variant="h4" className="text-center mb-4">
                    {isRegister ? "Регистрация" : "Вход"}
                </Typography>

                <form onSubmit={handleSubmit(onSubmit)}>
                    {isRegister && (
                        <>
                            <TextField
                                label="Имя"
                                fullWidth
                                margin="normal"
                                {...register("name", { required: "Введите имя" })}
                                error={!!errors.name}
                                helperText={errors.name?.message}
                            />
                            <TextField
                                label="Фамилия"
                                fullWidth
                                margin="normal"
                                {...register("surname", { required: "Введите фамилию" })}
                                error={!!errors.surname}
                                helperText={errors.surname?.message}
                            />
                        </>
                    )}

                    <TextField
                        label="Email"
                        fullWidth
                        margin="normal"
                        {...register("email", { required: "Введите email", pattern: { value: /^\S+@\S+\.\S+$/, message: "Неверный формат email" } })}
                        error={!!errors.email}
                        helperText={errors.email?.message}
                    />

                    <TextField
                        label="Пароль"
                        type="password"
                        fullWidth
                        margin="normal"
                        {...register("password", { required: "Введите пароль", minLength: { value: 6, message: "Минимум 6 символов" } })}
                        error={!!errors.password}
                        helperText={errors.password?.message}
                    />

                    {isRegister && (
                        <TextField
                            label="Подтвердите пароль"
                            type="password"
                            fullWidth
                            margin="normal"
                            {...register("confirmPassword", {
                                required: "Подтвердите пароль",
                                validate: (value) => value === watch("password") || "Пароли не совпадают",
                            })}
                            error={!!errors.confirmPassword}
                            helperText={errors.confirmPassword?.message}
                        />
                    )}

                    <Button type="submit" variant="contained" color="primary" fullWidth className="mt-4">
                        {isRegister ? "Зарегистрироваться" : "Войти"}
                    </Button>
                </form>

                <Typography
                    variant="body2"
                    className="text-center pt-4 text-blue-600 cursor-pointer hover:underline"
                    onClick={() => setIsRegister(!isRegister)}
                >
                    {isRegister ? "Уже есть аккаунт? Войти" : "Нет аккаунта? Зарегистрироваться"}
                </Typography>
            </Box>
        </Container>
    );
};
