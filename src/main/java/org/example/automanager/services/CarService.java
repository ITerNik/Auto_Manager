package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.dto.car.CarInfoRequest;
import org.example.automanager.model.Car;
import org.example.automanager.model.Client;
import org.example.automanager.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {
    private final ClientService clientService;
    private final CarRepository carRepository;

    private Car getCarById(UUID carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("No such car!"));
    }

    public List<Car> getUserCars() {
        Client editedCar = clientService.getCurrentUser();
        return editedCar.getCars().stream().toList();
    }

    private boolean checkValidClientAndCar(UUID clientId, UUID carId, String token) {
        if (clientService.checkValidClient(clientId, token)) {
            return getUserCars().stream()
                    .anyMatch(car -> car.getId().equals(carId));
        }
        throw new IllegalArgumentException("You have no access to this car!");
    }

    public void editCarById(UUID carId, CarInfoRequest request, String jwt) {
        if (checkValidClientAndCar(clientService.getCurrentUserId(), carId, jwt)) {

            Car editedCar = getCarById(carId);

            String car = request.getCar();
            String carModel = request.getCarModel();
            String carColor = request.getCarColor();
            Integer carModelYear = request.getCarModelYear();
            String carVin = request.getCarVin();
            String fuelType = request.getFuelType();
            String transmission = request.getTransmission();

            if (car != null) editedCar.setCar(car);
            if (carModel != null) editedCar.setCarModel(carModel);
            if (carColor != null) editedCar.setCarColor(carColor);
            if (carModelYear != null) editedCar.setCarModelYear(carModelYear);
            if (carVin != null) editedCar.setCarVin(carVin);
            if (fuelType != null) editedCar.setFuelType(fuelType);
            if (transmission != null) editedCar.setTransmission(transmission);

            carRepository.save(editedCar);
        }
    }

    public void deleteCar(UUID carId, String jwt) {
        if (checkValidClientAndCar(clientService.getCurrentUserId(), carId, jwt)) {
            clientService.removeClientCarById(carId);
            carRepository.deleteById(carId);
        }
    }

    public void addCar(CarInfoRequest request, String jwt) {
        if (clientService.checkValidClient(clientService.getCurrentUserId(), jwt)) {
            Car car = Car.builder()
                    .car(request.getCar())
                    .carColor(request.getCarColor())
                    .carModel(request.getCarModel())
                    .carModelYear(request.getCarModelYear())
                    .transmission(request.getTransmission())
                    .carVin(request.getCarVin())
                    .fuelType(request.getFuelType())
                    .build();

            clientService.getCurrentUser().getCars().add(car);
            carRepository.save(car);
        }
    }
}
