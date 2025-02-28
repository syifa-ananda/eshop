package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.create(car);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.findAll();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable("id") String carId) {
        return carService.findById(carId);
    }

    @PutMapping("/{id}")
    public void updateCar(@PathVariable("id") String carId, @RequestBody Car car) {
        carService.update(carId, car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable("id") String carId) {
        carService.deleteCarById(carId);
    }
}
