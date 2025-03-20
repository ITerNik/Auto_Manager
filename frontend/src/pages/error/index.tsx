import { useRouteError } from "react-router-dom";
import { Button, Container, Typography } from "@mui/material";

interface ErrorType {
  status?: number;
  statusText?: string;
  message?: string;
}

export const ErrorPage = () => {
  const error = useRouteError() as ErrorType;

  return (
    <Container className="flex flex-col items-center justify-center min-h-screen text-center">
      <Typography variant="h2" color="error">
        {error.status || 500}
      </Typography>
      <Typography variant="h5" className="mt-2">
        {error.statusText || "Что-то пошло не так"}
      </Typography>
      {error.message && (
        <Typography variant="body1" className="mt-4 text-gray-500">
          {error.message}
        </Typography>
      )}
      <Button
        variant="contained"
        color="primary"
        className="mt-6"
        onClick={() => (window.location.href = "/")}
      >
        Вернуться на главную
      </Button>
    </Container>
  );
};
