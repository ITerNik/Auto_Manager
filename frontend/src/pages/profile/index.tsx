import {useEffect, useState} from "react";
import { useForm } from "react-hook-form";
import { Button, Container, Typography, Box, Avatar } from "@mui/material";
import { AccountCircle } from "@mui/icons-material";
import { ProfileField } from "../../components/fields/ProfileField.tsx";
import {APIService} from "../../../network/api.ts";

interface ProfileForm {
  name: string;
  surname: string;
  email: string;
  birthday: string;
}

interface PasswordForm {
  password: string;
  newPassword: string;
  confirmPassword: string;
}

const EditingButtonsBlock = ({
  enableEditing,
  className,
}: {
  enableEditing: (enable: boolean) => void;
  className?: string;
}) => {
  return (
    <Box className={className} flexDirection="row" display="flex" gap="4px">
      <Button type="submit" variant="contained" color="success" fullWidth>
        Сохранить
      </Button>

      <Button
        variant="outlined"
        color="error"
        fullWidth
        className="mt-2"
        onClick={() => enableEditing(false)}
      >
        Отмена
      </Button>
    </Box>
  );
};

export const ProfilePage = () => {
  const [userData, setUserData] = useState<any>(null);
  const [isEditing, setIsEditing] = useState(false);
  const [isChangingPassword, setIsChangingPassword] = useState(false);

  const api = new APIService();

  useEffect(() => {
    api.getUserInfo().then(res => res.data)
            .then(setUserData)
  }, []);

  const {
    register,
    handleSubmit,
    formState: { errors , dirtyFields},
    watch,
  } = useForm<ProfileForm>({
    defaultValues: {
      name: "Иван",
      surname: "Иванов",
      email: "ivan@example.com",
      birthday: "1990-05-15",
    },
    values: userData,
  });

  const {
    register: registerPassword,
    handleSubmit: handleSubmitPassword,
    formState: { errors: passwordErrors },
    watch: watchPassword,
    reset: resetPasswordForm,
  } = useForm<PasswordForm>();

  const onSubmit = (data: ProfileForm) => {
    const dirty = Object.fromEntries(Object.entries(data).filter(([key, _]) => Boolean(dirtyFields[key])));
    api.editUserInfo(userData.id, dirty);
    enableEditing(false);
  };

  const onChangePassword = (data: PasswordForm) => {
    if (data.newPassword !== data.confirmPassword) {
      alert("Пароли не совпадают!");
      return;
    }
    const {confirmPassword, ...dataNeed} = data;
    api.editUserInfo(userData.id, dataNeed);
    enablePasswordEditing(false);
  };

  const enableEditing = (enable: boolean) => {
    setIsEditing(enable);
    setIsChangingPassword(false);
  };

  const enablePasswordEditing = (enable: boolean) => {
    resetPasswordForm();
    setIsEditing(false);
    setIsChangingPassword(enable);
  };

  return (
    <Container className="flex flex-col items-center justify-center min-h-screen">
      <Avatar className="mb-4" sx={{ width: 80, height: 80 }}>
        <AccountCircle sx={{ width: "100%", height: "100%" }} />
      </Avatar>

      <Typography variant="h4" className="mb-4">
        Мои данные
      </Typography>

      <Box className="w-full max-w-md p-6 rounded-lg shadow-lg bg-white">
        {!isChangingPassword && (
          <Box component="form" onSubmit={handleSubmit(onSubmit)}>
            <ProfileField
              label="Имя"
              name="name"
              value={watch("name")}
              isEditing={isEditing}
              register={register("name", { required: "Введите имя" })}
              error={errors.name?.message}
            />

            <ProfileField
              label="Фамилия"
              name="surname"
              value={watch("surname")}
              isEditing={isEditing}
              register={register("surname", { required: "Введите фамилию" })}
              error={errors.surname?.message}
            />

            <ProfileField
              label="Email"
              name="email"
              disabled={true}
              value={watch("email")}
              isEditing={isEditing}
              register={register("email", {
                required: "Введите email",
                pattern: {
                  value: /^\S+@\S+\.\S+$/,
                  message: "Неверный формат email",
                },
              })}
              error={errors.email?.message}
            />

            <ProfileField
              label="Дата рождения"
              name="birthday"
              value={watch("birthday")}
              isEditing={isEditing}
              register={register("birthday", {
                required: "Выберите дату рождения",
              })}
              error={errors.birthday?.message}
              type="date"
            />

            {isEditing && (
              <EditingButtonsBlock
                enableEditing={enableEditing}
                className="mt-4"
              />
            )}
          </Box>
        )}
        {!isEditing && !isChangingPassword && (
          <Box flexDirection="row" display="flex" gap="4px" className="mt-4">
            <Button
              variant="contained"
              color="primary"
              fullWidth
              className="mt-4"
              onClick={() => enableEditing(true)}
            >
              Редактировать
            </Button>

            <Button
              variant="outlined"
              color="secondary"
              fullWidth
              className="mt-4"
              onClick={() => enablePasswordEditing(true)}
            >
              Изменить пароль
            </Button>
          </Box>
        )}

        {isChangingPassword && (
          <Box
            component="form"
            onSubmit={handleSubmitPassword(onChangePassword)}
            className="mt-4"
          >
            <ProfileField
              label="Старый пароль"
              type="password"
              register={registerPassword("password", {
                required: "Введите старый пароль",
              })}
              error={passwordErrors.password?.message}
              name="password"
              value={watchPassword("password")}
              isEditing={isChangingPassword}
            />

            <ProfileField
              label="Новый пароль"
              type="password"
              register={registerPassword("newPassword", {
                required: "Введите новый пароль",
                minLength: { value: 6, message: "Минимум 6 символов" },
              })}
              error={passwordErrors.newPassword?.message}
              name="newPassword"
              value={watchPassword("newPassword")}
              isEditing={isChangingPassword}
            />

            <ProfileField
              label="Подтвердите пароль"
              type="password"
              register={registerPassword("confirmPassword", {
                required: "Подтвердите пароль",
              })}
              error={passwordErrors.confirmPassword?.message}
              name="confirmPassword"
              value={watchPassword("confirmPassword")}
              isEditing={isChangingPassword}
            />

            <EditingButtonsBlock
              enableEditing={enablePasswordEditing}
              className="mt-4"
            />
          </Box>
        )}
      </Box>
    </Container>
  );
};
