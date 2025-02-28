package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateCarWithNullId() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        Car createdCar = carRepository.create(car);

        // The repository (InMemoryRepository) should assign a generated ID if null
        assertNotNull(createdCar.getCarId());
        assertEquals("Toyota", createdCar.getCarName());
        assertEquals("Red", createdCar.getCarColor());
        assertEquals(10, createdCar.getCarQuantity());
    }

    @Test
    void testCreateCarWithExistingId() {
        Car car = new Car();
        car.setCarId("existing-id");
        car.setCarName("Honda");
        car.setCarColor("Blue");
        car.setCarQuantity(5);

        Car createdCar = carRepository.create(car);

        // The repository should keep the existing ID
        assertEquals("existing-id", createdCar.getCarId());
        assertEquals("Honda", createdCar.getCarName());
        assertEquals("Blue", createdCar.getCarColor());
        assertEquals(5, createdCar.getCarQuantity());
    }

    @Test
    void testFindByIdFound() {
        Car car = new Car();
        car.setCarId("id-123");
        car.setCarName("Found Car");
        carRepository.create(car);

        Car foundCar = carRepository.findById("id-123");
        assertNotNull(foundCar);
        assertEquals("Found Car", foundCar.getCarName());
    }

    @Test
    void testFindByIdNotFound() {
        Car foundCar = carRepository.findById("non-existent-id");
        assertNull(foundCar);
    }

    @Test
    void testUpdateFound() {
        // Create a car that we'll update later
        Car car = new Car();
        car.setCarId("id-update");
        car.setCarName("Old Name");
        car.setCarColor("Old Color");
        car.setCarQuantity(1);
        carRepository.create(car);

        // Prepare an updated car object
        Car updatedCar = new Car();
        updatedCar.setCarName("New Name");
        updatedCar.setCarColor("New Color");
        updatedCar.setCarQuantity(10);

        // This should trigger updateEntity(...) internally
        Car result = carRepository.update("id-update", updatedCar);
        assertNotNull(result);
        assertEquals("New Name", result.getCarName());
        assertEquals("New Color", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car updatedCar = new Car();
        updatedCar.setCarName("New Name");
        updatedCar.setCarColor("New Color");
        updatedCar.setCarQuantity(5);

        // No car with this ID, so it should return null
        Car result = carRepository.update("non-existent-id", updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteFound() {
        Car car = new Car();
        car.setCarId("id-to-delete");
        carRepository.create(car);

        // Confirm the car is present
        assertNotNull(carRepository.findById("id-to-delete"));

        carRepository.delete("id-to-delete");

        // Confirm the car is gone
        assertNull(carRepository.findById("id-to-delete"));
    }

    @Test
    void testDeleteNotFound() {
        Car car = new Car();
        car.setCarId("id-other");
        carRepository.create(car);

        // Attempt to delete a non-existent car
        carRepository.delete("does-not-exist");

        // The existing car should still be there
        assertNotNull(carRepository.findById("id-other"));
    }
}
