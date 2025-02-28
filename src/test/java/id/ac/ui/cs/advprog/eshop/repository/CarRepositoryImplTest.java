package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryImplTest {

    private CarRepositoryImpl carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepositoryImpl();
    }

    @Test
    void testCreateCarWithNullId() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        Car createdCar = carRepository.create(car);

        // The repository should generate an ID if it's null
        assertNotNull(createdCar.getCarId());
        assertEquals("Toyota", createdCar.getCarName());
        assertEquals("Red", createdCar.getCarColor());
        assertEquals(10, createdCar.getCarQuantity());
    }

    @Test
    void testCreateCarWithExistingId() {
        Car car = new Car();
        // Simulate an existing ID
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
    void testFindAllWhenEmpty() {
        Iterator<Car> iterator = carRepository.findAll();
        // Should have nothing
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindAllWhenNotEmpty() {
        Car car1 = new Car();
        car1.setCarId("id-1");
        car1.setCarName("Car 1");
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarId("id-2");
        car2.setCarName("Car 2");
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
        Car foundCar1 = iterator.next();
        assertTrue(iterator.hasNext());
        Car foundCar2 = iterator.next();
        assertFalse(iterator.hasNext());

        // Basic checks (optional, but good for sanity)
        assertNotNull(foundCar1);
        assertNotNull(foundCar2);
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
        Car car = new Car();
        car.setCarId("id-456");
        car.setCarName("Old Name");
        car.setCarColor("Old Color");
        car.setCarQuantity(1);
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("New Name");
        updatedCar.setCarColor("New Color");
        updatedCar.setCarQuantity(5);

        Car result = carRepository.update("id-456", updatedCar);
        assertNotNull(result);
        assertEquals("New Name", result.getCarName());
        assertEquals("New Color", result.getCarColor());
        assertEquals(5, result.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car updatedCar = new Car();
        updatedCar.setCarName("New Name");
        updatedCar.setCarColor("New Color");
        updatedCar.setCarQuantity(5);

        Car result = carRepository.update("non-existent-id", updatedCar);
        // Should return null if not found
        assertNull(result);
    }

    @Test
    void testDeleteFound() {
        Car car = new Car();
        car.setCarId("id-to-delete");
        carRepository.create(car);

        // Verify car is in the repository
        assertNotNull(carRepository.findById("id-to-delete"));

        carRepository.delete("id-to-delete");
        // After deletion, it should be null
        assertNull(carRepository.findById("id-to-delete"));
    }

    @Test
    void testDeleteNotFound() {
        Car car = new Car();
        car.setCarId("id-other");
        carRepository.create(car);

        // Attempting to delete a non-existent ID
        carRepository.delete("id-non-existent");

        // The existing car should still be present
        assertNotNull(carRepository.findById("id-other"));
    }
}
