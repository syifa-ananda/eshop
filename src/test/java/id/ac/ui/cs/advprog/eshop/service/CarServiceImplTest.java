package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    private CarRepositoryInterface carRepository;
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        carRepository = mock(CarRepositoryInterface.class);
        carService = new CarServiceImpl(carRepository);
    }

    @Test
    void testCreate() {
        Car car = new Car();
        car.setCarId("someId");

        when(carRepository.create(car)).thenReturn(car);

        Car result = carService.create(car);

        verify(carRepository, times(1)).create(car);
        assertEquals("someId", result.getCarId());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarId("id-1");
        Car car2 = new Car();
        car2.setCarId("id-2");

        List<Car> carList = Arrays.asList(car1, car2);
        Iterator<Car> carIterator = carList.iterator();

        when(carRepository.findAll()).thenReturn(carIterator);

        List<Car> result = carService.findAll();

        verify(carRepository, times(1)).findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(car1));
        assertTrue(result.contains(car2));
    }

    @Test
    void testFindById() {
        Car car = new Car();
        car.setCarId("find-me");
        when(carRepository.findById("find-me")).thenReturn(car);

        Car result = carService.findById("find-me");

        verify(carRepository, times(1)).findById("find-me");
        assertNotNull(result);
        assertEquals("find-me", result.getCarId());
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setCarId("update-id");

        carService.update("update-id", car);

        verify(carRepository, times(1)).update("update-id", car);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("delete-id");
        verify(carRepository, times(1)).delete("delete-id");
    }
}
