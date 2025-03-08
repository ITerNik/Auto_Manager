import { Button, Container, Typography } from "@mui/material";

function App() {
    return (
        <Container className="flex flex-col items-center justify-center min-h-screen text-center">
            <Typography variant="h3" className="mb-4">
                Ну типо умный автосервис
            </Typography>
            <Button variant="contained" color="primary" onClick={() => alert('Yeah!')}>
                Начать
            </Button>
        </Container>
    );
}

export default App;
