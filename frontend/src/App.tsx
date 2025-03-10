import { createBrowserRouter, RouterProvider, Outlet, Navigate, Link } from "react-router-dom";
import {Container, Typography, Button, AppBar, Toolbar, IconButton, Box} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import {ErrorPage} from "./pages/error";
import {AuthPage} from "./pages/auth";
import {ProfilePage} from "./pages/profile";
import {CarsPage} from "./pages/car";
import {ServicesPage} from "./pages/catalogue";

const isAuthenticated = () => !!localStorage.getItem("token");

const NavBar = () => (
    <AppBar position="static">
      <Toolbar>
        <IconButton edge="start" color="inherit" aria-label="menu">
          <MenuIcon />
        </IconButton>
        <Button color="inherit" component={Link} to="/">
          Главная
        </Button>
        <Button color="inherit" component={Link} to="/profile">
          Профиль
        </Button>
        <Button color="inherit" component={Link} to="/cars">
          Машины
        </Button>
        <Button color="inherit" component={Link} to="/services">
          Сервисы
        </Button>
      </Toolbar>
    </AppBar>
);

const ProtectedRoute = () => {
  return isAuthenticated() ? (
      <>
        <NavBar />
        <Outlet />
      </>
  ) : (
      <Navigate to="/auth" replace />
  );
};

const Page = () => {
  return (
      <Container className="flex flex-col items-center justify-center min-h-[80vh] text-center gap-y-4">
        <Typography variant="h3">Умный автосервис</Typography>
        <div className="flex gap-x-4">
            <Button variant="contained" color="primary" component={Link} to="/profile">
              <Box py={4}>
                Профиль
              </Box>
            </Button>
          <Button variant="contained" color="secondary" component={Link} to="/cars">
            Машины
          </Button>
          <Button variant="contained" color="success" component={Link} to="/services">
            Сервисы
          </Button>
        </div>
      </Container>
  );
};

const router = createBrowserRouter([
  {
    path: "/",
    element: <Outlet />,
    errorElement: <ErrorPage />,
    children: [
      { path: "/auth", element: <AuthPage /> },
      {
        path: "",
        element: <ProtectedRoute />,
        children: [
          { path: "", element: <Page /> },
          { path: "/profile", element: <ProfilePage /> },
          { path: "/cars", element: <CarsPage /> },
          { path: "/services", element: <ServicesPage /> },
        ],
      },
    ],
  },
]);

const App = () => <RouterProvider router={router} />;

export default App;
