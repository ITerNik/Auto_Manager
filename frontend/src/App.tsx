import { Button, Container, Typography } from "@mui/material";
import { createBrowserRouter, Outlet, RouterProvider } from "react-router-dom";
import { ErrorPage } from "./pages/error";
import { ProfilePage } from "./pages/profile";
import {AuthPage} from "./pages/auth";
import {ProtectedRoute} from "./pages/auth/ProtectedRoute.tsx";

const App = () => <RouterProvider router={router} />;

const Page = () => {
  return (
    <Container className="flex flex-col items-center justify-center min-h-screen text-center">
      <Typography variant="h3" className="mb-4">
        Ну типо умный автосервис
      </Typography>
      <Button variant="contained" color="primary" onClick={alert}>
        Начать
      </Button>
    </Container>
  );
};

const router = createBrowserRouter([
  {
    path: "/",
    element: <Outlet />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: '/auth',
        element: <AuthPage />
      },
      {
        path: "",
        element: <ProtectedRoute />,
        children: [
          {
            path: "",
            element: <Page />,
          },
          {
            path: "/profile",
            element: <ProfilePage />,
          },
        ]
      },
    ],
  },
]);

export default App;
